package com.pp.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Chat implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "Funame")
    private String funame;

    @Column(name = "Fuid")
    private Integer fuid;

    @Column(name = "Tuname")
    private String tuname;

    @Column(name = "Tuid")
    private Integer tuid;

    private Integer unread;

    private String mid;

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
     * @return Funame
     */
    public String getFuname() {
        return funame;
    }

    /**
     * @param funame
     */
    public void setFuname(String funame) {
        this.funame = funame;
    }

    /**
     * @return Fuid
     */
    public Integer getFuid() {
        return fuid;
    }

    /**
     * @param fuid
     */
    public void setFuid(Integer fuid) {
        this.fuid = fuid;
    }

    /**
     * @return Tuname
     */
    public String getTuname() {
        return tuname;
    }

    /**
     * @param tuname
     */
    public void setTuname(String tuname) {
        this.tuname = tuname;
    }

    /**
     * @return Tuid
     */
    public Integer getTuid() {
        return tuid;
    }

    /**
     * @param tuid
     */
    public void setTuid(Integer tuid) {
        this.tuid = tuid;
    }

    /**
     * @return unread
     */
    public Integer getUnread() {
        return unread;
    }

    /**
     * @param unread
     */
    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    /**
     * @return mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }
}