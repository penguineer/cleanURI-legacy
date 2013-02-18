package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.Callable;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.UriMetadataStore;
import com.penguineering.cleanuri.Verbosity;

/**
 * Site implementation for reichelt.de.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
public class ReicheltSite implements Site {
	public static ReicheltSite getInstance() {
		return new ReicheltSite();
	}

	private ReicheltSite() {
	}

	@Override
	public String getId() {
		return "Reichelt";
	}

	@Override
	public String getLabel() {
		return "Site transformer for reichelt.de";
	}

	@Override
	public boolean isMatch(URI uri) {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");

		final String authority = uri.getAuthority();

		return authority != null && authority.endsWith("reichelt.de");
	}

	@Override
	public String transform(URI uri, Verbosity v, String target) {
		if (!isMatch(uri))
			throw new IllegalArgumentException("URI does not match this site!");

		final String ART_id = getArticleID(uri);

		final URI href = URI
				.create("http://www.reichelt.de/index.html?ARTICLE=" + ART_id);

		StringBuilder result = new StringBuilder();
		if (v == Verbosity.DECORATED) {
			try {
				Properties meta = UriMetadataStore.INSTANCE
						.getUriProperties(href);

				if (meta == null) {
					Callable<Properties> c = ReicheltMetaRetrievalWorker
							.forURI(href);
					meta = c.call();
					UriMetadataStore.INSTANCE.addUriProperties(href, meta);
				}

				result.append(createDokuwikiString(href, meta));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else
			result.append(href);

		return result.toString();
	}

	private String createDokuwikiString(URI href, Properties meta) {
		StringBuilder result = new StringBuilder();

		result.append("[[");
		result.append(href.toASCIIString());
		result.append("|");
		result.append(meta.get(ReicheltMetaRetrievalWorker.PAR_ARTID));
		result.append("]] – ");
		result.append(meta.get(ReicheltMetaRetrievalWorker.PAR_DESCRIPTION));

		return result.toString();
	}

	private String getArticleID(URI uri) {
		String query = uri.getQuery();

		// extract the ARTICLE from the query
		final int ART_idx = query.indexOf("ARTICLE=");
		int COL_idx = query.indexOf(";", ART_idx);
		if (COL_idx == -1)
			COL_idx = query.length();

		return query.substring(ART_idx + 8, COL_idx);

	}

}
