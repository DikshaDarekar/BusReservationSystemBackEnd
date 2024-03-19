package com.userservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {
    private short id;
    private String name;
	public short getId() {
		return id;
	}
 
}
