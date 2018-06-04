package com.gwghk.ims.activity.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.gwghk.ims.activity.dao.entity.ImsMallItem;
import com.gwghk.ims.activity.dao.inf.MisMallItemDao;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.util.ImsBeanUtil;
import com.gwghk.ims.common.util.PageCustomizedUtil;
import com.gwghk.ims.common.vo.mall.ImsMallItemVO;

@Component
@Transactional
public class MisMallItemManager {
	@Autowired
	private MisMallItemDao misMallItemsDao;
	
	public PageR<ImsMallItemVO> findPageList(ImsMallItemVO pageSearchVo) {
		PageHelper.startPage(pageSearchVo.getPage(), pageSearchVo.getRows(), true);
		PageHelper.orderBy(PageCustomizedUtil.sort(ImsMallItem.class, pageSearchVo.getSort(), pageSearchVo.getOrder()));
		
		return PageCustomizedUtil.copyPageList(new PageR<ImsMallItem>(misMallItemsDao.listByPage(pageSearchVo)), new PageR<ImsMallItemVO>(),ImsMallItemVO.class);
	}
	
	public ImsMallItemVO findById(Long id) {
		return ImsBeanUtil.copyNotNull(new ImsMallItemVO(),misMallItemsDao.findObject(id));
	}

	public int update(ImsMallItemVO imsMallItemVo) {
		ImsMallItem imsMallItem =ImsBeanUtil.copyNotNull(new ImsMallItem(),imsMallItemVo);
		return misMallItemsDao.update(imsMallItem);
	}

	public int insert(ImsMallItemVO imsMallItemVo) {
		ImsMallItem imsMallItem =ImsBeanUtil.copyNotNull(new ImsMallItem(),imsMallItemVo);
		return misMallItemsDao.save(imsMallItem);
	}

	public int deleteByIds(String[] idArr) {
		List<String> ids=new ArrayList<String>();
		for(String id:idArr)
			ids.add(id);
		
		return misMallItemsDao.deleteBatch(ids);
	}

	public int batchSoftDel(String[] idArr) {
		return misMallItemsDao.batchSoftDel(idArr);
	}
}
