package com.upgrad.restaurantmanagementsystem.repositories.role;

import com.upgrad.restaurantmanagementsystem.entities.Role;

public interface RoleDao {
    void save(Role role);
    Role findById(int roleId);
}
