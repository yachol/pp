package com.pp.log;


public enum OprLogConst {
	ADD("add"),QUERY("query");
	
	private String opr;
	private OprLogConst(String opr) {
		this.opr = opr;
	}
	public static String getValue(String filedNmae) {
		OprLogConst[] values = OprLogConst.values();
		for (OprLogConst val : values) {
			if (val.name().equals(filedNmae)) {
				return val.opr;
			}
		}
		return null;
	}
}
