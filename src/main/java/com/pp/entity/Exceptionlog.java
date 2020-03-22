package com.pp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Exceptionlog implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "exc_id")
    private String excId;

    /**
     * 异常名称
     */
    @Column(name = "exc_name")
    private String excName;

    /**
     * 操作人员id
     */
    @Column(name = "oper_user_id")
    private String operUserId;

    /**
     *  操作人员姓名
     */
    @Column(name = "oper_user_name")
    private String operUserName;

    /**
     * 操作方法
     */
    @Column(name = "oper_method")
    private String operMethod;

    /**
     * 请求URI
     */
    @Column(name = "oper_uri")
    private String operUri;

    /**
     * 请求ip
     */
    @Column(name = "oper_ip")
    private String operIp;

    /**
     * 操作版本号
     */
    @Column(name = "oper_ver")
    private String operVer;

    /**
     * 创建时间
     */
    @Column(name = "oper_create_time")
    private Date operCreateTime;

    /**
     * 请求参数
     */
    @Column(name = "exc_requ_param")
    private String excRequParam;

    /**
     * 异常信息
     */
    @Column(name = "exc_message")
    private String excMessage;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return exc_id - 主键id
     */
    public String getExcId() {
        return excId;
    }

    /**
     * 设置主键id
     *
     * @param excId 主键id
     */
    public void setExcId(String excId) {
        this.excId = excId;
    }

    /**
     * 获取异常名称
     *
     * @return exc_name - 异常名称
     */
    public String getExcName() {
        return excName;
    }

    /**
     * 设置异常名称
     *
     * @param excName 异常名称
     */
    public void setExcName(String excName) {
        this.excName = excName;
    }

    /**
     * 获取操作人员id
     *
     * @return oper_user_id - 操作人员id
     */
    public String getOperUserId() {
        return operUserId;
    }

    /**
     * 设置操作人员id
     *
     * @param operUserId 操作人员id
     */
    public void setOperUserId(String operUserId) {
        this.operUserId = operUserId;
    }

    /**
     * 获取 操作人员姓名
     *
     * @return oper_user_name -  操作人员姓名
     */
    public String getOperUserName() {
        return operUserName;
    }

    /**
     * 设置 操作人员姓名
     *
     * @param operUserName  操作人员姓名
     */
    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    /**
     * 获取操作方法
     *
     * @return oper_method - 操作方法
     */
    public String getOperMethod() {
        return operMethod;
    }

    /**
     * 设置操作方法
     *
     * @param operMethod 操作方法
     */
    public void setOperMethod(String operMethod) {
        this.operMethod = operMethod;
    }

    /**
     * 获取请求URI
     *
     * @return oper_uri - 请求URI
     */
    public String getOperUri() {
        return operUri;
    }

    /**
     * 设置请求URI
     *
     * @param operUri 请求URI
     */
    public void setOperUri(String operUri) {
        this.operUri = operUri;
    }

    /**
     * 获取请求ip
     *
     * @return oper_ip - 请求ip
     */
    public String getOperIp() {
        return operIp;
    }

    /**
     * 设置请求ip
     *
     * @param operIp 请求ip
     */
    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    /**
     * 获取操作版本号
     *
     * @return oper_ver - 操作版本号
     */
    public String getOperVer() {
        return operVer;
    }

    /**
     * 设置操作版本号
     *
     * @param operVer 操作版本号
     */
    public void setOperVer(String operVer) {
        this.operVer = operVer;
    }

    /**
     * 获取创建时间
     *
     * @return oper_create_time - 创建时间
     */
    public Date getOperCreateTime() {
        return operCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param operCreateTime 创建时间
     */
    public void setOperCreateTime(Date operCreateTime) {
        this.operCreateTime = operCreateTime;
    }

    /**
     * 获取请求参数
     *
     * @return exc_requ_param - 请求参数
     */
    public String getExcRequParam() {
        return excRequParam;
    }

    /**
     * 设置请求参数
     *
     * @param excRequParam 请求参数
     */
    public void setExcRequParam(String excRequParam) {
        this.excRequParam = excRequParam;
    }

    /**
     * 获取异常信息
     *
     * @return exc_message - 异常信息
     */
    public String getExcMessage() {
        return excMessage;
    }

    /**
     * 设置异常信息
     *
     * @param excMessage 异常信息
     */
    public void setExcMessage(String excMessage) {
        this.excMessage = excMessage;
    }
}