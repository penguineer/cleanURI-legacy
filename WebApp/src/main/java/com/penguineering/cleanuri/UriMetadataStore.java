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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

import com.penguineering.cleanuri.api.Metakey;

@ThreadSafe
public enum UriMetadataStore {
	INSTANCE;

	private final Map<URI, Map<Metakey, String>> uriProps;

	private UriMetadataStore() {
		uriProps = new ConcurrentHashMap<URI, Map<Metakey, String>>();
	}

	public Map<Metakey, String> getUriProperties(URI rec) {
		final Map<Metakey, String> props = uriProps.get(rec);
		return props == null ? null : new HashMap<Metakey, String>(props);
	}

	public void addUriProperties(URI rec, Map<Metakey, String> props) {
		synchronized (uriProps) {
			Map<Metakey, String> p = uriProps.get(rec);

			if (p == null) {
				p = new HashMap<Metakey, String>();
				uriProps.put(rec, p);
			}

			p.putAll(props);
		}
	}

}
