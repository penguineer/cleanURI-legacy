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
