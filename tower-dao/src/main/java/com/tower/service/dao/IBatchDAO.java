package com.tower.service.dao;

import java.util.List;
import java.util.Map;

/**
 * 数据访问层基础接口
 * 
 * @todo 建议添加一个nocache访问接口［for关键性业务］
 * @author alexzhu
 *
 * @param <T>
 */
public interface IBatchDAO<T> extends ICacheable<T> {

	/**
	   * @deprecated replace by @see
	   * public Integer batchInsert(List<String> cols,List<Map<String,Object>> datas, String tabNameSuffix);
	   * 批量插入
	   * 
	   * @param datas
	   * @param tabNameSuffix
	   *          表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
	   * @return
	   */
	  public Integer batchInsert(List<Map<String, Object>> datas, String tabNameSuffix);
	  /**
	   * 
	   * @param cols model属性集合
	   * @param datas 数据集合
	   * @param tabNameSuffix 表名后缀［用于支持表拆分机制，即：数据库操作时的表名规则为:tableName+"_"+tabNameSuffix］
	   * @return
	   */
	  public Integer batchInsert(List<String> cols,List<Map<String,Object>> datas, String tabNameSuffix);
	/**
	 * 批量查询
	 * 
	 * @param datas
	 *            查询条件
	 * @param tabNameSuffix
	 * @return
	 */
	public List<T> batchQuery(List<Map<String, Object>> datas,
			String tabNameSuffix);

	/**
	 * 批量更新
	 * 
	 * @param new_
	 *            更新值
	 * @param datas
	 *            更新条件
	 * @param tabNameSuffix
	 * @return
	 */
	public Integer batchUpdate(Map<String, Object> new_,
			List<Map<String, Object>> datas, String tabNameSuffix);

	/**
	 * 批量删除
	 * 
	 * @param datas
	 *            删除条件
	 * @param tabNameSuffix
	 * @return
	 */
	public Integer batchDelete(List<Map<String, Object>> datas,
			String tabNameSuffix);

	public String get$TKjtTabName(String tabNameSuffix);

}
