package com.gwghk.ims.common.enums;

/**
 * 
* @ClassName: Mt4CountryEnum
* @Description: MT4国籍枚举
* @author lawrence
* @date 2018年3月28日
*
 */
public enum Mt4CountryEnum {
	TW("中国(台湾)", "ISO_3166_158"),
	TW2("中国台湾", "ISO_3166_158"),
	TW3("中國(臺灣)", "ISO_3166_158"),
	TW4("中國臺灣", "ISO_3166_158"),

	CN("中国(大陆)", "ISO_3166_156"),
	CN2("中國", "ISO_3166_156"),
	CN3("中國(大陸)", "ISO_3166_156"),
	CN4("中国", "ISO_3166_156"),

	MO("中国(澳门)", "ISO_3166_446"),
	MO2("中國(澳門)", "ISO_3166_446"),
	MO3("中国澳门", "ISO_3166_446"),
	MO4("中國澳門", "ISO_3166_446"),
	
	HK("中国(香港)", "ISO_3166_344"),
	HK2("中国香港", "ISO_3166_344"),
	HK3("中國(香港)", "ISO_3166_344"),
	HK4("中國香港", "ISO_3166_344"),
	UG("乌干达", "ISO_3166_800"),
	
	YE("也门", "ISO_3166_887"),
	
	CA("加拿大", "ISO_3166_124"),
	IN("印度", "ISO_3166_356"),
	ID("印度尼西亚", "ISO_3166_360"),
	KZ("哈萨克斯坦", "ISO_3166_398"),
	TM("土库曼斯坦", "ISO_3166_795"),
	TR("土耳其", "ISO_3166_792"),
	EG("埃及", "ISO_3166_818"),
	ET("埃塞俄比亚 ", "ISO_3166_231"),
	AL("奥兰", "ISO_3166_248"),
	AT("奥地利", "ISO_3166_040"),
	VE("委内瑞拉", "ISO_3166_862"),
	AO("安哥拉", "ISO_3166_024"),

	AG("安提瓜和巴布达", "ISO_3166_028"),
	
	AO2("安格拉", "ISO_3166_024"),
	
	

	;

	private final String name;
	private final String iso;

	Mt4CountryEnum(String name, String iso) {
		this.name = name;
		this.iso = iso;
	}

	public String getName() {
		return name;
	}

	public String getIso() {
		return iso;
	}

	public static String getIsoValue(String name){
		for(Mt4CountryEnum data:Mt4CountryEnum.values()){
			if(data.getName().equals(name)){
				return data.getIso();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String value = "中国（大陆）";
		System.out.println(Mt4CountryEnum.getIsoValue(value.replace("（", "(").replace("）", ")")));
	}
	
}