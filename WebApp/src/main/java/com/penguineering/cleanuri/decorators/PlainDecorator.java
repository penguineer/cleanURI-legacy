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

/**
 * A decorator that returns the plain URI as ASCII representation.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
public class PlainDecorator implements Decorator {

	public PlainDecorator() {
	}

	@Override
	public String decorate(URI uri, Map<Metakey, String> meta) {
		return uri.toASCIIString();
	}

}
