package com.userservice.mapper;

import com.userservice.dto.RoleDTO;
import com.userservice.entities.role.Role;
import org.modelmapper.ModelMapper;

public class RoleMapper {

    public Role toEntity(RoleDTO roleDTO) {
        ModelMapper mapper=new ModelMapper();
        Role role=mapper.map(roleDTO,Role.class);
        return role;
    }

    public RoleDTO toDTO(Role role) {
        ModelMapper mapper=new ModelMapper();
        RoleDTO roleDTO=mapper.map(role,RoleDTO.class);
        return roleDTO;
    }

}
