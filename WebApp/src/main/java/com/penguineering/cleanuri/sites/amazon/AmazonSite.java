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
package com.penguineering.cleanuri.sites.amazon;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.api.Canonizer;
import com.penguineering.cleanuri.api.Extractor;

import net.jcip.annotations.ThreadSafe;

/**
 * Site implementation for amazon.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
@ThreadSafe
public class AmazonSite implements Site {
	public static final String PREFIX = "http://www.amazon.de/dp/";

	public static AmazonSite getInstance() {
		return new AmazonSite();
	}

	private final Canonizer canonizer;
	private final Extractor extractor;

	private AmazonSite() {
		this.canonizer = new AmazonCanonizer();
		this.extractor = new AmazonExtractor();
	}

	@Override
	public String getId() {
		return "Amazon";
	}

	@Override
	public String getLabel() {
		return "Site transformer for amazon.de";
	}

	@Override
	public Canonizer getCanonizer() {
		return canonizer;
	}

	@Override
	public Extractor getExtractor() {
		return extractor;
	}

}
