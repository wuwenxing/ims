package com.gwghk.ims.mis.gateway.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuDubboService;
import com.gwghk.ims.common.vo.system.SystemMenuVO;

/**
 * 菜单相关缓存处理
 * @author wayne
 *
 */
public class MenuCache {

	private static MisSystemMenuDubboService systemMenuDubboService;

	// 默认缓存时间-6小时
	private static final int time = 6*60*60*1000;
	
	// 默认fx,菜单所有公司共用
	private static final String key = CompanyEnum.fx.getId();
	
	public static void setSystemMenuDubboService(MisSystemMenuDubboService systemMenuDubboService) {
		MenuCache.systemMenuDubboService = systemMenuDubboService;
	}

	/**
	 * 缓存每个公司的菜单的Url
	 * Map<companyId, LazyRefreshable<Map<菜单Url,菜单Code>> dictMap
	 */
	private static Map<String, LazyRefreshable<Map<String, String>>> menuUrlMap = new HashMap<String, LazyRefreshable<Map<String, String>>>();

	/**
	 * 缓存每个公司的菜单的对象
	 * Map<companyId, LazyRefreshable<List<菜单对象>> dictMap
	 */
	private static Map<String, LazyRefreshable<List<SystemMenuVO>>> menuObjMap = new HashMap<String, LazyRefreshable<List<SystemMenuVO>>>();

	/**
	 * 缓存每个用户的菜单的对象
	 * Map<userId, LazyRefreshable<Map<companyId,List<菜单对象>>>> dictMap
	 */
//	private static Map<String, LazyRefreshable<Map<String,List<SystemMenuVO>>>> menuObjMapByUser = new HashMap<String, LazyRefreshable<Map<String,List<SystemMenuVO>>>>();

	/**
	 * 缓存每个角色的菜单的对象
	 * Map<roleId, LazyRefreshable<Map<companyId,List<菜单对象>>>> dictMap
	 */
	private static Map<String, LazyRefreshable<Map<String,List<SystemMenuVO>>>> menuObjMapByRole = new HashMap<String, LazyRefreshable<Map<String,List<SystemMenuVO>>>>();
	
	/**
	 * 功能：提取菜单Url列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<菜单Url,菜单Code>
	 */
	public static Map<String, String> getMenuUrlList() throws Exception{
		LazyRefreshable<Map<String, String>> lazy = MenuCache.menuUrlMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<Map<String, String>>(time){
				private static final long serialVersionUID = 1L;
				@Override
				protected Map<String, String> refresh() throws Exception{
					Map<String, String> resultList = new HashMap<String, String>();
					List<SystemMenuVO> menuList = systemMenuDubboService.findListByCompanyId(Long.parseLong(key)).getData();
					if(null !=menuList && menuList.size()>0){
						for (SystemMenuVO menu : menuList) {
							if(null != menu && StringUtils.isNotBlank(menu.getMenuUrl())){
								resultList.put(menu.getMenuUrl(), menu.getMenuCode());
							}
						}
					}
					return resultList;
				}
			};
			MenuCache.menuUrlMap.put(key, lazy);
		}
		Map<String, String> menuUrlList = lazy.get();
		return menuUrlList;
	}

	/**
	 * 功能：提取菜单列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<code, String> map
	 */
	public static List<SystemMenuVO> getMenuObjList() throws Exception{
		LazyRefreshable<List<SystemMenuVO>> lazy = MenuCache.menuObjMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<List<SystemMenuVO>>(time){
				private static final long serialVersionUID = 1L;
				@Override
				protected List<SystemMenuVO> refresh() throws Exception{
					List<SystemMenuVO> menuList = systemMenuDubboService.findListByCompanyId(Long.parseLong(key)).getData();
					if(null != menuList && menuList.size() > 0){
						return menuList;
					}else{
						return new ArrayList<SystemMenuVO>();
					}
				}
			};
			MenuCache.menuObjMap.put(key, lazy);
		}
		List<SystemMenuVO> menuObjList = lazy.get();
		return menuObjList;
	}
	
	/**
	 * 功能：提取菜单列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<code, String> map
	 */
