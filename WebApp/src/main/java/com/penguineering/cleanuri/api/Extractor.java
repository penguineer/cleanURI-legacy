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
