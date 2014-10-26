package com.lurn2kode.gwt.vntful.shared;

import java.io.Serializable;

public class Image implements Serializable {

	private String caption;
	private Size medium;

	public String getCaption() {
		return caption;
	}

	public Size getMedium() {
		return medium;
	}
}
