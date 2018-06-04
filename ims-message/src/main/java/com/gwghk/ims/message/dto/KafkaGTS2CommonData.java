package com.gwghk.ims.message.dto;

import java.util.Date;

/**
 * 
 * @ClassName: KafkaGTS2CommonData
 * @Description: 接收kafka有关GTS2的数据
 * @author lawrence
 * @date 2017年6月15日
 *
 */
public class KafkaGTS2CommonData {
	private Date c;
	private String a;
	private String s;
	private String t;
	private String n;
	private String o;

	public Date getC() {
		return c;
	}

	public void setC(Date c) {
		this.c = c;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

}
