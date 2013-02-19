package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;
import java.util.Map;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.Verbosity;
import com.penguineering.cleanuri.api.Canonizer;
import com.penguineering.cleanuri.api.Extractor;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;

/**
 * Site implementation for reichelt.de.
 * 
 * @author Tux (tux@netz39.de)
 * 
 */
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
	public boolean isMatch(URI uri) {
		return canonizer.isSuitable(uri);
	}

	@Override
	public String transform(URI uri, Verbosity v, String target) {
		final URI href = canonizer.canonize(uri);

		StringBuilder result = new StringBuilder();
		if (v == Verbosity.DECORATED) {
			try {
				Map<Metakey, String> meta = extractor.extractMetadata(uri);

				result.append(createDokuwikiString(href, meta));
			} catch (ExtractorException e) {
				throw new RuntimeException(e);
			}
		} else
			result.append(href);

		return result.toString();
	}

	private String createDokuwikiString(URI href, Map<Metakey, String> meta) {
		StringBuilder result = new StringBuilder();

		result.append("[[");
		result.append(href.toASCIIString());
		result.append("|");
		result.append(meta.get(Metakey.ID));
		result.append("]] – ");
		result.append(meta.get(Metakey.NAME));

		return result.toString();
	}

}
