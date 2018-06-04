package com.gwghk.ims.bos.web.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.bos.web.common.context.UserContext;
import com.gwghk.ims.bos.web.common.lazy.LazyRefreshable;
import com.gwghk.ims.common.dto.system.SystemDictDTO;
import com.gwghk.ims.common.inf.sys.SystemDictDubboService;

/**
 * 数据库字典缓存处理
 * 
 * @author wayne
 *
 */
public class DictCache {

	private static final Logger logger = LoggerFactory.getLogger(DictCache.class);

	private static SystemDictDubboService systemDictDubboService;

	public static void setSystemDictDubboService(SystemDictDubboService systemDictDubboService) {
		DictCache.systemDictDubboService = systemDictDubboService;
	}

	/**
	 * Map<companyId + "_" + dictCode, LazyRefreshable<数据字典实体> dictMap
	 */
	private static Map<String, LazyRefreshable<SystemDictDTO>> dictMap = new HashMap<String, LazyRefreshable<SystemDictDTO>>();
	
	/**
	 * Map<companyId + "_" + dictCode, LazyRefreshable<List<子节点数据字典实体>> subDictListMap
	 */
	private static Map<String, LazyRefreshable<List<SystemDictDTO>>> subDictListMap = new HashMap<String, LazyRefreshable<List<SystemDictDTO>>>();

	/**
	 * 功能：提取字典列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return: SystemDictReqDTO
	 */
	public static SystemDictDTO getDictEntity(final String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		LazyRefreshable<SystemDictDTO> lazy = DictCache.dictMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<SystemDictDTO>(10*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected SystemDictDTO refresh() throws Exception{
					SystemDictDTO dictEntity = systemDictDubboService.findByDictCode(dictCode, UserContext.get().getCompanyId()).getData();
					return dictEntity;
				}
			};
			DictCache.dictMap.put(key, lazy);
		}
		SystemDictDTO dictEntity = lazy.get();
		return dictEntity;
	}
	
	/**
	 * 功能：提取子字典列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:List<SystemDictReqDTO>
	 */
	public static List<SystemDictDTO> getSubDictList(final String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		LazyRefreshable<List<SystemDictDTO>> lazy = DictCache.subDictListMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<List<SystemDictDTO>>(10*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected List<SystemDictDTO> refresh() throws Exception{
					List<SystemDictDTO> returnDictList = new ArrayList<SystemDictDTO>();
					SystemDictDTO dictDto = systemDictDubboService.findByDictCode(dictCode, UserContext.get().getCompanyId()).getData();
					if(null != dictDto && "Y".equals(dictDto.getEnableFlag())){
						List<SystemDictDTO> dictList = systemDictDubboService.findListByParentDictCode(dictCode, UserContext.get().getCompanyId()).getData();
						for(SystemDictDTO dto: dictList){
							if(null != dictDto && "Y".equals(dictDto.getEnableFlag())){
								returnDictList.add(dto);
							}
						}
					}
					return returnDictList;
				}
			};
			DictCache.subDictListMap.put(key, lazy);
		}
		List<SystemDictDTO> subDictListMap = lazy.get();
		return subDictListMap;
	}
	
	public static String getDictNameByDictCode(String dictCode) throws Exception {
		SystemDictDTO entity = getDictEntity(dictCode);
		if (null != entity) {
			return entity.getDictName();
		}
		logger.info("该数据字典code不存在:" + dictCode);
		return dictCode;
	}
	
	/**
	 * 当修改是调用此方法进行强制刷新
	 */
	public static void refresh(String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		DictCache.dictMap.remove(key);
	}
	
	/**
	 * 当修改是调用此方法进行强制刷新
	 */
	public static void refreshSubDictListMap(String dictCode) throws Exception{
		if(StringUtils.isNotBlank(dictCode)){
			String key = UserContext.get().getCompanyId() + "_" + dictCode;
			DictCache.subDictListMap.remove(key);
		}
	}

}
