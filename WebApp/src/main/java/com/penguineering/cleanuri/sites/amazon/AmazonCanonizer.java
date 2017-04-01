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
package com.penguineering.cleanuri.sites.amazon;

import java.net.URI;

import com.penguineering.cleanuri.api.Canonizer;

import net.jcip.annotations.ThreadSafe;
import ru.lanwen.verbalregex.VerbalExpression;

@ThreadSafe
public class AmazonCanonizer implements Canonizer {
	static final VerbalExpression idRegex = VerbalExpression.regex().startOfLine().anything().then("/dp/").capture()
			.anything().endCapture().then("/").anything().endOfLine().build();

	public AmazonCanonizer() {
	}

	@Override
	public boolean isSuitable(URI uri) {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");

		final String authority = uri.getAuthority();

		return authority != null && authority.endsWith("amazon.de");
	}

	@Override
	public URI canonize(URI uri) {
		final String uriStr = uri.toASCIIString();

		if (!idRegex.test(uriStr))
			throw new IllegalArgumentException("URI does not fit the expected structure.");

		final String id = idRegex.getText(uriStr, 1);

		return URI.create(AmazonSite.PREFIX + id);
	}

}
