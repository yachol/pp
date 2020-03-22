package com.pp.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Address implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer uid;

    private String aname;

    private String phone;

    private String adress;

    @Column(name = "post_code")
    private String postCode;

    private String isdefault;

    private static final long serialVersionUID = 1L;

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
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return aname
     */
    public String getAname() {
        return aname;
    }

    /**
     * @param aname
     */
    public void setAname(String aname) {
        this.aname = aname;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * @return post_code
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return isdefault
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }
}