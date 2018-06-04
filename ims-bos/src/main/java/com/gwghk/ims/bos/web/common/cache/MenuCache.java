package com.gwghk.ims.bos.web.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gwghk.ims.bos.web.common.context.Constants;
import com.gwghk.ims.bos.web.common.context.UserContext;
import com.gwghk.ims.bos.web.common.easyui.TreeBean;
import com.gwghk.ims.bos.web.common.easyui.TreeBeanUtil;
import com.gwghk.ims.bos.web.common.lazy.LazyRefreshable;
import com.gwghk.ims.common.dto.system.SystemMenuDTO;
import com.gwghk.ims.common.enums.CompanyEnum;
import com.gwghk.ims.common.inf.sys.SystemMenuDubboService;

import net.sf.json.JSONObject;

/**
 * 菜单缓存处理
 * @author wayne
 *
 */
public class MenuCache {

	private static SystemMenuDubboService systemMenuDubboService;

	public static void setSystemMenuDubboService(SystemMenuDubboService systemMenuDubboService) {
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
	private static Map<String, LazyRefreshable<List<SystemMenuDTO>>> menuObjMap = new HashMap<String, LazyRefreshable<List<SystemMenuDTO>>>();

	/**
	 * 缓存每个用户的菜单的对象
	 * Map<userId, LazyRefreshable<Map<companyId,List<菜单对象>>>> dictMap
	 */
	private static Map<String, LazyRefreshable<Map<String,List<SystemMenuDTO>>>> menuObjMapByUser = new HashMap<String, LazyRefreshable<Map<String,List<SystemMenuDTO>>>>();
	

	/**
	 * 功能：提取菜单Url列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<菜单Url,菜单Code>
	 */
	public static Map<String, String> getMenuUrlList(final String key) throws Exception{
		LazyRefreshable<Map<String, String>> lazy = MenuCache.menuUrlMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<Map<String, String>>(60*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected Map<String, String> refresh() throws Exception{
					Map<String, String> resultList = new HashMap<String, String>();
					List<SystemMenuDTO> menuList = systemMenuDubboService.findListByCompanyId(Long.parseLong(key)).getData();
					if(null !=menuList && menuList.size()>0){
						for (SystemMenuDTO menu : menuList) {
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
	 * 默认当前登录的companyId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMenuUrlList() throws Exception{
		return MenuCache.getMenuUrlList(UserContext.get().getCompanyId()+"");
	}

	/**
	 * 功能：提取菜单列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<code, String> map
	 */
	public static List<SystemMenuDTO> getMenuObjList(final String key) throws Exception{
		LazyRefreshable<List<SystemMenuDTO>> lazy = MenuCache.menuObjMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<List<SystemMenuDTO>>(60*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected List<SystemMenuDTO> refresh() throws Exception{
					List<SystemMenuDTO> menuList = systemMenuDubboService.findListByCompanyId(Long.parseLong(key)).getData();
					if(null != menuList && menuList.size() > 0){
						return menuList;
					}else{
						return new ArrayList<SystemMenuDTO>();
					}
				}
			};
			MenuCache.menuObjMap.put(key, lazy);
		}
		List<SystemMenuDTO> menuObjList = lazy.get();
		return menuObjList;
	}

	/**
	 * 默认当前登录的companyId
	 * @return
	 * @throws Exception
	 */
	public static List<SystemMenuDTO> getMenuObjList() throws Exception{
		return MenuCache.getMenuObjList(UserContext.get().getCompanyId()+"");
	}
	
	/**
	 * 功能：提取菜单列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<code, String> map
	 */
	public static Map<String, List<SystemMenuDTO>> getMenuObjListByUser(final String userId) throws Exception{
		LazyRefreshable<Map<String,List<SystemMenuDTO>>> lazy = MenuCache.menuObjMapByUser.get(userId);
		if(lazy == null){
			lazy = new LazyRefreshable<Map<String,List<SystemMenuDTO>>>(60*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected Map<String,List<SystemMenuDTO>> refresh() throws Exception{
					Map<String,List<SystemMenuDTO>> map = new HashMap<String,List<SystemMenuDTO>>();
					for(CompanyEnum companyEnum: CompanyEnum.getList()){
						String comanyId = companyEnum.getLabelKey();
						List<SystemMenuDTO> menuList = systemMenuDubboService.findListByUserIdAndCompanyId(Long.parseLong(userId), Long.parseLong(comanyId)).getData();
						map.put(comanyId, menuList);
					}
					return map;
				}
			};
			MenuCache.menuObjMapByUser.put(userId, lazy);
		}
		Map<String,List<SystemMenuDTO>> retu = lazy.get();
		return retu;
	}

	/**
	 * 默认当前登录的companyId
	 * @return
	 * @throws Exception
	 */
	public static List<SystemMenuDTO> getMenuObjListByUser2(String userId) throws Exception{
		String companyId = UserContext.get().getCompanyId() + "";
		Map<String, List<SystemMenuDTO>> map = MenuCache.getMenuObjListByUser(userId);
		return map.get(companyId);
	}
	
	/**
	 * 指定的companyId
	 * @return
	 * @throws Exception
	 */
	public static List<SystemMenuDTO> getMenuObjListByUser2(String userId, String companyId) throws Exception{
		Map<String, List<SystemMenuDTO>> map = MenuCache.getMenuObjListByUser(userId);
		return map.get(companyId);
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refresh() throws Exception{
		String key = UserContext.get().getCompanyId() + "";
		MenuCache.menuUrlMap.remove(key);
		MenuCache.menuObjMap.remove(key);
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refreshByUser(String userId) throws Exception{
		MenuCache.menuObjMapByUser.remove(userId);
	}
	
	/**
	 * 根据用户Id查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, SystemMenuDTO> findMenuMapByUserId(Long userId, Long companyId) throws Exception{
		Map<String, SystemMenuDTO> map = new HashMap<String, SystemMenuDTO>();
		List<SystemMenuDTO> menuList = MenuCache.getMenuObjListByUser2(userId+"", companyId+"");
		if(null !=menuList && menuList.size()>0){
			for(SystemMenuDTO menu: menuList){
				map.put(menu.getMenuUrl(), menu);
			}
		}
		return map;
	}
	
	/**
	 * 登陆后-通过userId提取菜单列表
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param showFlag 若没有权限，是否显示在菜单列表
	 * @param userId
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单,为空不过滤
	 * @return
	 */
	public static List<SystemMenuDTO> getMenuList(boolean hasTopTag, boolean hasMenu, boolean hasFun, boolean showFlag, Long userId, String topTagMenuCode) throws Exception{
		// 1、根据userId，查询拥有的menuId集合
		List<SystemMenuDTO> menuUserList = MenuCache.getMenuObjListByUser2(userId+"");
		Map<String, String> menuCodeMap = new HashMap<String, String>();
		if (menuUserList != null && menuUserList.size() > 0) {
			for(SystemMenuDTO menu : menuUserList){
				menuCodeMap.put(menu.getMenuCode(), menu.getParentMenuCode());
			}
		}
		// 2、查询所有菜单集合
		List<SystemMenuDTO> menuList = MenuCache.getMenuObjList();
		// 3、转MenuBean对象集合，如果在menuIdList里，设置勾选复选框
		List<SystemMenuDTO> returnList = new ArrayList<SystemMenuDTO>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuDTO row : menuList) {
				if(!hasTopTag && "0".equals(row.getMenuType())){
					// 如果不包含顶部页签，则跳过此次循环
					continue;
				}
				if(!hasMenu && "1".equals(row.getMenuType())){
					// 如果不包含菜单，则跳过此次循环
					continue;
				}
				if(!hasFun && "2".equals(row.getMenuType())){
					// 如果不包含功能，则跳过此次循环
					continue;
				}
				// 若没有权限，是否显示在菜单列表
				if(!showFlag){
					if(null == menuCodeMap.get(row.getMenuCode())){
						// 如果没有权限，则跳过此次循环
						continue;
					}
				}
				
				// 查询该节点的顶层节点，并判断是否是指定的topTagMenuCode
				if(StringUtils.isNotBlank(topTagMenuCode)){
					String parentCode = row.getMenuCode();
					while(StringUtils.isNotBlank(parentCode)){
						String temp = menuCodeMap.get(parentCode);
						if(StringUtils.isNotBlank(temp)){
							parentCode = temp;
						}else{
							if(topTagMenuCode.equals(parentCode)){
								returnList.add(row);
							}
							break;
						}
					}
				}else{
					returnList.add(row);
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 提取树形菜单
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单,为空不过滤
	 * @return
	 */
	public static List<TreeBean> getMenuTreeJson(boolean hasTopTag, boolean hasMenu, boolean hasFun, String topTagMenuCode) throws Exception{
		// 1、查询所有菜单集合
		List<SystemMenuDTO> menuList = MenuCache.getMenuObjList();
		Map<String, String> menuCodeMap = new HashMap<String, String>();
		if(null !=menuList && menuList.size()>0){
			for(SystemMenuDTO menu : menuList){
				menuCodeMap.put(menu.getMenuCode(), menu.getParentMenuCode());
			}
		}
		// 2、转MenuBean对象集合
		List<TreeBean> menuBeanList = new ArrayList<TreeBean>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuDTO row : menuList) {
				if(!hasTopTag && "0".equals(row.getMenuType())){
					// 如果不包含顶部页签，则跳过此次循环
					continue;
				}
				if(!hasMenu && "1".equals(row.getMenuType())){
					// 如果不包含菜单，则跳过此次循环
					continue;
				}
				if(!hasFun && "2".equals(row.getMenuType())){
					// 如果不包含功能，则跳过此次循环
					continue;
				}
				TreeBean menuBean = new TreeBean();
				menuBean.setText(row.getMenuName());
				menuBean.setId(row.getMenuCode());
				menuBean.setParentId(row.getParentMenuCode());
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("type", row.getMenuType());
				jsonObj.put("code", row.getMenuCode());
				jsonObj.put("url", row.getMenuUrl());
				menuBean.setAttributes(jsonObj);

				// 查询该节点的顶层节点，并判断是否是指定的topTagMenuCode
				if(StringUtils.isNotBlank(topTagMenuCode)){
					String parentCode = row.getMenuCode();
					while(StringUtils.isNotBlank(parentCode)){
						String temp = menuCodeMap.get(parentCode);
						if(StringUtils.isNotBlank(temp)){
							parentCode = temp;
						}else{
							if(topTagMenuCode.equals(parentCode)){
								menuBeanList.add(menuBean);
							}
							break;
						}
					}
				}else{
					menuBeanList.add(menuBean);
				}
				
			}
		}
		return TreeBeanUtil.formatTreeBean(menuBeanList, false);
	}
	
	/**
	 * 通过userId提取树形菜单,并设置checkbox是否勾选
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param showFlag 若没有权限，是否显示在树形结构里
	 * @param userId 选择的用户id
	 * @param loginUserId 登录用户的id
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单,为空不过滤
	 * @return
	 */
	public static List<TreeBean> getMenuTreeJsonByUserId(boolean hasTopTag, boolean hasMenu, boolean hasFun, boolean showFlag, Long userId, Long loginUserId, String topTagMenuCode) throws Exception{
		// 1、根据userId，查询拥有的menuId集合
		List<SystemMenuDTO> menuUserList = MenuCache.getMenuObjListByUser2(userId+"");
		Map<String, String> menuCodeMap = new HashMap<String, String>();
		if (menuUserList != null && menuUserList.size() > 0) {
			for(SystemMenuDTO menu : menuUserList){
				menuCodeMap.put(menu.getMenuCode(), menu.getParentMenuCode());
			}
		}
		// 2、查询菜单集合
		List<SystemMenuDTO> menuList = null;
		if(Constants.superAdmin.equals(UserContext.get().getLoginNo())){
			// 查询所有菜单集合
			menuList = MenuCache.getMenuObjList();
		}else{
			// 当前登录用户拥有的菜单集合
			menuList = MenuCache.getMenuObjListByUser2(loginUserId+"");
		}
		
		// 3、转MenuBean对象集合，如果在menuIdList里，设置勾选复选框
		List<TreeBean> menuBeanList = new ArrayList<TreeBean>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuDTO row : menuList) {
				if(!"Y".equals(row.getEnableFlag())){
					// 如果被禁用，则跳过此次循环
					continue;
				}
				if(!hasTopTag && "0".equals(row.getMenuType())){
					// 如果不包含顶部页签，则跳过此次循环
					continue;
				}
				if(!hasMenu && "1".equals(row.getMenuType())){
					// 如果不包含菜单，则跳过此次循环
					continue;
				}
				if(!hasFun && "2".equals(row.getMenuType())){
					// 如果不包含功能，则跳过此次循环
					continue;
				}
				// 若没有权限，是否显示在树形结构里
				if(!showFlag){
					if(null == menuCodeMap.get(row.getMenuCode())){
						// 如果没有权限，则跳过此次循环
						continue;
					}
				}
				
				TreeBean menuBean = new TreeBean();
				menuBean.setText(row.getMenuName());
				menuBean.setId(row.getMenuCode());
				menuBean.setParentId(row.getParentMenuCode());
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("type", row.getMenuType());
				jsonObj.put("code", row.getMenuCode());
				jsonObj.put("url", row.getMenuUrl());
				menuBean.setAttributes(jsonObj);
				if(null != menuCodeMap.get(row.getMenuCode())){
					menuBean.setChecked(true);
				}
				
				// 查询该节点的顶层节点，并判断是否是指定的topTagMenuCode
				if(StringUtils.isNotBlank(topTagMenuCode)){
					String parentCode = row.getMenuCode();
					while(StringUtils.isNotBlank(parentCode)){
						String temp = menuCodeMap.get(parentCode);
						if(StringUtils.isNotBlank(temp)){
							parentCode = temp;
						}else{
							if(topTagMenuCode.equals(parentCode)){
								menuBeanList.add(menuBean);
							}
							break;
						}
					}
				}else{
					menuBeanList.add(menuBean);
				}
				
			}
		}
		return TreeBeanUtil.formatTreeBean(menuBeanList, false);
	}
	
	/**
	 * 返回有权限的菜单集合
	 * Map<key=user_id+","+menu_id, user_no>
	 * @param companyId
	 * @return
	 */
	public static Map<String, String> findUserMenuMap(){
		Map<String, String> map = new HashMap<String, String>();
		List<Object> paramList = new ArrayList<Object>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
		return map;
	}
	
}
