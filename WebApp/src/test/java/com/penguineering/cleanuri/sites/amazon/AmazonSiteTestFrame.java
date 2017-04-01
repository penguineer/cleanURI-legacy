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
import java.util.Map;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.api.Decorator;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;
import com.penguineering.cleanuri.decorators.DokuwikiDecorator;

public class AmazonSiteTestFrame {

	public static void main(String args[]) throws ExtractorException {
		final URI uri = URI.create(
				"https://www.amazon.de/Aihasd-SNOFF-Wireless-Controlled-Switch/dp/B01I1A5W3A/ref=sr_1_1?ie=UTF8&qid=1491047244&sr=8-1&keywords=Aihasd+SNOFF+WiFi+Wireless+APP+Controlled+Smart+Switch+For+Smart+Home");

		final Site site = AmazonSite.getInstance();

		URI href = site.getCanonizer().canonize(uri);
		Map<Metakey, String> meta = site.getExtractor().extractMetadata(href);

		System.out.println(href);

		Decorator decorator = new DokuwikiDecorator();
		System.out.println(decorator.decorate(href, meta));
	}
}
