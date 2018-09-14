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
import java.util.Map;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.api.Decorator;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;
import com.penguineering.cleanuri.decorators.DokuwikiDecorator;

public class ReicheltSiteTestFrame {

	public static void main(String args[]) throws ExtractorException {
		final URI uri = URI
				.create("https://www.reichelt.de/i-o-port-expander-with-interrupt-16-bit-soic-28-mcp-23016-i-so-p89337.html?r=1");

		final Site site = ReicheltSite.getInstance();

		URI href = site.getCanonizer().canonize(uri);
		Map<Metakey, String> meta = site.getExtractor().extractMetadata(href);

		Decorator decorator = new DokuwikiDecorator();
		System.out.println(decorator.decorate(href, meta));
	}
}
