package com.ggw.app.domain.chart.msg;
/**
 * 地理位置消息
 * @author ggw
 *
 */

public class LocationMessage extends BaseMessage {
	//地理位置维度
	private String Location_x;
	//地理位置经度
	private String Location_y;
	//地图缩放比例
	private String Scale;
	//地理位置信息
	private String Lable;
	
	public String getLocation_x() {
		return Location_x;
	}
	public void setLocation_x(String location_x) {
		Location_x = location_x;
	}
	public String getLocation_y() {
		return Location_y;
	}
	public void setLocation_y(String location_y) {
		Location_y = location_y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLable() {
		return Lable;
	}
	public void setLable(String lable) {
		Lable = lable;
	}
}
