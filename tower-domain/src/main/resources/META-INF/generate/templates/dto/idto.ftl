package ${package}.dto;

import com.tower.service.domain.IResult;

public class ${name}Dto implements IResult {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * 通过拷贝粘土相关model对象的属性,
	 * 其相关属性直接从${name}拷贝
	 */
	
	public String toString(){
		throw new RuntimeException("必须重新实现");
	}
}
