package com.cvg.msvc.oauth.models.entity;

public class RolModel {
	private Long id;
    private String name;
    private String roleDescription;
	public RolModel(Long id, String name, String roleDescription) {
		super();
		this.id = id;
		this.name = name;
		this.roleDescription = roleDescription;
	}
	public RolModel() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
}
