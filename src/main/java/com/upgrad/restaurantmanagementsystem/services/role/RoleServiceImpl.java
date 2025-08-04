package com.upgrad.restaurantmanagementsystem.services.role;

import com.upgrad.restaurantmanagementsystem.entities.Role;
import com.upgrad.restaurantmanagementsystem.repositories.role.RoleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void addRole(Role role) {
        try {
            logger.info("Adding role: {}", role);
            roleDao.save(role);
        } catch (Exception e) {
            logger.error("Failed to add role: {}", role, e);
            throw new RuntimeException("Service failed to add role", e);
        }
    }

    @Override
    public Role getRoleById(int roleId) {
        try {
            logger.info("Retrieving role by ID: {}", roleId);
            return roleDao.findById(roleId);
        } catch (Exception e) {
            logger.error("Failed to retrieve role with ID: {}", roleId, e);
            throw new RuntimeException("Service failed to retrieve role", e);
        }
    }
}
