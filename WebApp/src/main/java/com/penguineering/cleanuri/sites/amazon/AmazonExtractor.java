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
 */package com.penguineering.cleanuri.sites.amazon;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.penguineering.cleanuri.api.Extractor;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;

import net.jcip.annotations.ThreadSafe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.lanwen.verbalregex.VerbalExpression;

@ThreadSafe
public class AmazonExtractor implements Extractor {
	static final VerbalExpression idRegex = VerbalExpression.regex().startOfLine().anything().then("/dp/").capture()
			.anything().endCapture().endOfLine().build();

	static final VerbalExpression descRegex = VerbalExpression.regex().startOfLine()
			.capture().anything().endCapture().then(": Amazon.de").anything().endOfLine().build();

	public AmazonExtractor() {
	}

	@Override
	public Map<Metakey, String> extractMetadata(URI uri) throws ExtractorException {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");

		final String uriStr = uri.toASCIIString();

		// Check if the prefix matches
		if (!uriStr.startsWith(AmazonSite.PREFIX))
			throw new IllegalArgumentException(
					"Amazon extractor has been presented a URI which does not match the Amazon prefix!");

		/*
		 * Create a URL from the provided URI.
		 * 
		 * Amazon made everybody use HTTPS now, unfortunately this is encoded in
		 * the URI. HTTP still works, but will result in a 301 response, which
		 * we resolve beforehand by changing the http scheme in the URI to
		 * https.
		 */
		final URL url;
		try {
			// Amazon URLs must be HTTPS now
			if (uri.getScheme().equals("http"))
				url = new URI("https" + uriStr.substring(4)).toURL();
			else
				url = uri.toURL();
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("The provided URI is not a URL!");
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Could not convert provided URL to https scheme!", e);
		}

		Map<Metakey, String> meta = new HashMap<>();

		final String id = idRegex.getText(uriStr, 1);
		meta.put(Metakey.ID, id.trim());

		try {
			final Document doc = Jsoup.connect(url.toExternalForm()).get();

			final Elements title = doc.select("title");
			final String titleText = title.text();
			if (descRegex.test(titleText)) {
				final String desc = descRegex.getText(titleText, 1);
				meta.put(Metakey.NAME, desc.trim());
			}
		} catch (IOException e) {
			throw new ExtractorException("I/O exception during extraction: " + e.getMessage(), e, uri);
		}

		return meta;
	}
}
