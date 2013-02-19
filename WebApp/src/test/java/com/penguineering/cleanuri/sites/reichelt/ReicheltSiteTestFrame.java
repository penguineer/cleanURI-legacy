package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;
import java.util.Map;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.api.Decorator;
import com.penguineering.cleanuri.api.ExtractorException;
import com.penguineering.cleanuri.api.Metakey;
import com.penguineering.cleanuri.decorators.DokuwikiDecorator;

public class ReicheltSiteTestFrame {

	public static void main(String args[]) throws ExtractorException {
		final URI uri = URI
				.create("http://www.reichelt.de/Interne-Festplatten-8-89cm-3-5-SATA/WD20EFRX/3/index.html?;ACTION=3;LA=3;ARTICLE=122928;GROUPID=720;SID=13UL35O38AAAIAAF0s96896ddf17472f1c88daef7e4f67873a9d0");

		final Site site = ReicheltSite.getInstance();

		URI href = site.getCanonizer().canonize(uri);
		Map<Metakey, String> meta = site.getExtractor().extractMetadata(href);

		Decorator decorator = new DokuwikiDecorator();
		System.out.println(decorator.decorate(href, meta));
	}
}
