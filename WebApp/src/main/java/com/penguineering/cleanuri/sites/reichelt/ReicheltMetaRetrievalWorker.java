package com.penguineering.cleanuri.sites.reichelt;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringEscapeUtils;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class ReicheltMetaRetrievalWorker implements Callable<Properties> {
	public static final String PAR_ARTID = "artid";
	public static final String PAR_DESCRIPTION = "description";

	public static ReicheltMetaRetrievalWorker forURI(URI uri) {
		return new ReicheltMetaRetrievalWorker(uri);
	}

	private final URI uri;

	private ReicheltMetaRetrievalWorker(URI uri) {
		super();

		if (uri == null)
			throw new NullPointerException("The URI argument must not be null!");

		this.uri = uri;
	}

	public URI getURI() {
		return this.uri;
	}

	@Override
	public Properties call() throws Exception {
		final Properties meta = new Properties();

		final URL url = uri.toURL();
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
				final String art_id = line.substring(8, col_idx).trim();
				meta.setProperty(PAR_ARTID,
						StringEscapeUtils.unescapeHtml4(art_id));

				int span_idx = line.indexOf("</span>");
				final String art_name = line.substring(col_idx + 32, span_idx)
						.trim();
				meta.setProperty(PAR_DESCRIPTION,
						StringEscapeUtils.unescapeHtml4(art_name));
				
				break;
			}
		} finally {
			if (reader != null)
				reader.close();
		}
		return meta;
	}
}
