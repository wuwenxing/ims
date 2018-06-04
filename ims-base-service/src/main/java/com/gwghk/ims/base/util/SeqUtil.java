package com.gwghk.ims.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import com.gwghk.ims.base.dao.entity.Seq;

/**
 * 摘要：统一序列号工具类
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年7月13日
 */
public class SeqUtil {
	private static final char[] charArray = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };  // Exclude O
	private static final int charArrayLen = charArray.length;
	
	/**
	 * 功能：根据规则重新构建序列
	 * @param sequence  序列对象
	 * @param newVal    新序列值1000
	 */
	public static String buildSeqByRule(Seq seq,Long newVal){
    	String prefix = seq.getSeqPrefix();
	    if(StringUtils.isEmpty(prefix)){
            return newVal.toString();
        }else{
      	    long mod = seq.getInitVal();
            String date = new SimpleDateFormat("yyMMdd").format(new Date());
            long jobNo = newVal % (charArrayLen * mod);
            int charArrayIndex = (int) (jobNo / mod);
            StringBuilder sb = new StringBuilder().append(prefix).append(date).append(RandomStringUtils.randomNumeric(4))
						                          .append(charArray[charArrayIndex])
						                          .append(String.valueOf(jobNo % mod + mod).substring(1))
						                          .append(RandomStringUtils.randomNumeric(4));
            return sb.toString();
        }
    }
}
