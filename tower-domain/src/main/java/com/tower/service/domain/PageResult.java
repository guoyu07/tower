package com.tower.service.domain;

import java.util.List;

public class PageResult<T> implements IResult{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6656398713432429279L;

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
	
	private int total = 0; // 总记录数
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	private int pageCount = 0; // 总页数
	
	private int pageIndex;
	private int pageSize;
	private List<T> result;
	
	public PageResult(int pageIndex, int pageSize){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	public PageResult(int pageIndex, int pageSize,List<T> result){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.result= result;
	}
	
}
