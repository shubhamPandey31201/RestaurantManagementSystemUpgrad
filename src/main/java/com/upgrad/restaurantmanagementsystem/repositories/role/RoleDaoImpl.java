package com.upgrad.restaurantmanagementsystem.repositories.role;

import com.upgrad.restaurantmanagementsystem.entities.Role;
import com.upgrad.restaurantmanagementsystem.entities.RoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.upgrad.restaurantmanagementsystem.ddl.Role.createRoleTableIfNotExists;
import static com.upgrad.restaurantmanagementsystem.utils.DatabaseUtil.getConnection;

public class RoleDaoImpl implements RoleDao {

    private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    private final Connection connection;

    public RoleDaoImpl(Connection connection) throws SQLException {
        this.connection = getConnection();
        createRoleTableIfNotExists();
        logger.info("Role table initialized");
    }

    @Override
    public void save(Role role) {
        String sql = "INSERT INTO role (role_id, role_name) VALUES (?, ?) ON CONFLICT (role_id) DO NOTHING";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, role.getRoleId());
            stmt.setString(2, role.getRoleName().name());
            int rowsAffected = stmt.executeUpdate();
            logger.info("Role saved: {} | Rows affected: {}", role, rowsAffected);
        } catch (SQLException e) {
            logger.error("Error saving role: {}", role, e);
            throw new RuntimeException("Error saving role", e);
        }
    }

    @Override
    public Role findById(int roleId) {
        String sql = "SELECT * FROM role WHERE role_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Role role = new Role.Builder()
                        .roleId(rs.getInt("role_id"))
                        .roleName(RoleName.valueOf(rs.getString("role_name")))
                        .build();
                logger.info("Role found: {}", role);
                return role;
            } else {
                logger.warn("No role found with ID: {}", roleId);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving role with ID: {}", roleId, e);
            throw new RuntimeException("Error retrieving role", e);
        }
        return null;
    }
}
