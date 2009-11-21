package com.pioneer.app.vo;

public class Column {
	private String name;//列名
	private String type;//列类型
	private String fkTable;//参考表
	private String fkTableStruct=null;
	private String fkTableId;//参考表的主键。
	private int winWidth=250;
	private int winHeight=250;
	private int labelWidth=60;
	public int getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFkTable() {
		return fkTable;
	}
	public void setFkTable(String fkTable) {
		this.fkTable = fkTable;
	}
	
	public String getFkTableId() {
		return fkTableId;
	}
	public void setFkTableId(String fkTableId) {
		this.fkTableId = fkTableId;
	}
	public int getWinWidth() {
		return winWidth;
	}
	public void setWinWidth(int winWidth) {
		this.winWidth = winWidth;
	}
	public int getWinHeight() {
		return winHeight;
	}
	public void setWinHeight(int winHeight) {
		this.winHeight = winHeight;
	}
	public String getFkTableStruct() {
		return fkTableStruct;
	}
	public void setFkTableStruct(String fkTableStruct) {
		this.fkTableStruct = fkTableStruct;
	}
	
	

}
