package com.pp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Operationlog implements Serializable {
    /**
     * 主键id
     */
    @Id
    @Column(name = "oper_id")
    private String operId;

    /**
     * 功能模块
     */
    @Column(name = "oper_modul")
    private String operModul;

    /**
     * 操作类型
     */
    @Column(name = "oper_type")
    private String operType;

    /**
     * 操作描述
     */
    @Column(name = "oper_desc")
    private String operDesc;

    /**
     * 操作人员id
     */
    @Column(name = "oper_user_id")
    private String operUserId;

    /**
     * 操作人员姓名
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
     * 操作时间
     */
    @Column(name = "oper_create_time")
    private Date operCreateTime;

    /**
     * 操作版本号
     */
    @Column(name = "oper_ver")
    private String operVer;

    /**
     * 请求参数
     */
    @Column(name = "oper_requ_param")
    private String operRequParam;

    /**
     * 返回参数
     */
    @Column(name = "oper_resp_param")
    private String operRespParam;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键id
     *
     * @return oper_id - 主键id
     */
    public String getOperId() {
        return operId;
    }

    /**
     * 设置主键id
     *
     * @param operId 主键id
     */
    public void setOperId(String operId) {
        this.operId = operId;
    }

    /**
     * 获取功能模块
     *
     * @return oper_modul - 功能模块
     */
    public String getOperModul() {
        return operModul;
    }

    /**
     * 设置功能模块
     *
     * @param operModul 功能模块
     */
    public void setOperModul(String operModul) {
        this.operModul = operModul;
    }

    /**
     * 获取操作类型
     *
     * @return oper_type - 操作类型
     */
    public String getOperType() {
        return operType;
    }

    /**
     * 设置操作类型
     *
     * @param operType 操作类型
     */
    public void setOperType(String operType) {
        this.operType = operType;
    }

    /**
     * 获取操作描述
     *
     * @return oper_desc - 操作描述
     */
    public String getOperDesc() {
        return operDesc;
    }

    /**
     * 设置操作描述
     *
     * @param operDesc 操作描述
     */
    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc;
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
     * 获取操作人员姓名
     *
     * @return oper_user_name - 操作人员姓名
     */
    public String getOperUserName() {
        return operUserName;
    }

    /**
     * 设置操作人员姓名
     *
     * @param operUserName 操作人员姓名
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
     * 获取操作时间
     *
     * @return oper_create_time - 操作时间
     */
    public Date getOperCreateTime() {
        return operCreateTime;
    }

    /**
     * 设置操作时间
     *
     * @param operCreateTime 操作时间
     */
    public void setOperCreateTime(Date operCreateTime) {
        this.operCreateTime = operCreateTime;
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
     * 获取请求参数
     *
     * @return oper_requ_param - 请求参数
     */
    public String getOperRequParam() {
        return operRequParam;
    }

    /**
     * 设置请求参数
     *
     * @param operRequParam 请求参数
     */
    public void setOperRequParam(String operRequParam) {
        this.operRequParam = operRequParam;
    }

    /**
     * 获取返回参数
     *
     * @return oper_resp_param - 返回参数
     */
    public String getOperRespParam() {
        return operRespParam;
    }

    /**
     * 设置返回参数
     *
     * @param operRespParam 返回参数
     */
    public void setOperRespParam(String operRespParam) {
        this.operRespParam = operRespParam;
    }
}