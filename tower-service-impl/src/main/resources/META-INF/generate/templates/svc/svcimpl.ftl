package ${package}.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.BeanUtils;

import ${package}.dao.model.${name};
import ${package}.dto.${name}Dto;
import ${package}.I${name}Service;
import ${package}.dao.I${name}DAO;

import com.tower.service.domain.IntegerResult;
<#if pkType="Integer">	
<#elseif pkType="java.math.BigInteger">
import java.math.BigInteger;
import com.tower.service.domain.BigIntegerResult;
<#elseif pkType="String">
import com.tower.service.domain.StringResult;
<#else>
import com.tower.service.domain.LongResult;
</#if>
import com.tower.service.domain.ListResult;
import com.tower.service.domain.PageResult;
import com.tower.service.domain.ServiceResponse;
import com.tower.service.impl.AbsServiceImpl;

public class ${name}ServiceImpl extends AbsServiceImpl<${name}Dto> implements I${name}Service<${name}Dto> {

	@Resource(name="${name}")
	private I${name}DAO<${name}> i${name}DAO;
	
	@Override
	<#if pkType="Integer">	
	public ServiceResponse<IntegerResult> insert(Map<String, Object> params) {
		ServiceResponse<IntegerResult> response = new ServiceResponse<IntegerResult>();
		${name} model = new ${name}();
		try {
			BeanUtilsBean2.getInstance().copyProperties(model, params);
			Integer id = i${name}DAO.insert(model, null);
			IntegerResult result = new IntegerResult(id);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<BigIntegerResult> insert(Map<String, Object> params) {
		ServiceResponse<BigIntegerResult> response = new ServiceResponse<BigIntegerResult>();
		${name} model = new ${name}();
		try {
			BeanUtilsBean2.getInstance().copyProperties(model, params);
			BigInteger id = i${name}DAO.insert(model, null);
			BigIntegerResult result = new BigIntegerResult(id);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	<#elseif pkType="String">
	public ServiceResponse<StringResult> insert(Map<String, Object> params) {
		ServiceResponse<StringResult> response = new ServiceResponse<StringResult>();
		${name} model = new ${name}();
		try {
			BeanUtilsBean2.getInstance().copyProperties(model, params);
			String id = i${name}DAO.insert(model, null);
			StringResult result = new StringResult(id);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	<#else>
	public ServiceResponse<LongResult> insert(Map<String, Object> params) {
		ServiceResponse<LongResult> response = new ServiceResponse<LongResult>();
		${name} model = new ${name}();
		try {
			BeanUtilsBean2.getInstance().copyProperties(model, params);
			Long id = i${name}DAO.insert(model, null);
			LongResult result = new LongResult(id);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	</#if>
	}
	
	@Override
	<#if pkType="Integer">	
	public ServiceResponse<IntegerResult> deleteById(Integer id) {
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<IntegerResult> deleteById(BigInteger id) {
	<#elseif pkType="String">
	public ServiceResponse<IntegerResult> deleteById(String id) {
	<#else>
	public ServiceResponse<IntegerResult> deleteById(Long id) {
	</#if>
		ServiceResponse<IntegerResult> response = new ServiceResponse<IntegerResult>();
		try {
			Integer eft = i${name}DAO.deleteById(id, null);
			IntegerResult result = new IntegerResult(eft);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	}
	
	@Override
	public ServiceResponse<IntegerResult> deleteByMap(Map<String, Object> params) {
		ServiceResponse<IntegerResult> response = new ServiceResponse<IntegerResult>();
		try {
			Integer eft = i${name}DAO.deleteByMap(params, null);
			IntegerResult result = new IntegerResult(eft);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
			logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(), e);
		}
		return response;
	}
	
	@Override
	<#if pkType="Integer">	
	public ServiceResponse<IntegerResult> updateById(Integer id, Map<String, Object> newValue) {
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<IntegerResult> updateById(BigInteger id, Map<String, Object> newValue) {
	<#elseif pkType="String">
	public ServiceResponse<IntegerResult> updateById(String id, Map<String, Object> newValue) {
	<#else>
	public ServiceResponse<IntegerResult> updateById(Long id, Map<String, Object> newValue) {
	</#if>
		ServiceResponse<IntegerResult> response = new ServiceResponse<IntegerResult>();
		try {
			Integer eft = i${name}DAO.updateById(id,newValue, null);
			IntegerResult result = new IntegerResult(eft);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	}
	
	@Override
	public ServiceResponse<IntegerResult> updateByMap(
			Map<String, Object> newValue, Map<String, Object> conds) {
		ServiceResponse<IntegerResult> response = new ServiceResponse<IntegerResult>();
		try {
			Integer eft = i${name}DAO.updateByMap(newValue, conds, null);
			IntegerResult result = new IntegerResult(eft);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
			logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(), e);
		}
		return response;
	}
	
	@Override
	<#if pkType="Integer">	
	public ServiceResponse<${name}Dto> queryById(Integer id) {
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<${name}Dto> queryById(BigInteger id) {
	<#elseif pkType="String">
	public ServiceResponse<${name}Dto> queryById(String id) {
	<#else>
	public ServiceResponse<${name}Dto> queryById(Long id) {
	</#if>
		ServiceResponse<${name}Dto> response = new ServiceResponse<${name}Dto>();
		try {
			${name} result = i${name}DAO.queryById(id,null);
			if (result != null) {
				${name}Dto dto = new ${name}Dto();
				BeanUtils.copyProperties(result, dto);
				response.setResult(dto);
			}
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	}
	
	@Override
	<#if pkType="Integer">
	public ServiceResponse<${name}Dto> queryById(Integer id, Boolean master) {
	<#elseif pkType="java.math.BigInteger">
	public ServiceResponse<${name}Dto> queryById(BigInteger id, Boolean master) {
	<#elseif pkType="String">
	public ServiceResponse<${name}Dto> queryById(String id, Boolean master) {
	<#else>
	public ServiceResponse<${name}Dto> queryById(Long id, Boolean master) {
	</#if>
		ServiceResponse<${name}Dto> response = new ServiceResponse<${name}Dto>();
		try {
			${name} result = i${name}DAO.queryById(id,master,null);
			if (result != null) {
				${name}Dto dto = new ${name}Dto();
				BeanUtils.copyProperties(result, dto);
				response.setResult(dto);
			}
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	}
	
	@Override
	public ServiceResponse<ListResult<${name}Dto>> queryByMap(
			Map<String, Object> params) {
		ServiceResponse<ListResult<${name}Dto>> response = new ServiceResponse<ListResult<${name}Dto>>();
		try {
			List<${name}> resultlist = i${name}DAO.queryByMap(params, null);
			List<${name}Dto> list = transfter(resultlist);
			if(list!=null){
				response.setResult(new ListResult<${name}Dto>(list));
			}
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
			logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ServiceResponse<ListResult<${name}Dto>> queryByMap(
			Map<String, Object> params, Boolean master) {
		ServiceResponse<ListResult<${name}Dto>> response = new ServiceResponse<ListResult<${name}Dto>>();
		try {
			List<${name}> resultlist = i${name}DAO.queryByMap(params, master, null);
			List<${name}Dto> list = transfter(resultlist);
			if(list!=null){
				response.setResult(new ListResult<${name}Dto>(list));
			}
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
			logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(), e);
		}
		return response;
	}
	
	@Override
	public ServiceResponse<IntegerResult> count(Map<String, Object> params) {
		ServiceResponse<IntegerResult> response = new ServiceResponse<IntegerResult>();
		try {
			Integer cnt = i${name}DAO.countByMap(params,null);
			IntegerResult result = new IntegerResult(cnt);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	}
	
	@Override
	public ServiceResponse<IntegerResult> count(Map<String, Object> params,
			Boolean master) {
		ServiceResponse<IntegerResult> response = new ServiceResponse<IntegerResult>();
		try {
			Integer cnt = i${name}DAO.countByMap(params,master,null);
			IntegerResult result = new IntegerResult(cnt);
			response.setResult(result);
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
		    logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
		}
		return response;
	}
	
	@Override
	public ServiceResponse<PageResult<${name}Dto>> pageByMap(
			Map<String, Object> params, int pageIndex, int pageSize) {
		ServiceResponse<PageResult<${name}Dto>> response = new ServiceResponse<PageResult<${name}Dto>>();
		try {
			List<${name}> resultlist = i${name}DAO.pageQuery(params, pageIndex,
					pageSize, null);
			List<${name}Dto> list = transfter(resultlist);
			if(list!=null){
				PageResult<${name}Dto> pager = new PageResult<${name}Dto>(pageIndex,
						pageSize, list);
				response.setResult(pager);
			}
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
			logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ServiceResponse<PageResult<${name}Dto>> pageByMap(
			Map<String, Object> params, int pageIndex, int pageSize,
			Boolean master) {
		ServiceResponse<PageResult<${name}Dto>> response = new ServiceResponse<PageResult<${name}Dto>>();
		try {
			List<${name}> resultlist = i${name}DAO.pageQuery(params, pageIndex,
					pageSize, master, null);
			List<${name}Dto> list = transfter(resultlist);
			if(list!=null){
				PageResult<${name}Dto> pager = new PageResult<${name}Dto>(pageIndex,
						pageSize, list);
				response.setResult(pager);
			}
		} catch (Exception e) {
			response.setCode(ServiceResponse.FAILURE);
			response.setMsg(e.getMessage());
			logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(), e);
		}
		return response;
	}
	
	private List<${name}Dto> transfter(List<${name}> resultList) {
		List<${name}Dto> list = null;
		if (resultList != null && resultList.size() > 0) {
			list = new ArrayList<${name}Dto>();
			for (${name} result : resultList) {
				try {
					${name}Dto dto = new ${name}Dto();
					BeanUtilsBean2.getInstance().copyProperties(dto, result);
					list.add(dto);
				} catch (Exception e) {
					logger.error(ServiceResponse.SYSTEM_ERROR + e.getMessage(),e);
				}
			}
		}
		return list;
	}
}
