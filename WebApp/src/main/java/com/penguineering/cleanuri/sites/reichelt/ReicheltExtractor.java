/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.penguineering.cleanuri.sites.reichelt;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import com.penguineering.cleanuri.api.Extractor;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;

/**
 * Meta-data extractor for Reichelt catalog data.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
public class ReicheltExtractor implements Extractor {
	public ReicheltExtractor() {

	}

	@Override
	public Map<Metakey, String> extractMetadata(URI uri)
			throws ExtractorException {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");

		URL url;
		try {
			url = uri.toURL();
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("The provided URI is not a URL!");
		}

		Map<Metakey, String> meta = new HashMap<Metakey, String>();

		try {
			final URLConnection con = url.openConnection();

			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new InputStreamReader(
						con.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					if (!line.contains("<h2>"))
						continue;

					// h2
					int h2_idx = line.indexOf("h2");
					// Doppelpunkte
					int col_idx = line.indexOf("<span> :: <span");
					final String art_id = line.substring(h2_idx + 3, col_idx);
					meta.put(Metakey.ID, html2oUTF8(art_id).trim());

					int span_idx = line.indexOf("</span>");
					final String art_name = line.substring(col_idx + 32,
							span_idx);
					meta.put(Metakey.NAME, html2oUTF8(art_name).trim());

					break;
				}

				return meta;
			} finally {
				if (reader != null)
					reader.close();
			}
		} catch (IOException e) {
			throw new ExtractorException("I/O exception during extraction: "
					+ e.getMessage(), e, uri);
		}

	}

	private static String html2oUTF8(String html)
			throws UnsupportedEncodingException {
		final String iso = StringEscapeUtils.unescapeHtml4(html);

		final byte[] b = iso.getBytes("ISO-8859-15");
		return new String(b, "ISO-8859-15");
	}

}
