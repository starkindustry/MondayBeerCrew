package com.lurn2kode.gwt.vntful.client;

import java.util.HashMap;

import com.github.gwtbootstrap.client.ui.CellTable;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.lurn2kode.gwt.vntful.client.presenter.MainPresenter;

public class TableFactory {

	private static final String TABLE_STYLE_NAME = "table";
	private static final String COLUMN_STYLE_NAME = "cellTableRow";
	private static final String HEADER_STYLE_NAME = "headerTableRow";
	private static final String START_TIME_HEADER = "Start Date";
	private static final String STOP_TIME_HEADER = "Until";
	private static final String VENUE_HEADER = "Venue";
	private static final String CITY_HEADER = "City";
	private static final String TITLE_HEADER = "Title";
	private static final String DATE_TIME_COLUMN_WIDTH = "110px";

	public static CellTable<HashMap<String, String>> createTable() {
		CellTable<HashMap<String, String>> table = new CellTable<HashMap<String, String>>();

		table.setStyleDependentName(TABLE_STYLE_NAME, true);
		table.setStriped(true);

		table.addColumn(createDataColumn(MainPresenter.EVENT_TITLE),
				createHeaderColumn(TITLE_HEADER));
		table.addColumn(createDataColumn(MainPresenter.EVENT_CITY_NAME),
				createHeaderColumn(CITY_HEADER));
		table.addColumn(createDataColumn(MainPresenter.EVENT_VENUE_NAME),
				createHeaderColumn(VENUE_HEADER));

		TextColumn<HashMap<String, String>> startTimeColumn = createDataColumn(MainPresenter.EVENT_START_TIME);
		table.addColumn(startTimeColumn, createHeaderColumn(START_TIME_HEADER));
		table.setColumnWidth(startTimeColumn, DATE_TIME_COLUMN_WIDTH);

		TextColumn<HashMap<String, String>> stopTimeColumn = createDataColumn(MainPresenter.EVENT_STOP_TIME);
		table.addColumn(stopTimeColumn, createHeaderColumn(STOP_TIME_HEADER));
		table.setColumnWidth(stopTimeColumn, DATE_TIME_COLUMN_WIDTH);

		return table;
	}

	public static TextColumn<HashMap<String, String>> createDataColumn(
			final String key) {
		TextColumn<HashMap<String, String>> column = new TextColumn<HashMap<String, String>>() {

			@Override
			public String getValue(HashMap<String, String> object) {
				return object.get(key);
			}
		};
		column.setCellStyleNames(COLUMN_STYLE_NAME);
		return column;
	}

	public static Header<String> createHeaderColumn(final String title) {
		Header<String> header = new Header<String>(new TextCell()) {

			@Override
			public String getValue() {
				return title;
			}
		};
		header.setHeaderStyleNames(HEADER_STYLE_NAME);
		return header;
	}
}
