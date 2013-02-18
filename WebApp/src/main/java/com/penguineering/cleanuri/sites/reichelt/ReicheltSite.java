package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.Verbosity;

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
	public String transform(URI uri, Verbosity v, String target, String format) {
		if (!isMatch(uri))
			throw new IllegalArgumentException("URI does not match this site!");

		final String ART_id = getArticleID(uri);

		return URI.create(
				"https://www.reichelt.de/index.html?ARTICLE=" + ART_id)
				.toASCIIString();
	}

	private String getArticleID(URI uri) {
		String query = uri.getQuery();

		// extract the ARTICLE from the query
		int ART_idx = query.indexOf("ARTICLE=");
		int COL_idx = query.indexOf(";", ART_idx);

		return query.substring(ART_idx + 8, COL_idx);

	}

}
