package com.penguineering.cleanuri;

import java.net.URI;

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

	/**
	 * Check if the URI matches this site.
	 * 
	 * @param src
	 *            The URI to match.
	 * @return true, if the URI can be processed by this site, false if not.
	 * @throws NullPointerException
	 *             if the uri parameter is null
	 */
	// TODO use distinct matcher interface
	public boolean isMatch(URI uri);

	/**
	 * Transform a URI according to the parameters.
	 * 
	 * @param uri
	 *            The URI to be transformed.
	 * @param v
	 *            The {@link Verbosity}.
	 * @param target
	 *            The target, i.e. Plain, DokuWiki, …
	 * @param format
	 *            The output format.
	 * @return The transformation result.
	 */
	public String transform(URI uri, Verbosity v, String target);
}
