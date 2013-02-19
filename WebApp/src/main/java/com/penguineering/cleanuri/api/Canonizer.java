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

import net.jcip.annotations.ThreadSafe;

/**
 * A URI canonizer brings URIs to a canonized form, so that redundant
 * information is removed and URIs can be compared.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
@ThreadSafe
public interface Canonizer {
	/**
	 * Check if this canonizer is suitable for the provided URI.
	 * 
	 * @param uri
	 *            The URI to check.
	 * @return true if the URI can be canonized, false if not.
	 * @throws NullPointerException
	 *             if the URI argument is null
	 */
	public boolean isSuitable(URI uri);

	/**
	 * Canonize the provided URI. Two URIs are considered equal if their
	 * canonized forms are equal.
	 * 
	 * @param uri
	 *            The URI to canonize.
	 * @return The canonized URI.
	 * @throws NullPointerException
	 *             if the URI argument is null
	 * @throws IllegalArgumentException
	 *             if the URI cannot be canonized by the canonizer. In this case
	 *             isSuitable should return false and this exception must not be
	 *             thrown if isSuitable returns true.
	 */
	public URI canonize(URI uri);
}
