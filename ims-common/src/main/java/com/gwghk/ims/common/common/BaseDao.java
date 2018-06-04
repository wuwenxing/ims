package com.gwghk.ims.common.common;

import java.util.List;
import java.util.Map;

/**
 * 摘要：基础DAO接口
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月20日
 */
public interface BaseDao<T> {
    int save(T o);
    
    int saveByMap(Map<String,Object> map);
    
    int saveBatch(List<T> oList);
    
    int update(T o);
    
    int updateByMap(Map<String,Object> map);
    
    int updateBatch(List<T> oList);
    
    int delete(Object id);
    
    int deleteByMap(Map<String,Object> map);
    
    int deleteBatch(List<String> ids);
    
    List<T> findListByMap(Map<String,Object> map);
    
    List<T> findListByBean(T t);
    
    List<T> findList(Object id);
    
    T findObject(Object id);
    
    T findObjectByMap(Map<String,Object> map);
    
    int findTotal(Map<String,Object> map);
}