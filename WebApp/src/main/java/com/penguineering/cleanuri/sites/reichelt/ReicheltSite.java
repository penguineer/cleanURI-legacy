package com.penguineering.cleanuri.sites.reichelt;

import net.jcip.annotations.ThreadSafe;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.api.Canonizer;
import com.penguineering.cleanuri.api.Extractor;

/**
 * Site implementation for reichelt.de.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
@ThreadSafe
public class ReicheltSite implements Site {
	public static ReicheltSite getInstance() {
		return new ReicheltSite();
	}

	private final Canonizer canonizer;
	private final Extractor extractor;

	private ReicheltSite() {
		this.canonizer = ReicheltCanonizer.getInstance();
		this.extractor = ReicheltExtractor.getInstance();
	}

	@Override
	public String getId() {
		return "Reichelt";
	}

	@Override
	public String getLabel() {
		return "Site transformer for reichelt.de";
	}

	@Override
	public Canonizer getCanonizer() {
		return this.canonizer;
	}

	@Override
	public Extractor getExtractor() {
		return this.extractor;
	}

}
