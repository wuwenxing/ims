package com.gwghk.ims.activity;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.PageR;
import com.gwghk.ims.common.enums.ActItemCategoryEnum;
import com.gwghk.ims.common.enums.ActItemTypeEnum;
import com.gwghk.ims.common.enums.ActStringCodeStatusEnum;
import com.gwghk.ims.common.inf.mis.activity.MisActItemsSettingDubboService;
import com.gwghk.ims.common.inf.mis.activity.MisActStringCodeDubboService;
import com.gwghk.ims.common.util.ImsDateUtil;
import com.gwghk.ims.common.vo.activity.ActItemsSettingVO;
import com.gwghk.ims.common.vo.activity.ActStringCodeVO;
import com.gwghk.unify.framework.common.util.JsonUtil;

/**
 * 摘要：后台-串码测试用例
 * @author eva.liu
 * @version 1.0
 * @Date 2018年3月29日
 */
public class MisActStringCodeManagerTest extends BaseTest{

	@Autowired
	private MisActStringCodeDubboService misActStringCodeDubboService;
	@Autowired
	private MisActItemsSettingDubboService misActItemsSettingDubboService;

	private Long getCompanyId(){
		return 1L;
	}
	
	@Test
	@Ignore
	public void testFindPageList(){
		System.out.println("------->testFindPageList");
		ActStringCodeVO reqVo = new ActStringCodeVO();
		reqVo.setCompanyId(getCompanyId());
		MisRespResult<PageR<ActStringCodeVO>> misResult = misActStringCodeDubboService.findPageList(reqVo,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<ActStringCodeVO> list = misResult.getData().getList();
				System.err.println(list.size());
				System.out.println(JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	 
	@Test
	@Ignore
	public void testFindAllStringCodes(){
		System.out.println("------->testFindAllStringCodes");
		MisRespResult<List<String>> misResult = misActStringCodeDubboService.findAllStringCodes(getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				List<String> list = misResult.getData();
				System.err.println(list.size());
				System.out.println(JsonUtil.obj2Str(list));
			}
		}else{
			System.out.println("<-------fail");
		}
	}
	
	/**
	 * 新增串码
	 */
	@Test
	@Ignore
	public void testCreateStringCode(){
		System.out.println("------->testCreateStringCode");
		ActStringCodeVO reqVo = new ActStringCodeVO();
		String itemNumber = null;
//		createVirtual();
		itemNumber="fx_20180410164058";
		reqVo.setItemNumber(itemNumber);
		reqVo.setStringCode("10000001");
		reqVo.setStatus(ActStringCodeStatusEnum.NOTUSED.getValue());//未使用
		reqVo.setCompanyId(getCompanyId());
		MisRespResult<Void> misResult = misActStringCodeDubboService.saveOrUpdate(reqVo,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}	 
	}
	
	/**
	 * 修改物品
	 */
	@Test
	@Ignore
	public void testUpdateStringCodeById(){
		System.out.println("------->testUpdateStringCodeById");
		ActStringCodeVO reqVo = findById(3682L);
		reqVo.setAccountNo("001");
		reqVo.setActivityPeriods("0000001");
		reqVo.setTaskTitle("任务标题001");
		reqVo.setStatus(ActStringCodeStatusEnum.USED.getValue());
		reqVo.setCompanyId(getCompanyId());
		MisRespResult<Void> misResult = misActStringCodeDubboService.saveOrUpdate(reqVo,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
	}
	
	/**
	 * 修改物品
	 */
	@Test
	@Ignore
	public void testUpdateStringCodeByStringCode(){
		System.out.println("------->testUpdateStringCodeByStringCode");
		String stringCode ="E10004";
		ActStringCodeVO reqVo = findByStringCode(stringCode);
		reqVo.setStringCode(stringCode);
		reqVo.setAccountNo("002");
		reqVo.setActivityPeriods("0000002");
		reqVo.setTaskTitle("任务标题002");
		reqVo.setStatus(ActStringCodeStatusEnum.USED.getValue());
		reqVo.setCompanyId(getCompanyId());
		MisRespResult<Void> misResult = misActStringCodeDubboService.saveOrUpdate(reqVo,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
	}
 
 
	/**
	 * 删除物品
	 * @param itemNumber
	 * @return
	 */
	@Test
	@Ignore
	public void testDeleteByIdArray(){
		System.out.println("------->testDeleteByIdArray");
		String idArray="3683,3685";
		Long companyId = 1L;
		MisRespResult<Void> misResult = misActStringCodeDubboService.deleteByIds(idArray,companyId);
		if(misResult.isOk()){
			System.out.println("<-------succuss");
		}else{
			System.out.println("<-------fail");
		}
	}
	
	@Test
	@Ignore
	 public void testGetStringCodeCount(){
		System.out.println("------->testGetStringCodeCount");
		ActStringCodeVO reqVo = new ActStringCodeVO();
		reqVo.setItemNumber("test_virtual_29171318");
		MisRespResult<Integer> misResult = misActStringCodeDubboService.getStringCodeCount(reqVo,getCompanyId());
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				System.out.println(misResult.getData().toString());
			}
		}else{
			System.out.println("<-------fail");
		}
	 }
 
	@Test
	@Ignore
    public void testFindByStringCode(){
		System.out.println("------->testFindByItemNumber");
		Long companyId = 1L;
		MisRespResult<ActStringCodeVO> misResult = misActStringCodeDubboService.findByStringCode("test001",companyId);
		if(misResult.isOk()){
			System.out.println("<-------succuss");
			if(misResult.getData() != null){
				System.out.println(misResult.getData().toString());
			}
		}else{
			System.out.println("<-------fail");
		}
	}

	
	private ActStringCodeVO findByStringCode(String stringCode){
		Long companyId = 1L;
		MisRespResult<ActStringCodeVO> misResult = misActStringCodeDubboService.findByStringCode(stringCode,companyId);
		if(misResult.isOk()){
			return misResult.getData();
		}
		return null;
	}
	
	private ActStringCodeVO findById(Long id){
		Long companyId = 1L;
		MisRespResult<ActStringCodeVO> misResult = misActStringCodeDubboService.findById(id,companyId);
		if(misResult.isOk()){
			return misResult.getData();
		}
		return null;
	}
	
	/**
	 * 实例化串码的虚拟物品
	 * @param s
	 * @return
	 */
	private String createVirtual(){
		String itemType = ActItemTypeEnum.VIRTUAL.getValue();
		ActItemsSettingVO vo = new ActItemsSettingVO();
		String str = ImsDateUtil.toddHhmmss();
		String itemNumber = "test_"+itemType+"_"+str;
		String itemName ="test_"+ActItemTypeEnum.formatValue(itemType)+str;
		vo.setItemNumber(itemNumber);
		vo.setItemName(itemName);
		vo.setEnableFlag("Y");//禁用或启用
		vo.setStartDate(new Date());//开始时间
		vo.setEndDate(ImsDateUtil.addDay(new Date(), 30));//结束时间
		vo.setItemType(itemType);
		//虚拟物品
		 if(ActItemTypeEnum.VIRTUAL.getValue().equals(itemType)){
				vo.setItemStockAmount(100);//库存数量
				vo.setItemCategory(ActItemCategoryEnum.STRINGCODE.getValue());//种类:串码
		}
		 vo.setCompanyId(getCompanyId());
		MisRespResult<Void> misResult = misActItemsSettingDubboService.saveOrUpdate(vo,getCompanyId());
		if(misResult.isOk()){
			return itemNumber;
		}else{
			System.out.println("<-------createVirtual:fail");
		}
		return null; 
	}
 
}
