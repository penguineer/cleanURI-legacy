package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.Verbosity;

public class ReicheltSiteTestFrame {

	public static void main(String args[]) {
		final URI uri = URI
				.create("http://www.reichelt.de/index.html?ACTION=3;ARTICLE=45018;SEARCH=ATtiny");

		final Site site = ReicheltSite.getInstance();

		System.out.println(site.transform(uri, Verbosity.MINIMAL, "URI"));
	}
}
