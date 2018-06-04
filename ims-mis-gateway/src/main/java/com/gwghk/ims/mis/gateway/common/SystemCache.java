package com.gwghk.ims.mis.gateway.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemMenuRoleDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleColumnAuthDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemRoleDubboService;
import com.gwghk.ims.common.inf.mis.sys.MisSystemUserDubboService;
import com.gwghk.ims.common.vo.system.SystemMenuRoleVO;
import com.gwghk.ims.common.vo.system.SystemMenuVO;
import com.gwghk.ims.common.vo.system.SystemRoleColumnAuthVO;
import com.gwghk.ims.common.vo.system.SystemRoleVO;
import com.gwghk.ims.common.vo.system.SystemUserVO;

/**
 * 系统用户、菜单、角色、列等缓存处理
 * @author wayne
 *
 */
public class SystemCache {

	private static final Logger logger = LoggerFactory.getLogger(SystemCache.class);

	
	// 默认fx,菜单所有公司共用
//	private static final String companyId = CompanyEnum.fx.getId();
	
	private static SystemCache systemCache = null;

	// 默认缓存时间-6小时
	private static final int time = 6*60*60*1000;
	private LazyRefreshable<Void> loadDataFlag = null;
	
	private MisSystemUserDubboService misSystemUserDubboService;
	private MisSystemMenuDubboService misSystemMenuDubboService;
	private MisSystemRoleDubboService misSystemRoleDubboService;
	private MisSystemMenuRoleDubboService misSystemMenuRoleDubboService;
	private MisSystemRoleColumnAuthDubboService misSystemRoleColumnAuthDubboService;

	/**
	 * 缓存用户
	 */
	private List<SystemUserVO> systemUserList = new ArrayList<>();
	/**
	 * 缓存菜单
	 */
	private List<SystemMenuVO> systemMenuList = new ArrayList<>();
	/**
	 * 缓存角色
	 */
	private List<SystemRoleVO> systemRoleList = new ArrayList<>();
	/**
	 * 缓存菜单与角色
	 */
	private List<SystemMenuRoleVO> systemMenuRoleList = new ArrayList<>();
	/**
	 * 缓存角色与列
	 */
	private List<SystemRoleColumnAuthVO> systemRoleColumnAuthList = new ArrayList<>();

	
	public LazyRefreshable<Void> getLoadDataFlag() {
		return loadDataFlag;
	}
	public List<SystemUserVO> getSystemUserList() {
		return systemUserList;
	}
	public List<SystemMenuVO> getSystemMenuList() {
		if(null == systemMenuList || systemMenuList.size() <= 0){
			// 菜单一般不会为空，如果为空，说明加载异常，重新load
			this.loadData();
		}
		systemMenuList = systemMenuList.stream()
				.sorted(Comparator.comparing(SystemMenuVO::getMenuType).thenComparing(SystemMenuVO::getOrderCode))
				.collect(Collectors.toList());
		return systemMenuList;
	}
	public List<SystemRoleVO> getSystemRoleList() {
		return systemRoleList;
	}
	public List<SystemMenuRoleVO> getSystemMenuRoleList() {
		return systemMenuRoleList;
	}
	public List<SystemRoleColumnAuthVO> getSystemRoleColumnAuthList() {
		return systemRoleColumnAuthList;
	}

	static {
		
	}
	
	private SystemCache(){
		
	}
	
	public static SystemCache getInstance(){
		if(null == systemCache){
			synchronized (SystemCache.class) {
				if(null == systemCache){
					systemCache = new SystemCache(); // 线程安全
				}
			}
		}
		// 获取实例前，调用刷新方法
//		try {
//			systemCache.loadDataRefresh();
//		} catch (Exception e) {
//			logger.error("LazyRefreshable处理异常");
//		}
		return systemCache;
	}

	public void loadDataRefresh() throws Exception{
		if(loadDataFlag == null){
			loadDataFlag = new LazyRefreshable<Void>(time){
				private static final long serialVersionUID = 1L;
				@Override
				protected Void refresh() throws Exception{
					// 刷新数据
					loadData();
					return null;
				}
			};
		}
		loadDataFlag.get();
	}
	
