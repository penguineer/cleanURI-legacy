package com.penguineering.cleanuri.sites.reichelt;

import java.net.URI;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.Verbosity;

public class ReicheltSiteTestFrame {

	public static void main(String args[]) {
		final URI uri = URI
				.create("http://www.reichelt.de/Programmer-Entwicklungstools/AT-AVR-ISP/3/index.html?;ACTION=3;LA=2;ARTICLE=45040;GROUPID=2969;artnr=AT+AVR+ISP;SID=11T2EP9H8AAAIAAFZX9wo00382e81eab2abba2001cc172384a41b");

		final Site site = ReicheltSite.getInstance();

		System.out.println(site.transform(uri, Verbosity.DECORATED, "URI"));
	}
}
