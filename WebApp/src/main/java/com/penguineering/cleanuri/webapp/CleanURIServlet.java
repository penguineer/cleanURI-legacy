package com.penguineering.cleanuri.webapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jcip.annotations.ThreadSafe;

import com.penguineering.cleanuri.Site;
import com.penguineering.cleanuri.Verbosity;
import com.penguineering.cleanuri.sites.reichelt.ReicheltSite;

@ThreadSafe
public class CleanURIServlet extends HttpServlet {
	private static final long serialVersionUID = 8983389610237056848L;

	static final Set<Site> sites;

	static {
		sites = new HashSet<Site>();
		sites.add(ReicheltSite.getInstance());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get the request URI
		final URI uri;
		try {
			uri = retrieveUriParameter(request);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"On reading uri parameter: " + e.getMessage());
			return;
		}

		// get the verbosity
		final Verbosity v;
		try {
			v = retrieveVerbosityParameter(request);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"On reading verbosity parameter: " + e.getMessage());
			return;
		}

		// get the target platform
		final String target = retrieveTargetParameter(request);

		// retrieve the matching site
		final Site site = getSite(uri);

		if (site == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"No site matching the URI could be found!");
			return;
		}

		response.getWriter().println(site.transform(uri, v, target));
	}

	private Site getSite(URI uri) {
		if (uri == null)
			throw new NullPointerException("URI argument must not be null!");
		
		for (Site site : sites)
			if (site.isMatch(uri))
				return site;

		return null;
	}

	/**
	 * Retrieve the URI from the HTTP request.
	 * 
	 * @param request
	 *            the HTTP request.
	 * @return The URI from the request.
	 * @throws NullPointerException
	 *             if the request argument is null.
	 * @throws IllegalArgumentException
	 *             if the URI in the request is invalid.
	 */
	private static URI retrieveUriParameter(HttpServletRequest request) {
		if (request == null)
			throw new NullPointerException("Request argument must not be null!");

		final String p_uri = request.getParameter("uri");

		if (p_uri == null || p_uri.isEmpty())
			throw new IllegalArgumentException("Missing parameter: uri");

		try {
			return new URI(p_uri);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(
					"Illegal URI: " + e.getMessage(), e);
		}
	}

	/**
	 * Retrieve the verbosity from the request. Defaults to MINIMAL.
	 * 
	 * @return the target verbosity, MINIMAL if the parameter was ommited.
	 * @throws NullPointerException
	 *             if the request argument is null.
	 * @throws IllegalArgumentException
	 *             if the verbosity in the request is invalid.
	 */
	private static Verbosity retrieveVerbosityParameter(
			HttpServletRequest request) {
		if (request == null)
			throw new NullPointerException("Request argument must not be null!");

		final String p_v = request.getParameter("v");

		final Verbosity v;
		if (p_v == null || p_v.isEmpty())
			v = Verbosity.MINIMAL;
		else {
			v = Verbosity.valueOf(p_v.toUpperCase());
		}

		if (v == null)
			throw new IllegalArgumentException("Unknown verbosity!");

		return v;
	}

	/**
	 * Retrieve the target format from the request.
	 * 
	 * @return the target format.
	 * @throws NullPointerException
	 *             if the request argument is null.
	 */
	private static String retrieveTargetParameter(HttpServletRequest request) {
		if (request == null)
			throw new NullPointerException("Request argument must not be null!");

		return (String) request.getParameter("target");
	}

}
