package com.tower.service.domain;

import java.util.List;

public class PageResult<T extends IResult> extends AbsResult implements IResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6656398713432429279L;

	private int pageIndex;
	private int pageSize;
	private int total;
	private List<T> result;

	public PageResult(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public PageResult(int pageIndex, int pageSize, List<T> result) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.result = result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

}
