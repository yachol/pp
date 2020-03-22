package com.pp.log;


import java.util.UUID;

public class UuidUtil {
	public static String get32UUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
