package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;

import net.jcip.annotations.ThreadSafe;

import com.penguineering.cleanuri.api.Canonizer;

/**
 * Canonizer for the Reichelt online catalogue.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
@ThreadSafe
public class ReicheltCanonizer implements Canonizer {
	/**
	 * Get an instance of the Reichelt canonizer.
	 * 
	 * @return an instance of the Reichelt canonizer.
	 */
	public static ReicheltCanonizer getInstance() {
		return new ReicheltCanonizer();
	}

	private final String PREFIX = "http://www.reichelt.de/index.html?ARTICLE=";

	private ReicheltCanonizer() {
	}

	@Override
	public boolean isSuitable(URI uri) {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");

		final String authority = uri.getAuthority();

		return authority != null && authority.endsWith("reichelt.de");
	}

	@Override
	public URI canonize(URI uri) {
		if (!this.isSuitable(uri))
			throw new IllegalArgumentException("URI does not match this site!");

		final String ART_id = getArticleID(uri);

		return URI.create(PREFIX + ART_id);
	}

	private String getArticleID(URI uri) {
		String query = uri.getQuery();

		// extract the ARTICLE from the query
		final int ART_idx = query.indexOf("ARTICLE=");
		int COL_idx = query.indexOf(";", ART_idx);
		// take the rest of the string if there are no more delimiters
		if (COL_idx == -1)
			COL_idx = query.length();

		return query.substring(ART_idx + 8, COL_idx);

	}

}
