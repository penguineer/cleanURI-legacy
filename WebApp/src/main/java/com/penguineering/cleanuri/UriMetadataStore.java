package com.penguineering.cleanuri;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

import com.penguineering.cleanuri.api.Metakey;

@ThreadSafe
public enum UriMetadataStore {
	INSTANCE;

	private final Map<URI, Map<Metakey, String>> uriProps;

	private UriMetadataStore() {
		uriProps = new ConcurrentHashMap<URI, Map<Metakey, String>>();
	}

	public Map<Metakey, String> getUriProperties(URI rec) {
		final Map<Metakey, String> props = uriProps.get(rec);
		return props == null ? null : new HashMap<Metakey, String>(props);
	}

	public void addUriProperties(URI rec, Map<Metakey, String> props) {
		synchronized (uriProps) {
			Map<Metakey, String> p = uriProps.get(rec);

			if (p == null) {
				p = new HashMap<Metakey, String>();
				uriProps.put(rec, p);
			}

			p.putAll(props);
		}
	}

}
