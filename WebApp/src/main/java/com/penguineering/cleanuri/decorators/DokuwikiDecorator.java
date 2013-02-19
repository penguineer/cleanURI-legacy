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
package com.penguineering.cleanuri.decorators;

import java.net.URI;
import java.util.Map;

import com.penguineering.cleanuri.api.Decorator;
import com.penguineering.cleanuri.api.Metakey;

public class DokuwikiDecorator implements Decorator {

	public DokuwikiDecorator() {
	}

	@Override
	public String decorate(URI uri, Map<Metakey, String> meta) {
		if (uri == null || meta == null)
			throw new NullPointerException("Arguments must not be null!");

		StringBuilder result = new StringBuilder();

		result.append("[[");
		result.append(uri);
		result.append("|");
		result.append(meta.get(Metakey.ID));
		result.append("]] – ");
		result.append(meta.get(Metakey.NAME));

		return result.toString();
	}

}
