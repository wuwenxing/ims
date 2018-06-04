package com.gwghk.ims.activity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.util.ImsDateUtil;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：后台-物品管理测试用例
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月29日
 */
public class MisActItemsSettingManagerTest extends BaseTest{

	@Autowired
	private MisActItemsSettingDubboService actItemsSettingDubboService;
	
	private Long getCompanyId(){
		return 1L;
	}
	
	@Test
	@Ignore
	public void testFindPageList(){
		System.out.println("------->testFindPageList");
		ActItemsSettingVO actItemsSettingVO = new ActItemsSettingVO();
		actItemsSettingVO.setCompanyId(getCompanyId());
		actItemsSettingVO.setStartDate(ImsDateUtil.parseDateDayFormat("2018-04-20 00:00:00"));
		MisRespResult<PageR<ActItemsSettingVO>> misResult = actItemsSettingDubboService.findPageList(actItemsSettingVO,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<ActItemsSettingVO> list = misResult.getData().getList();
				System.err.println(list.size());
				System.out.println(JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	@Ignore
	public void testSaveOrUpdateItem(){
		//实物
		String itemType = null;
		 itemType = ActItemTypeEnum.REAL.getValue();
//		 itemType = ActItemTypeEnum.VIRTUAL.getValue();
//		 itemType = ActItemTypeEnum.INTERFACE.getValue();
//		 itemType = ActItemTypeEnum.WITHGOLD.getValue();
//		 itemType = ActItemTypeEnum.TOKENCOIN.getValue();
//		 itemType = ActItemTypeEnum.ANALOGCOIN.getValue();
		String itemNumber = null;
		itemNumber = testCreateItem(itemType);
	 
//		testUpdateItem(itemNumber);
 
	}
	
	
	/**
	 * 新增物品
	 */
	 
	public String testCreateItem(String itemType){
		System.out.println("------->testCreateItem_"+itemType);
		ActItemsSettingVO actItemsSettingVO = new ActItemsSettingVO();
		actItemsSettingVO = getTempItemVo(itemType);
		MisRespResult<Void> misResult = actItemsSettingDubboService.saveOrUpdate(actItemsSettingVO,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
		return actItemsSettingVO.getItemNumber();
	}
	
	/**
	 * 修改物品
	 */
	public Long testUpdateItem(String itemNumber){
		System.out.println("------->testUpdateItem_"+itemNumber);
		ActItemsSettingVO oldVO = findByItemNumber(itemNumber);
		ActItemsSettingVO newVo = new ActItemsSettingVO();
		newVo = getTempItemVo(oldVO.getItemType());
		newVo.setId(oldVO.getId());
		newVo.setItemName(oldVO.getItemName()+"-修改");
		//实物
		if(ActItemTypeEnum.REAL.getValue().equals(oldVO.getItemType())){
			newVo.setItemStockAmount(200);//库存数量
			newVo.setItemPrice(new BigDecimal("50"));//物品价格
		}
		//虚拟物品
		else  if(ActItemTypeEnum.VIRTUAL.getValue().equals(oldVO.getItemType())){
			newVo.setItemStockAmount(200);//库存数量
			newVo.setItemCategory(ActItemCategoryEnum.MOBILECHARGE.getValue());//种类
			newVo.setItemCategoryAmount(50);//种类价值数 eg:话费20元
		}
		
		//接口物品
		else  if(ActItemTypeEnum.INTERFACE.getValue().equals(oldVO.getItemType())){
			newVo.setItemStockAmount(200);//库存数量
			newVo.setItemCategory(ActItemCategoryEnum.MOBILECHARGE.getValue());//种类
		}
		//模拟币、赠金、代币
		else  if(ActItemTypeEnum.ANALOGCOIN.getValue().equals(oldVO.getItemType())
				||ActItemTypeEnum.TOKENCOIN.getValue().equals(oldVO.getItemType())
				||ActItemTypeEnum.WITHGOLD.getValue().equals(oldVO.getItemType())){
			newVo.setItemPrice(new BigDecimal("50"));//固定金额量 eg:赠金50
		}
		MisRespResult<Void> misResult = actItemsSettingDubboService.saveOrUpdate(newVo,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
		return oldVO.getId();
	}
	 
	
	/**
	 * 实例化一个界面不同类型的物品参数
	 * @param s
	 * @return
	 */
	private ActItemsSettingVO getTempItemVo(String itemType){
		ActItemsSettingVO vo = new ActItemsSettingVO();
		String str = ImsDateUtil.toddHhmmss();
		String itemNumber = "eva_"+itemType+"_"+str;
		String itemName ="eva_"+ActItemTypeEnum.formatValue(itemType)+str;
		vo.setItemNumber(itemNumber);
		vo.setItemName(itemName);
		vo.setEnableFlag("Y");//禁用或启用
		vo.setDeleteFlag("N");
		vo.setStartDate(new Date());//开始时间
		vo.setEndDate(ImsDateUtil.addDay(new Date(), 30));//结束时间
		vo.setItemType(itemType);
		vo.setCompanyId(getCompanyId());
		//实物
		if(ActItemTypeEnum.REAL.getValue().equals(itemType)){
			vo.setItemStockAmount(100);//库存数量
			vo.setItemPrice(new BigDecimal("5"));//物品价格
		}
		//虚拟物品
		else  if(ActItemTypeEnum.VIRTUAL.getValue().equals(itemType)){
				vo.setItemStockAmount(100);//库存数量
				vo.setItemCategory(ActItemCategoryEnum.MOBILECHARGE.getValue());//种类
				vo.setItemCategoryAmount(5);//种类价值数 eg:话费5元
		}
		
		//接口物品
		else  if(ActItemTypeEnum.INTERFACE.getValue().equals(itemType)){
			vo.setItemStockAmount(100);//库存数量
			vo.setItemCategory(ActItemCategoryEnum.MOBILECHARGE.getValue());//种类
		}
		//模拟币、赠金、代币
		else  if(ActItemTypeEnum.ANALOGCOIN.getValue().equals(itemType)
				||ActItemTypeEnum.TOKENCOIN.getValue().equals(itemType)
				||ActItemTypeEnum.WITHGOLD.getValue().equals(itemType)){
			vo.setItemPrice(new BigDecimal("5"));//固定金额量 eg:赠金5元
		}
		return vo;
	}
	
	/**
	 * 删除物品
	 * @param itemNumber
	 * @return
	 */
	@Test
//	@Ignore
	public void testDeleteByIdArray(){
		System.out.println("------->testDeleteByIdArray");
		String idArray="1078";
		Long companyId = getCompanyId();
		MisRespResult<Void> misResult = actItemsSettingDubboService.deleteByIdArray(idArray,companyId);
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	@Ignore
    public void testFindByItemNumber(){
		System.out.println("------->testFindByItemNumber");
		Long companyId = 1L;
		MisRespResult<ActItemsSettingVO> misResult = actItemsSettingDubboService.findByItemNumber("eva_real_19094759",true,companyId);
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				System.out.println(misResult.getData().toString());
			}
		}else{
			System.out.println("<-------fail");
		}
	}

	
	public ActItemsSettingVO findByItemNumber(String itemNumber){
		Long companyId = 1L;
		MisRespResult<ActItemsSettingVO> misResult = actItemsSettingDubboService.findByItemNumber(itemNumber,true,companyId);
		if(misResult.isOk()){
			return misResult.getData();
		}
		return null;
	}
 
}
