package com.lurn2kode.gwt.vntful.client;

import java.io.IOException;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lurn2kode.gwt.vntful.shared.EventModel;

@RemoteServiceRelativePath("eventModelService")
public interface EventModelService extends RemoteService {
	List<EventModel> getEventModelDetails(String keyword, String location,
			String radius, String sortOrder, String dateRange)
			throws IOException;
}
