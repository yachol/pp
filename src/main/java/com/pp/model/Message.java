package com.pp.model;

import java.util.Date;

public class Message {
	private Integer fid;
	private Integer tid;
	private String mid;
	private String name;
	private Date time;
	private String text;
	/**
	 * @return the fid
	 */
	public Integer getFid() {
		return fid;
	}
	/**
	 * @param fid the fid to set
	 */
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	/**
	 * @return the tid
	 */
	public Integer getTid() {
		return tid;
	}
	/**
	 * @param tid the tid to set
	 */
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	/**
	 * @return the mid
	 */
	public String getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	

}
