package com.tower.service.rule;

public interface IEngine<T> {

	public void refresh();

	public abstract T build();

	public abstract T build(String name);

	public void execute();

	public void execute(T session);
}