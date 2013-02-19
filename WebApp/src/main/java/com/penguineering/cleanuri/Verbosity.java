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

import net.jcip.annotations.Immutable;

/**
 * The target verbosity of a URI.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
@Immutable
public enum Verbosity {
	/**
	 * Minimal URI variant. This is absolutely clean!
	 */
	MINIMAL,
	/**
	 * Readable URI variant. This may contain some redundant information to make
	 * the URI more human readable.
	 */
	READABLE,
	/**
	 * Decorated URI. This may come with additional loading and processing
	 * overhead, but creates a URI decorated with meta-information.
	 */
	DECORATED;
}
