package com.tower.service.rule;

public interface IRule<T> {
	String getFile();
	void execute(T fact);
}
