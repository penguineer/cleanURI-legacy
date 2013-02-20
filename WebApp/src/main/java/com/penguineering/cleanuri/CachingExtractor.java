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
package com.penguineering.cleanuri;

import java.net.URI;
import java.util.Map;

import net.jcip.annotations.Immutable;

import com.penguineering.cleanuri.api.Extractor;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;

/**
 * An extractor which caches meta-data in the URI meta-data store.
 * 
 * @author Tux (tux@netz39.de)
 *
 */
@Immutable
public class CachingExtractor implements Extractor {
	public static CachingExtractor forExtractor(Extractor extractor) {
		return new CachingExtractor(extractor);
	}

	private final Extractor extractor;

	private CachingExtractor(Extractor extractor) {
		if (extractor == null)
			throw new NullPointerException(
					"Extractor argument must not be null!");

		this.extractor = extractor;
	}

	@Override
	public Map<Metakey, String> extractMetadata(URI uri)
			throws ExtractorException {
		Map<Metakey, String> meta = UriMetadataStore.INSTANCE
				.getUriProperties(uri);

		if (meta == null) {
			meta = extractor.extractMetadata(uri);

			if (meta != null)
				UriMetadataStore.INSTANCE.addUriProperties(uri, meta);
		}

		return meta;
	}
}
