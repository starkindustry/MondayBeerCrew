package com.lurn2kode.gwt.vntful.client;

import com.github.gwtbootstrap.client.ui.ProgressBar;
import com.github.gwtbootstrap.client.ui.base.ProgressBarBase.Color;
import com.github.gwtbootstrap.client.ui.base.ProgressBarBase.Style;

public class ProgressBarFactory {

	private static final String PROGRESS_BAR_STYLE = "progressBar";
	private static final int PROGRESS_BAR_PERCENT = 100;

	public static ProgressBar createProgressBar() {
		ProgressBar bar = new ProgressBar();
		bar.setColor(Color.DEFAULT);
		bar.addStyle(Style.ANIMATED);
		bar.setPercent(PROGRESS_BAR_PERCENT);
		bar.addStyleDependentName(PROGRESS_BAR_STYLE);
		bar.setVisible(false);
		return bar;
	}
}
