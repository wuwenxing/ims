package com.gwghk.ims.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值通道枚举
 */
public enum RechargeChannelEnum {

	rl("容联", "rl"),
	of("欧飞", "of");

	private final String value;
	private final String labelKey;

	RechargeChannelEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<RechargeChannelEnum> getList() {
		List<RechargeChannelEnum> result = new ArrayList<RechargeChannelEnum>();
		for (RechargeChannelEnum ae : RechargeChannelEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey) {
		for (RechargeChannelEnum ae : RechargeChannelEnum.values()) {
			if (ae.getLabelKey().equals(labelKey)) {
				return ae.getValue();
			}
		}
		return labelKey;
	}

	/**
	 *  根据每个运营商的套餐，进行拆分流量包
	 *  容联-运营商	
		sn编码	档位	原价		描述
		888888	无	无		测试档位不实际充值不产生费用
		
		"移动"
		100001	10	3		移动10M
		100002	30	5		移动30M
	  --100014	50	 		移动50M(特殊包)
		100003	70	10		移动70M
	  --100012	100	 		移动100M(特殊包)
		100004	150	20		移动150M
		100005	500	30		移动500M
		100006	1024	50		移动1024M
		100007	2048	70		移动2048M
		100008	3072	100		移动3072M
		100009	4096	130		移动4096M
		100010	6144	180		移动6144M
		100011	11264	280		移动11264M
		
		"全国联通"
		200001	20	3	0	联通20M
	  --200008	30	 		联通30M(特殊包)
		200002	50	6	0	联通50M
		200003	100	10	0	联通100M
		200004	200	15	0	联通200M
	  --200006	300	 		联通300M(特殊包)
		200005	500	30	0	联通500M
	  --200007	1024 		联通1024M(特殊包)
		
		"电信"
		300001	5	1		电信5M
		300002	10	2		电信10M
		300003	30	5		电信30M
		300004	50	7		电信50M
		300005	100	10		电信100M
		300006	200	15		电信200M
		300007	500	30		电信500M
		300008	1024	50		电信1024M	
	 */
	public static List<String> rlSNOrPacketList_cmcc = new ArrayList<String>();// 移动
	public static List<String> rlSNOrPacketList_cucc = new ArrayList<String>();// 联通
	public static List<String> rlSNOrPacketList_ctcc = new ArrayList<String>();// 电信
	public static List<String> ofSNOrPacketList_cmcc = new ArrayList<String>();// 移动
	public static List<String> ofSNOrPacketList_cucc = new ArrayList<String>();// 联通
	public static List<String> ofSNOrPacketList_ctcc = new ArrayList<String>();// 电信
	static {
		// 容联(档位编码,流量大小)
		rlSNOrPacketList_cmcc.add("100011,11264");
		rlSNOrPacketList_cmcc.add("100010,6144");
		rlSNOrPacketList_cmcc.add("100009,4096");
		rlSNOrPacketList_cmcc.add("100008,3072");
		rlSNOrPacketList_cmcc.add("100007,2048");
		rlSNOrPacketList_cmcc.add("100006,1024");
		rlSNOrPacketList_cmcc.add("100005,500");
		rlSNOrPacketList_cmcc.add("100004,150");
		rlSNOrPacketList_cmcc.add("100012,100");
		rlSNOrPacketList_cmcc.add("100003,70");
		rlSNOrPacketList_cmcc.add("100014,50");
		rlSNOrPacketList_cmcc.add("100002,30");
		rlSNOrPacketList_cmcc.add("100001,10");

		rlSNOrPacketList_cucc.add("200007,1024");
		rlSNOrPacketList_cucc.add("200005,500");
		rlSNOrPacketList_cucc.add("200006,300");
		rlSNOrPacketList_cucc.add("200004,200");
		rlSNOrPacketList_cucc.add("200003,100");
		rlSNOrPacketList_cucc.add("200002,50");
		rlSNOrPacketList_cucc.add("200008,30");
		rlSNOrPacketList_cucc.add("200001,20");

		rlSNOrPacketList_ctcc.add("300008,1024");
		rlSNOrPacketList_ctcc.add("300007,500");
		rlSNOrPacketList_ctcc.add("300006,200");
		rlSNOrPacketList_ctcc.add("300005,100");
		rlSNOrPacketList_ctcc.add("300004,50");
		rlSNOrPacketList_ctcc.add("300003,30");
		rlSNOrPacketList_ctcc.add("300002,10");
		rlSNOrPacketList_ctcc.add("300001,5");
		
		// 欧飞(面值,流量大小)
//		充值省份	可充全国	运营商	面值（元）	流量值	使用范围	流量有效期	生效时间	当月限充(次)	折扣率
//						移动		3	10M	全国	当月有效	立即生效	无限次	91.00%		
//								4	20M	全国	当月有效	立即生效	无限次	67.00%		
//								5	30M	全国	当月有效	立即生效	无限次	91.00%		
//								6	50M	全国	当月有效	立即生效	无限次	67.00%		
//								10	70M	全国	当月有效	立即生效	无限次	91.00%		
//								10	100M	全国	当月有效	立即生效	无限次	67.00%		
//								20	150M	全国	当月有效	立即生效	无限次	91.00%		
//								20	200M	全国	当月有效	立即生效	无限次	67.00%		
//								20	300M	全国	当月有效	立即生效	无限次	91.00%		
//								30	500M	全国	当月有效	立即生效	无限次	80.00%		
//								50	1G	全国	当月有效	立即生效	无限次	91.00%		
//								70	2G	全国	当月有效	立即生效	无限次	91.00%		
//								100	3G	全国	当月有效	立即生效	无限次	91.00%		
//								130	4G	全国	当月有效	立即生效	无限次	91.00%		
//								180	6G	全国	当月有效	立即生效	无限次	91.00%		
//								280	11G	全国	当月有效	立即生效	无限次	91.00%		
		ofSNOrPacketList_cmcc.add("280,11G");
		ofSNOrPacketList_cmcc.add("180,6G");
		ofSNOrPacketList_cmcc.add("130,4G");
		ofSNOrPacketList_cmcc.add("100,3G");
		ofSNOrPacketList_cmcc.add("70,2G");
		ofSNOrPacketList_cmcc.add("50,1G");
		ofSNOrPacketList_cmcc.add("30,500M");
		ofSNOrPacketList_cmcc.add("20,300M");
		ofSNOrPacketList_cmcc.add("20,200M");
		ofSNOrPacketList_cmcc.add("20,150M");
		ofSNOrPacketList_cmcc.add("10,100M");
		ofSNOrPacketList_cmcc.add("10,70M");
		ofSNOrPacketList_cmcc.add("6,50M");
		ofSNOrPacketList_cmcc.add("5,30M");
		ofSNOrPacketList_cmcc.add("4,20M");
		ofSNOrPacketList_cmcc.add("3,10M");

//		充值省份	可充全国	运营商	面值（元）	流量值	使用范围	流量有效期	生效时间	当月限充(次)	折扣率
//						联通		3	20M	全国	当月有效	立即生效	同一号码同一档位限充5次/月	93.00%	
//								4	30M	全国	当月有效	立即生效	同一号码同一档位限充5次/月	95.00%	
//								6	50M	全国	当月有效	立即生效	同一号码同一档位限充5次/月	93.00%	
//								10	100M	全国	当月有效	立即生效	同一号码同一档位限充5次/月	93.00%	
//								15	200M	全国	当月有效	立即生效	同一号码同一档位限充5次/月	93.00%	
//								20	300M	全国	当月有效	立即生效	同一号码同一档位限充5次/月	95.00%	
//								30	500M	全国	当月有效	立即生效	同一号码同一档位限充5次/月	93.00%	
//								50	1G	全国	当月有效	立即生效	同一号码同一档位限充5次/月	95.00%	
		ofSNOrPacketList_cucc.add("50,1G");
		ofSNOrPacketList_cucc.add("30,500M");
		ofSNOrPacketList_cucc.add("20,300M");
		ofSNOrPacketList_cucc.add("15,200M");
		ofSNOrPacketList_cucc.add("10,100M");
		ofSNOrPacketList_cucc.add("6,50M");
		ofSNOrPacketList_cucc.add("4,30M");
		ofSNOrPacketList_cucc.add("3,20M");
		
//		充值省份	可充全国	运营商	面值（元）	流量值	使用范围	流量有效期	生效时间	当月限充(次)	折扣率
//						电信		1	5M	全国	当月有效	立即生效	无限次	79.00%	
//								2	10M	全国	当月有效	立即生效	无限次	79.00%	
//								5	30M	全国	当月有效	立即生效	无限次	79.00%	
//								7	50M	全国	当月有效	立即生效	无限次	79.00%	
//								10	100M	全国	当月有效	立即生效	无限次	79.00%	
//								15	200M	全国	当月有效	立即生效	无限次	79.00%	
//								30	500M	全国	当月有效	立即生效	无限次	79.00%	
//								50	1G	全国	当月有效	立即生效	无限次	79.00%	
		ofSNOrPacketList_ctcc.add("50,1G");
		ofSNOrPacketList_ctcc.add("30,500M");
		ofSNOrPacketList_ctcc.add("15,200M");
		ofSNOrPacketList_ctcc.add("10,100M");
		ofSNOrPacketList_ctcc.add("7,50M");
		ofSNOrPacketList_ctcc.add("5,30M");
		ofSNOrPacketList_ctcc.add("2,10M");
		ofSNOrPacketList_ctcc.add("1,5M");
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
}
