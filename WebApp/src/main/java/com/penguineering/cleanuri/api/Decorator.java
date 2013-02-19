package com.penguineering.cleanuri.api;

import java.net.URI;
import java.util.Map;

public interface Decorator {
	public String decorate(URI uri, Map<Metakey, String> meta);
}
