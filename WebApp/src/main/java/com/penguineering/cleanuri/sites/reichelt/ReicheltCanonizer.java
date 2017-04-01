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

	public ReicheltCanonizer() {
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

		return URI.create(ReicheltSite.PREFIX + ART_id);
	}

	private static final String ART_ID = "ARTICLE=";

	private String getArticleID(URI uri) {
		String query = uri.getQuery();

		// extract the ARTICLE from the query
		final int ART_idx = query.indexOf(ART_ID);
		final int COL_idx = query.indexOf(";", ART_idx);
		final int AMP_idx = query.indexOf("&", ART_idx);

		final int idx;
		// take the rest of the string if there are no more delimiters
		if (COL_idx == -1 && AMP_idx == -1)
			idx = query.length();
		else
			// take index of AMP or COL, whichever exists and comes first
			idx = Math.min(COL_idx == -1 ? query.length() : COL_idx, AMP_idx == -1 ? query.length() : AMP_idx);

		return query.substring(ART_idx + ART_ID.length(), idx);

	}

}
