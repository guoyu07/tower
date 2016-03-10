package ${package}.dto;

import com.tower.service.domain.IResult;

public class ${name}Dto implements IResult {
	
	private static final long serialVersionUID = 1L;
<#list fields as field>

	private ${field.y.type.simpleName} ${field.y.name};
	public ${field.y.type.simpleName} get${field.x}(){
		return ${field.y.name};
	}
	
	public void set${field.x}(${field.y.type.simpleName} ${field.y.name}){
		this.${field.y.name} = ${field.y.name};
	}
	
</#list>	
	
	/**
	 * 
	 * 通过拷贝粘土相关model对象的属性,
	 * 其相关属性直接从${name}拷贝
	 */
	
	public String toString(){
		throw new RuntimeException("必须重新实现");
	}
}
