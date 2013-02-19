package com.penguineering.cleanuri.api;

import java.net.URI;

public class ExtractorException extends Exception {
	private static final long serialVersionUID = 2088965193018062117L;
	private final URI uri;

	public ExtractorException(String message, URI uri) {
		this(message, null, uri);
	}

	public ExtractorException(String message, Throwable cause, URI uri) {
		super(message, cause);
		this.uri = uri;
	}

	public URI getURI() {
		return this.uri;
	}
}
