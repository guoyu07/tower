package com.tower.service.rule;

import java.util.List;

import org.kie.api.command.Command;

import com.tower.service.domain.IDTO;

public interface IEngine<T extends IDTO> {
	public void init();
	public void refresh();
	public void execute(List<Command<T>> cmds);
}
