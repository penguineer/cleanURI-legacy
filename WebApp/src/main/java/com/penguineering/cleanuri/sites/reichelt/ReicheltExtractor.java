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
	public static ReicheltExtractor getInstance() {
		return new ReicheltExtractor();
	}

	private ReicheltExtractor() {

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

					// Doppelpunkte
					int col_idx = line.indexOf("<span> :: <span");
					final String art_id = line.substring(8, col_idx);
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
