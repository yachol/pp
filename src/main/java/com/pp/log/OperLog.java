package com.pp.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {
	String operModul() default ""; // 操作模块

	String operType() default "";// 操作类型

	String operDesc() default "";// 操作说明
}
