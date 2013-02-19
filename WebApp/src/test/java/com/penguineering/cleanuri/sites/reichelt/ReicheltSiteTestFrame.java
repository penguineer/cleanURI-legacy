package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;
import java.util.Map;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;

public class ReicheltSiteTestFrame {

	public static void main(String args[]) throws ExtractorException {
		final URI uri = URI
				.create("http://www.reichelt.de/Power-Induktivitaeten-SMD/L-PIS2408-100-/3/index.html?;ACTION=3;LA=2;ARTICLE=73087;GROUPID=3709;artnr=L-PIS2408+100%C2%B5;SID=13UL35O38AAAIAAF0s96896ddf17472f1c88daef7e4f67873a9d0");

		final Site site = ReicheltSite.getInstance();

		URI href = site.getCanonizer().canonize(uri);
		Map<Metakey, String> meta = site.getExtractor().extractMetadata(href);

		System.out.println(href);
		System.out.println(meta);
	}
}
