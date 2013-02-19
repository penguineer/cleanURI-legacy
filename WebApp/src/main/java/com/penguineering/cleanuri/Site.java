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
