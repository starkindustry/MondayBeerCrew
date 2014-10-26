package com.lurn2kode.gwt.vntful.client;

import com.github.gwtbootstrap.client.ui.ListBox;
import com.lurn2kode.gwt.vntful.shared.SortOrder;

public class ListBoxFactory {

	private static final String LIST_BOX_STYLE_NAME = "listBox";

	public static final String RADIUS = "radius";
	public static final String SORT_ORDER = "sortOrder";
	public static final String RADIUS_PLACEHOLDER_OPTION = "Radius (km)";

	public ListBox getListBox(String type) {

		if (type.equalsIgnoreCase(RADIUS)) {
			return buildRadiusListBox();
		} else if (type.equalsIgnoreCase("sortOrder")) {
			return buildSortOrderListBox();
		}
		return new ListBox();
	}

	private ListBox buildRadiusListBox() {
		ListBox radiusBox = new ListBox();
		radiusBox.setStyleName(LIST_BOX_STYLE_NAME);
		radiusBox.addItem(RADIUS_PLACEHOLDER_OPTION);
		radiusBox.addItem("5");
		radiusBox.addItem("10");
		radiusBox.addItem("15");
		radiusBox.addItem("20");
		radiusBox.addItem("30");
		radiusBox.addItem("45");
		radiusBox.setWidth("auto");
		return radiusBox;
	}

	private ListBox buildSortOrderListBox() {
		ListBox listBox = new ListBox();
		listBox.setStyleName(LIST_BOX_STYLE_NAME);
		listBox.addItem("Sort by: ");
		for (SortOrder order : SortOrder.values()) {
			listBox.addItem(order.get());
		}
		return listBox;
	}

}
