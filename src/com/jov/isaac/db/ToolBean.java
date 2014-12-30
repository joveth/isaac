package com.jov.isaac.db;

public class ToolBean {
	private int id;
	private String img;
	private String name;
	private String enName;
	private String desc;
	private String power;
	private String unlock;
	// "全部", "主动", "被动", "饰品","塔牌", "符文", "胶囊", "人物", "成就", "挑战"
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getUnlock() {
		return unlock;
	}

	public void setUnlock(String unlock) {
		this.unlock = unlock;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
