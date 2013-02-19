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

public class ExtractorException extends Exception {
	private static final long serialVersionUID = 2088965193018062117L;
	private final URI uri;

	public ExtractorException(String message, URI uri) {
		this(message, null, uri);
	}

	public ExtractorException(String message, Throwable cause, URI uri) {
		super(message, cause);
		this.uri = uri;
	}

	public URI getURI() {
		return this.uri;
	}
}
