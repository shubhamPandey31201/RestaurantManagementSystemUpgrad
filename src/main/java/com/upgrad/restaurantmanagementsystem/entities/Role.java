package com.upgrad.restaurantmanagementsystem.entities;

public class Role {
    private final int roleId;
    private final RoleName roleName;

    private Role(Builder builder) {
        this.roleId = builder.roleId;
        this.roleName = builder.roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public static class Builder {
        private int roleId;
        private RoleName roleName;

        public Builder roleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder roleName(RoleName roleName) {
            this.roleName = roleName;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
