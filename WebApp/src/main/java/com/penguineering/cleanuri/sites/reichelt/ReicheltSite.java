package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.Callable;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.UriMetadataStore;
import com.penguineering.cleanuri.Verbosity;
import com.penguineering.cleanuri.api.Canonizer;

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

	private ReicheltSite() {
		this.canonizer = ReicheltCanonizer.getInstance();
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
				Properties meta = UriMetadataStore.INSTANCE
						.getUriProperties(href);

				if (meta == null) {
					Callable<Properties> c = ReicheltMetaRetrievalWorker
							.forURI(href);
					meta = c.call();
					UriMetadataStore.INSTANCE.addUriProperties(href, meta);
				}

				result.append(createDokuwikiString(href, meta));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else
			result.append(href);

		return result.toString();
	}

	private String createDokuwikiString(URI href, Properties meta) {
		StringBuilder result = new StringBuilder();

		result.append("[[");
		result.append(href.toASCIIString());
		result.append("|");
		result.append(meta.get(ReicheltMetaRetrievalWorker.PAR_ARTID));
		result.append("]] – ");
		result.append(meta.get(ReicheltMetaRetrievalWorker.PAR_DESCRIPTION));

		return result.toString();
	}

}
