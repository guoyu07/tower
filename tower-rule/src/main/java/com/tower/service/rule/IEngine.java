package com.tower.service.rule;

import java.util.List;

import com.tower.service.domain.IDTO;

public interface IEngine<T extends IDTO> {

	public void refresh();
	
	public void execute(List<T> fact);
}
