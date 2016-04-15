package com.peter.common.model;

import java.io.Serializable;

/**
 * 广告类
 * 
 * @ClassName: Advertising
 * @author peter
 * @date 2015-1-16 下午9:08:13
 * 
 */
public class Advertising implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 图片跳转地址
	 */
	private String action;

	/**
	 * 图片地址
	 */
	private String img_path;

	/**
	 * 视频地址
	 */
	private String video_path;

	/**
	 * 跳转地址
	 */
	private String url;

	/**
	 * 本地资源
	 */
	private int icon;

	/**
	 * 位置
	 */
	private int position;

	/**
	 * 类型
	 */
	private int type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getVideo_path() {
		return video_path;
	}

	public void setVideo_path(String video_path) {
		this.video_path = video_path;
	}

}
