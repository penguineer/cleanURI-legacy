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

import com.penguineering.cleanuri.api.Canonizer;
import com.penguineering.cleanuri.api.Extractor;

/**
 * Interface for site-based URI processing.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
public interface Site {
	/**
	 * Get the unique ID of this site.
	 * 
	 * @return The site ID.
	 */
	public String getId();

	/**
	 * Get a label for this site.
	 * 
	 * @return the site label, null if none is set.
	 */
	public String getLabel();

	public Canonizer getCanonizer();

	public Extractor getExtractor();
}