	/**
	 * 加载缓存菜单权限
	 */
	public void init(
		MisSystemUserDubboService misSystemUserDubboService,
		MisSystemMenuDubboService misSystemMenuDubboService,
		MisSystemRoleDubboService misSystemRoleDubboService,
		MisSystemMenuRoleDubboService misSystemMenuRoleDubboService,
		MisSystemRoleColumnAuthDubboService misSystemRoleColumnAuthDubboService) {
			this.misSystemMenuDubboService = misSystemMenuDubboService;
			this.misSystemUserDubboService = misSystemUserDubboService;
			this.misSystemRoleDubboService = misSystemRoleDubboService;
			this.misSystemMenuRoleDubboService = misSystemMenuRoleDubboService;
			this.misSystemRoleColumnAuthDubboService = misSystemRoleColumnAuthDubboService;
			this.loadData();
	}
	
	/**
	 * 加载缓存菜单权限
	 */
	public void loadData(){
		try{
			List<SystemUserVO> systemUserListTemp = misSystemUserDubboService.findList(new SystemUserVO()).getData();
			List<SystemMenuVO> systemMenuListTemp = misSystemMenuDubboService.findList(new SystemMenuVO()).getData();
			List<SystemRoleVO> systemRoleListTemp = misSystemRoleDubboService.findList(new SystemRoleVO()).getData();
			List<SystemMenuRoleVO> systemMenuRoleListTemp = misSystemMenuRoleDubboService.findList(new SystemMenuRoleVO()).getData();
			List<SystemRoleColumnAuthVO> systemRoleColumnAuthListTemp = misSystemRoleColumnAuthDubboService.findList(new SystemRoleColumnAuthVO()).getData();
			if(null != systemUserListTemp){
				systemUserList = systemUserListTemp;
			}
			if(null != systemMenuListTemp){
				systemMenuList = systemMenuListTemp;
			}
			if(null != systemRoleListTemp){
				systemRoleList = systemRoleListTemp;
			}
			if(null != systemMenuRoleListTemp){
				systemMenuRoleList = systemMenuRoleListTemp;
			}
			if(null != systemRoleColumnAuthListTemp){
				systemRoleColumnAuthList = systemRoleColumnAuthListTemp;
			}
		}catch(Exception e){
			logger.error("菜单权限缓存处理异常");
		}
	}

	/**
	 * 功能：提取菜单Url列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<菜单Url,菜单Code>
	 */
	public Map<String, String> getMenuUrlList(){
		Map<String, String> map = new HashMap<String, String>();
		for(SystemMenuVO vo : systemMenuList){
			map.put(vo.getMenuUrl(), vo.getMenuCode());
		}
		return map;
	}
	
	/**
	 * 根据角色Id查询拥有的菜单权限-return List<SystemMenuVO>
	 * @param roleId
	 */
	public List<SystemMenuVO> findMenuListByRoleId(Long roleId) throws Exception{
		if(null == roleId){
			return new ArrayList<SystemMenuVO>();
		}
		// 该角色拥有的菜单ID集合
		List<Long> menuIdList = systemMenuRoleList.stream().filter(r -> roleId.equals(r.getRoleId()))
			.collect(() -> new ArrayList<Long>()
				, (lista, item) -> lista.add(item.getMenuId())
				, (lista, listb) -> lista.addAll(listb));
		
		List<SystemMenuVO> returnList = systemMenuList.stream()
				.filter(s -> menuIdList.contains(s.getMenuId()))
				.sorted(Comparator.comparing(SystemMenuVO::getMenuType)
					.thenComparing(SystemMenuVO::getOrderCode))
				.collect(Collectors.toList());
		return returnList;
	}

	/**
	 * 根据用户Id查询拥有的菜单权限-return List<SystemMenuVO>
	 * @param roleId
	 */
	public List<SystemMenuVO> findMenuListByUserId(Long userId) throws Exception{
		if(null == userId){
			return new ArrayList<SystemMenuVO>();
		}
		Long roleId = systemUserList.stream().filter(r -> userId.equals(r.getUserId())).collect(Collectors.toList()).get(0).getRoleId();
		return findMenuListByRoleId(roleId);
	}
	
