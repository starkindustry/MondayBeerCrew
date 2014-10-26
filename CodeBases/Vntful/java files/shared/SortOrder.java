package com.lurn2kode.gwt.vntful.shared;

import java.io.Serializable;

public enum SortOrder implements Serializable {

	POPULARITY("popularity"), DATE("date"), RELEVANCE("relevance");

	private String order;

	private SortOrder(String order) {
		this.order = order;
	}

	public String get() {
		return order;
	}

	public static boolean contains(String value) {
		for (SortOrder order : SortOrder.values()) {
			if (order.get().equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
}
