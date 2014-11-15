package com.lurn2kode.gwt.vntful.client;

import com.github.gwtbootstrap.client.ui.Brand;
import com.github.gwtbootstrap.client.ui.Dropdown;
import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavWidget;
import com.github.gwtbootstrap.client.ui.Navbar;
import com.github.gwtbootstrap.client.ui.constants.Alignment;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.NavbarPosition;

public class NavBarBuilder {
	private static NavBarBuilder navBarInstance = null;
		
	private NavBarBuilder() {
	}

	public static NavBarBuilder getNavBarBuilder() {
		if (navBarInstance == null) {
			navBarInstance = new NavBarBuilder();
		}

		return navBarInstance;
	}

	public void createNavBar(Navbar navbar, Dropdown recentSearches, NavWidget favEvents, NavWidget logOutLink) {
		Nav navComponent = new Nav();
		Brand brand = new Brand("Eventful");
		brand.setStyleName("active", false);
		createNavWidget(favEvents, IconType.STAR, "Favorites");
		createNavWidget(logOutLink, IconType.POWER_OFF, "Log Out");

	    navComponent.add(recentSearches);
		navComponent.add(favEvents);
		navComponent.add(logOutLink);
		navComponent.setAlignment(Alignment.RIGHT);

		navbar.setPosition(NavbarPosition.TOP);
		navbar.add(brand);
		navbar.add(navComponent);
	}

	public NavWidget createNavWidget(NavWidget widget, IconType icon, String text) {
		widget.setIcon(icon);
		widget.setText(text);
		return widget;
	}
}