	/**
	 * 根据角色Id查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 */
	public Map<String, SystemMenuVO> findMenuMapByRoleId(Long roleId) throws Exception{
		if(null == roleId){
			return new HashMap<String, SystemMenuVO>();
		}
		Map<String, SystemMenuVO> map = new HashMap<String, SystemMenuVO>();
		List<SystemMenuVO> menuList = findMenuListByRoleId(roleId);
		if(null !=menuList && menuList.size()>0){
			for(SystemMenuVO menu: menuList){
				map.put(menu.getMenuUrl(), menu);
			}
		}
		return map;
	}
	
	/**
	 * 根据用户Id查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 */
	public Map<String, SystemMenuVO> findMenuMapByUserId(Long userId) throws Exception{
		if(null == userId){
			return new HashMap<String, SystemMenuVO>();
		}
		Long roleId = systemUserList.stream().filter(r -> userId.equals(r.getUserId())).collect(Collectors.toList()).get(0).getRoleId();
		return findMenuMapByRoleId(roleId);
	}
	
	/**
	 * 获取MenuBean对象集合
	 */
	public List<MenuBean> getMenuBeanList(){
		List<MenuBean> returnList = new ArrayList<>();
		try{
			Map<Long, List<SystemMenuVO>> groupMap = systemMenuList.stream().filter(r -> r != null)
					.sorted(Comparator.comparing(SystemMenuVO::getMenuType)
							.thenComparing(SystemMenuVO::getOrderCode))
					.collect(Collectors.groupingBy(SystemMenuVO::getCompanyId));
			for (Map.Entry<Long, List<SystemMenuVO>> entry : groupMap.entrySet()) {
				Long companyId = entry.getKey();
				List<SystemMenuVO> menuList = entry.getValue();
				MenuBean bean = new MenuBean();
				bean.setCompanyId(companyId);
				bean.setMenuList(menuList);
				returnList.add(bean);
			}
		}catch(Exception e){
			logger.error("处理异常");
		}
		return returnList;
	}
	
	/**
	 * 获取RoleBean对象集合
	 */
	public List<RoleBean> getRoleBeanList(){
		List<RoleBean> returnList = new ArrayList<>();
		try{
			Map<Long, List<SystemRoleVO>> groupMap = systemRoleList.stream().collect(Collectors.groupingBy(SystemRoleVO::getCompanyId));
			for (Map.Entry<Long, List<SystemRoleVO>> entry : groupMap.entrySet()) {
				Long companyId = entry.getKey();
				List<SystemRoleVO> roleList = entry.getValue();
				RoleBean bean = new RoleBean();
				bean.setCompanyId(companyId);
				bean.setRoleList(roleList);
				returnList.add(bean);
			}
		}catch(Exception e){
			logger.error("处理异常");
		}
		return returnList;
	}
	
	/**
	 * 获取RoleColumnBean对象集合
	 */
	public List<RoleColumnBean> getRoleColumnBeanList(){
		List<RoleColumnBean> returnList = new ArrayList<>();
		try{
			Map<Long, List<SystemRoleColumnAuthVO>> groupMap = systemRoleColumnAuthList.stream().filter(r -> r != null)
					.collect(Collectors.groupingBy(SystemRoleColumnAuthVO::getCompanyId));
			for (Map.Entry<Long, List<SystemRoleColumnAuthVO>> entry : groupMap.entrySet()) {
				Long companyId = entry.getKey();
				List<SystemRoleColumnAuthVO> roleColumnList = entry.getValue();
				RoleColumnBean bean = new RoleColumnBean();
				bean.setCompanyId(companyId);
				bean.setRoleColumnList(roleColumnList);
				returnList.add(bean);
			}
		}catch(Exception e){
			logger.error("处理异常");
		}
		return returnList;
	}
	
	/**
	 * 用户-刷新
	 */
	public void refreshUser(Long userId){
		systemUserList = systemUserList.stream().filter(r -> !r.getUserId().equals(userId)).collect(Collectors.toList());
		SystemUserVO tempObj = misSystemUserDubboService.findById(userId).getData();
		if(null != tempObj){
			systemUserList.add(tempObj);
		}
	}
	
	/**
	 * 角色-刷新
	 */
	public void refreshRole(Long roleId){
		systemRoleList = systemRoleList.stream().filter(r -> !r.getRoleId().equals(roleId)).collect(Collectors.toList());
		SystemRoleVO tempObj = misSystemRoleDubboService.findById(roleId).getData();
		if(null != tempObj){
			systemRoleList.add(tempObj);
		}
	}
	
