package com.gwghk.ims.activity.dao.inf;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Select;
import com.gwghk.ims.activity.dao.entity.ActTaskCustomSetting;
import net.oschina.durcframework.easymybatis.dao.CrudDao;

public interface ActTaskCustomSettingDao extends CrudDao<ActTaskCustomSetting>{

	@Select("select * from act_task_custom_setting where delete_flag != 'Y' and (task_code = #{taskCode} or rule_code = #{ruleCode} or task_name = #{taskName})")
	List<ActTaskCustomSetting> isExsits(Map<String,Object> dataMap);
}