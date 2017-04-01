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
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

import com.penguineering.cleanuri.api.Extractor;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;

import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Meta-data extractor for Reichelt catalog data.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
public class ReicheltExtractor implements Extractor {
	static final VerbalExpression idRegex = VerbalExpression.regex().startOfLine().then("<title>").capture().anything()
			.endCapture().then(":").anything().then(" bei reichelt elektronik</title>").endOfLine().build();

	static final VerbalExpression descRegex = VerbalExpression.regex().startOfLine().then("<title>").anything()
			.then(":").capture().anything().endCapture().then(" bei reichelt elektronik</title>").endOfLine().build();

	public ReicheltExtractor() {

	}

	@Override
	public Map<Metakey, String> extractMetadata(URI uri) throws ExtractorException {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");

		final String uriStr = uri.toASCIIString();

		// Check if the prefix matches
		if (!uriStr.startsWith(ReicheltSite.PREFIX))
			throw new IllegalArgumentException(
					"Reichelt extractor has been presented a URI which does not match the Reichelt prefix!");

		/*
		 * Create a URL from the provided URI.
		 * 
		 * Reichelt made everybode use HTTPS now, unfortunately this is encoded
		 * in the URI. HTTP still works, but will result in a 301 response,
		 * which we resolve beforehand by changing the http scheme in the URI to
		 * https.
		 */
		final URL url;
		try {
			// Reichelt URLs must be HTTPS now
			if (uri.getScheme().equals("http"))
				url = new URI("https" + uriStr.substring(4)).toURL();
			else
				url = uri.toURL();
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("The provided URI is not a URL!");
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Could not convert provided URL to https scheme!", e);
		}

		Map<Metakey, String> meta = new HashMap<Metakey, String>();

		try {
			final URLConnection con = url.openConnection();

			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new InputStreamReader(con.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					// extract the id
					if (idRegex.test(line)) {
						final String id = idRegex.getText(line, 1);
						meta.put(Metakey.ID, html2oUTF8(id).trim());
					}

					// extract the description
					if (descRegex.test(line)) {
						final String desc = descRegex.getText(line, 1);
						meta.put(Metakey.NAME, html2oUTF8(desc).trim());
					}
				}

				return meta;
			} finally {
				if (reader != null)
					reader.close();
			}
		} catch (IOException e) {
			throw new ExtractorException("I/O exception during extraction: " + e.getMessage(), e, uri);
		}

	}

	private static String html2oUTF8(String html) throws UnsupportedEncodingException {
		final String iso = StringEscapeUtils.unescapeHtml4(html);

		final String encoding = "ISO-8859-1"; // according to page header
		// final String encoding = "UTF-8";

		final byte[] b = iso.getBytes(encoding);
		return new String(b, encoding);
	}
}