	/**
	 * 菜单-刷新
	 */
	public void refreshMenu(Long menuId){
		systemMenuList = systemMenuList.stream().filter(r -> !r.getMenuId().equals(menuId)).collect(Collectors.toList());
		SystemMenuVO tempObj = misSystemMenuDubboService.findById(menuId).getData();
		if(null != tempObj){
			systemMenuList.add(tempObj);
		}
	}
	
	/**
	 * 菜单与角色-关联关系-刷新
	 */
	public void refreshMenuRole(Long roleId){
		SystemMenuRoleVO vo = new SystemMenuRoleVO();
		vo.setRoleId(roleId);
		List<SystemMenuRoleVO> tempList = misSystemMenuRoleDubboService.findList(vo).getData();
		systemMenuRoleList = systemMenuRoleList.stream().filter(r -> !r.getRoleId().equals(roleId)).collect(Collectors.toList());
		if(null != tempList){
			systemMenuRoleList.addAll(tempList);
		}
	}
	
	/**
	 * 角色与列-关联关系-刷新
	 */
	public void refreshRoleColumn(Long roleId){
		SystemRoleColumnAuthVO vo = new SystemRoleColumnAuthVO();
		vo.setRoleId(roleId);
		List<SystemRoleColumnAuthVO> tempList = misSystemRoleColumnAuthDubboService.findList(vo).getData();
		systemRoleColumnAuthList = systemRoleColumnAuthList.stream().filter(r -> !r.getRoleId().equals(roleId)).collect(Collectors.toList());
		if(null != tempList){
			systemRoleColumnAuthList.addAll(tempList);
		}
	}

	/**
	 * 根据userid获取用户对象
	 */
	public SystemUserVO getSystemUserByUserId(Long userId){
		List<SystemUserVO> tempList = systemUserList.stream().filter(r -> r.getUserId().equals(userId)).collect(Collectors.toList());
		if(null != tempList && tempList.size() > 0){
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 根据userNo获取用户对象
	 */
	public SystemUserVO getSystemUserByUserNo(String userNo, Long companyId){
		List<SystemUserVO> tempList = systemUserList.stream()
				.filter(r -> r.getUserNo().equals(userNo) && r.getCompanyId().equals(companyId))
				.collect(Collectors.toList());
		if(null != tempList && tempList.size() > 0){
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 根据userNo获取用户对象
	 */
	public SystemUserVO getSystemUserByUserNo(String userNo){
		List<SystemUserVO> tempList = systemUserList.stream()
				.filter(r -> r.getUserNo().equals(userNo))
				.collect(Collectors.toList());
		if(null != tempList && tempList.size() > 0){
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 根据角色id获取角色对象
	 */
	public SystemRoleVO getSystemRoleByRoleId(Long roleId){
		List<SystemRoleVO> tempList = systemRoleList.stream().filter(r -> r.getRoleId().equals(roleId)).collect(Collectors.toList());
		if(null != tempList && tempList.size() > 0){
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 根据角色code获取角色对象
	 */
	public SystemRoleVO getSystemRoleByRoleCode(String roleCode, Long companyId){
		List<SystemRoleVO> tempList = systemRoleList.stream()
				.filter(r -> r.getRoleCode().equals(roleCode) && r.getCompanyId().equals(companyId))
				.collect(Collectors.toList());
		if(null != tempList && tempList.size() > 0){
			return tempList.get(0);
		}
		return null;
	}

	/**
	 * 根据角色id获取所有的列权限
	 */
	public List<SystemRoleColumnAuthVO> getSystemRoleColumnAuthListByRoleId(Long roleId){
		List<SystemRoleColumnAuthVO> tempList = systemRoleColumnAuthList.stream().filter(r -> r.getRoleId().equals(roleId)).collect(Collectors.toList());
		return tempList;
	}

	/**
	 * 根据用户id获取所有的列权限
	 */
	public List<SystemRoleColumnAuthVO> getSystemRoleColumnAuthListByUserId(Long userId){
		List<SystemUserVO> tempList = systemUserList.stream().filter(r -> r.getUserId().equals(userId)).collect(Collectors.toList());
		if(null != tempList && tempList.size() > 0){
			return getSystemRoleColumnAuthListByRoleId(tempList.get(0).getRoleId());
		}
		return new ArrayList<SystemRoleColumnAuthVO>();
	}
	
}
