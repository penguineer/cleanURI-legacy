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

import com.penguineering.cleanuri.api.Canonizer;

import net.jcip.annotations.ThreadSafe;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Canonizer for the Reichelt online catalogue.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
@ThreadSafe
public class ReicheltCanonizer implements Canonizer {
	static final VerbalExpression artidRegex = VerbalExpression.regex().startOfLine().then("http").anything()
			.then("://www.reichelt.de/").anything().then("-p").capture().anything().endCapture().then(".html")
			.anything().endOfLine().build();

	static final VerbalExpression artidRegex2 = VerbalExpression.regex().startOfLine().then("http").anything()
			.then("://www.reichelt.de/index.html?ARTICLE=").capture().anything().endCapture().endOfLine().build();

	public ReicheltCanonizer() {
	}

	@Override
	public boolean isSuitable(URI uri) {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");

		final String authority = uri.getAuthority();

		return authority != null && authority.endsWith("reichelt.de") &&
				(artidRegex.test(uri.toASCIIString()) || artidRegex2.test(uri.toASCIIString()));
	}

	@Override
	public URI canonize(URI uri) {
		if (!this.isSuitable(uri))
			throw new IllegalArgumentException("URI does not match this site!");

		final String ART_id = getArticleID(uri);

		return URI.create(ReicheltSite.PREFIX + ART_id);
	}

	private String getArticleID(URI uri) {
		if (!isSuitable(uri))
			throw new IllegalArgumentException("URI is not suitable for this extractor!");

		final String artid;

		if (artidRegex.test(uri.toASCIIString()))
			artid = artidRegex.getText(uri.toASCIIString(), 1);
		else if (artidRegex2.test(uri.toASCIIString()))
			artid = artidRegex2.getText(uri.toASCIIString(), 1);
		else
			artid = null;

		return artid;
	}

}
