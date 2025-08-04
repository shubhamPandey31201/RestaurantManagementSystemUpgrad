package com.upgrad.restaurantmanagementsystem.services.role;

import com.upgrad.restaurantmanagementsystem.entities.Role;

public interface RoleService {
    void addRole(Role role);
    Role getRoleById(int roleId);
}
