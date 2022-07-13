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
import com.penguineering.cleanuri.decorators.JsonDecorator;

public class AmazonSiteTestFrame {

	public static void main(String args[]) throws ExtractorException {
		final URI uri = URI.create(
				"https://www.amazon.de/dp/B089N623TP/?psc=1&ref_=lv_ov_lig_dp_it");

		final Site site = AmazonSite.getInstance();

		URI href = site.getCanonizer().canonize(uri);
		Map<Metakey, String> meta = site.getExtractor().extractMetadata(href);

		System.out.println(href);

		Decorator decorator = new DokuwikiDecorator();
		System.out.println(decorator.decorate(href, meta));

		decorator = new JsonDecorator();
		System.out.println(decorator.decorate(href, meta));
	}
}
