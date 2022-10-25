package com.example.testjwt.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "t_permission")
public class Permission extends BaseEntity {

    private String permissionName;

    private String permissionKey;

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}
    
    

}
