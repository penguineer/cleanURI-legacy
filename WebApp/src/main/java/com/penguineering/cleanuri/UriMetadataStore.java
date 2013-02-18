package com.penguineering.cleanuri;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public enum UriMetadataStore {
	INSTANCE;

	private final Map<URI, Properties> uriProps;

	private UriMetadataStore() {
		uriProps = new ConcurrentHashMap<URI, Properties>();
	}

	public Properties getUriProperties(URI rec) {
		final Properties props = uriProps.get(rec);
		return (Properties) props.clone();
	}

	public void addUriProperties(URI rec, Properties props) {
		synchronized (uriProps) {
			Properties p = uriProps.get(rec);

			if (p == null) {
				p = new Properties();
				uriProps.put(rec, p);
			}

			for (Entry<Object, Object> e : props.entrySet())
				p.setProperty((String) e.getKey(), (String) e.getValue());
		}
	}

}
