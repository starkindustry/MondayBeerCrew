package com.lurn2kode.gwt.vntful.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class FavoriteEvent extends GwtEvent<FavoriteEventHandler> {

	public static Type<FavoriteEventHandler> TYPE = new Type<FavoriteEventHandler>();

	@Override
	public Type<FavoriteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FavoriteEventHandler handler) {
		handler.onClickFavorites(this);
	}
}
