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
package com.penguineering.cleanuri.api;

import java.net.URI;
import java.util.Map;

public interface Extractor {
	/**
	 * Extract meta-data for the given URI.
	 * 
	 * @param uri
	 *            The target URI.
	 * @return a map of meta-data values.
	 * @throws ExtractorException
	 *             if extraction fails.
	 * @throws NullPointerException
	 *             if the URI argument is null.
	 * @throws IllegalArgumentException
	 *             if the URI is not suitable for use with this extractor.
	 */
	public Map<Metakey, String> extractMetadata(URI uri)
			throws ExtractorException;
}
