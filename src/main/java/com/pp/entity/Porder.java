package com.pp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Porder implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String no;

    private Integer pid;

    private String pname;

    private Integer buyid;

    private Integer saleid;

    @Column(name = "old_pricce")
    private BigDecimal oldPricce;

    @Column(name = "pay_price")
    private BigDecimal payPrice;

    @Column(name = "pp_time")
    private Date ppTime;

    @Column(name = "porder_time")
    private Date porderTime;

    private Integer aid;

    private String adress;

    private String state;

    private static final long serialVersionUID = 1L;

    public Porder(String no, Integer pid, String pname, Integer buyid, Integer saleid, BigDecimal oldPricce,
			BigDecimal payPrice, Date ppTime, Date porderTime, Integer aid, String adress, String state) {
		super();
		this.no = no;
		this.pid = pid;
		this.pname = pname;
		this.buyid = buyid;
		this.saleid = saleid;
		this.oldPricce = oldPricce;
		this.payPrice = payPrice;
		this.ppTime = ppTime;
		this.porderTime = porderTime;
		this.aid = aid;
		this.adress = adress;
		this.state = state;
	}

	public Porder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return no
     */
    public String getNo() {
        return no;
    }

    /**
     * @param no
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * @param pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * @return buyid
     */
    public Integer getBuyid() {
        return buyid;
    }

    /**
     * @param buyid
     */
    public void setBuyid(Integer buyid) {
        this.buyid = buyid;
    }

    /**
     * @return saleid
     */
    public Integer getSaleid() {
        return saleid;
    }

    /**
     * @param saleid
     */
    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
    }

    /**
     * @return old_pricce
     */
    public BigDecimal getOldPricce() {
        return oldPricce;
    }

    /**
     * @param oldPricce
     */
    public void setOldPricce(BigDecimal oldPricce) {
        this.oldPricce = oldPricce;
    }

    /**
     * @return pay_price
     */
    public BigDecimal getPayPrice() {
        return payPrice;
    }

    /**
     * @param payPrice
     */
    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * @return pp_time
     */
    public Date getPpTime() {
        return ppTime;
    }

    /**
     * @param ppTime
     */
    public void setPpTime(Date ppTime) {
        this.ppTime = ppTime;
    }

    /**
     * @return porder_time
     */
    public Date getPorderTime() {
        return porderTime;
    }

    /**
     * @param porderTime
     */
    public void setPorderTime(Date porderTime) {
        this.porderTime = porderTime;
    }

    /**
     * @return aid
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * @param aid
     */
    public void setAid(Integer aid) {
        this.aid = aid;
    }

    /**
     * @return adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
}