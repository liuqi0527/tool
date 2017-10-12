package com.server.tool.xls.template;

public class ArmyTitleTemplate implements KeySupport<Integer>{
	private int id_i;
	private String name_s;
	private int level_i;
	private int star_i;
	
	@Override
	public Integer getKey(){
		return this.id_i;
	}

	public int getId_i() {
		return id_i;
	}

	public void setId_i(int id_i) {
		this.id_i = id_i;
	}

	public String getName_s() {
		return name_s;
	}

	public void setName_s(String name_s) {
		this.name_s = name_s;
	}

	public int getLevel_i() {
		return level_i;
	}

	public void setLevel_i(int level_i) {
		this.level_i = level_i;
	}

	public int getStar_i() {
		return star_i;
	}

	public void setStar_i(int star_i) {
		this.star_i = star_i;
	}
}