//	private static Map<String, List<SystemMenuVO>> getMenuObjListByUser(final Long userId) throws Exception{
//		LazyRefreshable<Map<String,List<SystemMenuVO>>> lazy = MenuCache.menuObjMapByUser.get(userId+"");
//		if(lazy == null){
//			lazy = new LazyRefreshable<Map<String,List<SystemMenuVO>>>(time){
//				private static final long serialVersionUID = 1L;
//				@Override
//				protected Map<String,List<SystemMenuVO>> refresh() throws Exception{
//					Map<String,List<SystemMenuVO>> map = new HashMap<String,List<SystemMenuVO>>();
//					List<SystemMenuVO> menuList = systemMenuDubboService.findListByUserIdAndCompanyId(userId, Long.parseLong(key)).getData();
//					map.put(key, menuList);
//					return map;
//				}
//			};
//			MenuCache.menuObjMapByUser.put(userId+"", lazy);
//		}
//		Map<String,List<SystemMenuVO>> retu = lazy.get();
//		return retu;
//	}
	
	/**
	 * 功能：提取菜单列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<code, String> map
	 */
	private static Map<String, List<SystemMenuVO>> getMenuObjListByRole(final Long roleId) throws Exception{
		LazyRefreshable<Map<String,List<SystemMenuVO>>> lazy = MenuCache.menuObjMapByRole.get(roleId+"");
		if(lazy == null){
			lazy = new LazyRefreshable<Map<String,List<SystemMenuVO>>>(time){
				private static final long serialVersionUID = 1L;
				@Override
				protected Map<String,List<SystemMenuVO>> refresh() throws Exception{
					Map<String,List<SystemMenuVO>> map = new HashMap<String,List<SystemMenuVO>>();
					List<SystemMenuVO> menuList = systemMenuDubboService.findMenuListByRoleId(roleId).getData();
					map.put(key, menuList);
					return map;
				}
			};
			MenuCache.menuObjMapByRole.put(roleId+"", lazy);
		}
		Map<String,List<SystemMenuVO>> retu = lazy.get();
		return retu;
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refresh() throws Exception{
		String key = CompanyEnum.fx.getId();// 默认fx,菜单所有公司共用
		MenuCache.menuUrlMap.remove(key);
		MenuCache.menuObjMap.remove(key);
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refreshByRole(String roleId) throws Exception{
		MenuCache.menuObjMapByRole.remove(roleId);
	}

	/**
	 * 默认FX的companyId
	 * @return
	 * @throws Exception
	 */
	public static List<SystemMenuVO> findMenuListByRoleId(Long roleId) throws Exception{
		if(null == roleId){
			return new ArrayList<SystemMenuVO>();
		}
		String companyId = CompanyEnum.fx.getId();// 默认fx,菜单所有公司共用
		Map<String, List<SystemMenuVO>> map = MenuCache.getMenuObjListByRole(roleId);
		return map.get(companyId);
	}
	
	/**
	 * 根据用户Id查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, SystemMenuVO> findMenuMapByRoleId(Long roleId) throws Exception{
		if(null == roleId){
			return new HashMap<String, SystemMenuVO>();
		}
		Map<String, SystemMenuVO> map = new HashMap<String, SystemMenuVO>();
		List<SystemMenuVO> menuList = MenuCache.findMenuListByRoleId(roleId);
		if(null !=menuList && menuList.size()>0){
			for(SystemMenuVO menu: menuList){
				map.put(menu.getMenuUrl(), menu);
			}
		}
		return map;
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
//	public static void refreshByUser(String userId) throws Exception{
//		MenuCache.menuObjMapByUser.remove(userId);
//	}

	/**
	 * 默认FX的companyId
	 * @return
	 * @throws Exception
	 */
//	public static List<SystemMenuVO> findMenuListByUserId(Long userId) throws Exception{
//		String companyId = CompanyEnum.fx.getId();// 默认fx,菜单所有公司共用
//		Map<String, List<SystemMenuVO>> map = MenuCache.getMenuObjListByUser(userId);
//		return map.get(companyId);
//	}
	
	/**
	 * 根据用户Id查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
//	public static Map<String, SystemMenuVO> findMenuMapByUserId(Long userId) throws Exception{
//		Map<String, SystemMenuVO> map = new HashMap<String, SystemMenuVO>();
//		List<SystemMenuVO> menuList = MenuCache.findMenuListByUserId(userId);
//		if(null !=menuList && menuList.size()>0){
//			for(SystemMenuVO menu: menuList){
//				map.put(menu.getMenuUrl(), menu);
//			}
//		}
//		return map;
//	}
	
}
