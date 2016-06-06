package com.tower.service.rpc.impl;

import io.searchbox.core.SearchResult;

import com.google.gson.Gson;
import com.tower.service.rpc.IResult;

public class ESQResult extends SearchResult implements IResult {

	public ESQResult(Gson gson) {
		super(gson);
	}

	public ESQResult(ESQResult searchResult) {
		super(searchResult);
	}

}
