package com.penguineering.cleanuri.decorators;

import java.net.URI;
import java.util.Map;

import com.penguineering.cleanuri.api.Decorator;
import com.penguineering.cleanuri.api.Metakey;

public class DokuwikiDecorator implements Decorator {

	public DokuwikiDecorator() {
	}

	@Override
	public String decorate(URI uri, Map<Metakey, String> meta) {
		if (uri == null || meta == null)
			throw new NullPointerException("Arguments must not be null!");

		StringBuilder result = new StringBuilder();

		result.append("[[");
		result.append(uri);
		result.append("|");
		result.append(meta.get(Metakey.ID));
		result.append("]] – ");
		result.append(meta.get(Metakey.NAME));

		return result.toString();
	}

}
