package com.gwghk.ims.mis.gateway.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.inf.mis.sys.MisSystemDictDubboService;
import com.gwghk.ims.common.vo.system.SystemDictVO;

/**
 * 数据库字典相关缓存处理
 * 
 * @author wayne
 *
 */
public class DictCache {

	private static final Logger logger = LoggerFactory.getLogger(DictCache.class);

	// 默认缓存时间-6小时
	private static final int time = 6*60*60*1000;

	private static MisSystemDictDubboService systemDictDubboService;

	public static void setSystemDictDubboService(MisSystemDictDubboService systemDictDubboService) {
		DictCache.systemDictDubboService = systemDictDubboService;
	}

	/**
	 * Map<companyId + "_" + dictCode, LazyRefreshable<数据字典实体> dictMap
	 */
	private static Map<String, LazyRefreshable<SystemDictVO>> dictMap = new HashMap<String, LazyRefreshable<SystemDictVO>>();
	
	/**
	 * Map<companyId + "_" + dictCode, LazyRefreshable<List<子节点数据字典实体>> subDictListMap
	 */
	private static Map<String, LazyRefreshable<List<SystemDictVO>>> subDictListMap = new HashMap<String, LazyRefreshable<List<SystemDictVO>>>();

	/**
	 * 功能：提取字典列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return: SystemDictReqDTO
	 */
	public static SystemDictVO getDictEntity(final String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		LazyRefreshable<SystemDictVO> lazy = DictCache.dictMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<SystemDictVO>(time){
				private static final long serialVersionUID = 1L;
				@Override
				protected SystemDictVO refresh() throws Exception{
					SystemDictVO dictEntity = systemDictDubboService.findByDictCode(dictCode, UserContext.get().getCompanyId()).getData();
					return dictEntity;
				}
			};
			DictCache.dictMap.put(key, lazy);
		}
		SystemDictVO dictEntity = lazy.get();
		return dictEntity;
	}
	
	/**
	 * 功能：提取子字典列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:List<SystemDictReqDTO>
	 */
	public static List<SystemDictVO> getSubDictList(final String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		LazyRefreshable<List<SystemDictVO>> lazy = DictCache.subDictListMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<List<SystemDictVO>>(time){
				private static final long serialVersionUID = 1L;
				@Override
				protected List<SystemDictVO> refresh() throws Exception{
					List<SystemDictVO> returnDictList = new ArrayList<SystemDictVO>();
					SystemDictVO dictDto = systemDictDubboService.findByDictCode(dictCode, UserContext.get().getCompanyId()).getData();
					if(null != dictDto && "Y".equals(dictDto.getEnableFlag())){
						List<SystemDictVO> dictList = systemDictDubboService.findListByParentDictCode(dictCode, UserContext.get().getCompanyId()).getData();
						for(SystemDictVO dto: dictList){
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
		List<SystemDictVO> subDictListMap = lazy.get();
		return subDictListMap;
	}
	
	public static String getDictNameByDictCode(String dictCode) throws Exception {
		SystemDictVO entity = getDictEntity(dictCode);
		if (null != entity) {
			return entity.getDictNameCn();
		}
		logger.info("该数据字典code不存在:" + dictCode);
		return dictCode;
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refresh(String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		DictCache.dictMap.remove(key);
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refreshSubDictListMap(String dictCode) throws Exception{
		if(StringUtils.isNotBlank(dictCode)){
			String key = UserContext.get().getCompanyId() + "_" + dictCode;
			DictCache.subDictListMap.remove(key);
		}
	}

}
