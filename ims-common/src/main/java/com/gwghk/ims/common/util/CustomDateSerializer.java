package com.gwghk.ims.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * 摘要：自定义返回JSON 数据格中日期格式化处理
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年10月23日
 */
public class CustomDateSerializer extends JsonSerializer<Date>{
	
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeString(format.format(value));
	}
}
