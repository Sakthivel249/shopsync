package com.super_market.model;

import java.util.*;

public class RolePermissions {
    private static final Map<Role, Set<Permission>> rolePermissions = new HashMap<>();

    static {
        rolePermissions.put(Role.ADMIN, new HashSet<>(Arrays.asList(
                Permission.CHANGE_PASSWORD,
                Permission.CREATE_EMPLOYEE, Permission.DELETE_EMPLOYEE, Permission.UPDATE_EMPLOYEE, Permission.READ_ALL_EMPLOYEES,
                Permission.CREATE_PRODUCT, Permission.DELETE_PRODUCT, Permission.UPDATE_PRODUCT, Permission.READ_ALL_PRODUCTS, Permission.READ_SECTION_PRODUCTS,
                Permission.CREATE_SECTION, Permission.DELETE_SECTION, Permission.UPDATE_SECTION, Permission.READ_ALL_SECTIONS,
                Permission.CREATE_RECEIPT, Permission.DELETE_RECEIPT, Permission.READ_ALL_RECEIPTS, Permission.READ_CASHIER_RECEIPTS, Permission.READ_MY_RECEIPTS, Permission.READ_RECEIPT
        )));

        rolePermissions.put(Role.CASHIER, new HashSet<>(Arrays.asList(
                Permission.CHANGE_PASSWORD,
                Permission.CASHIER_CREATE_RECEIPT, Permission.CASHIER_DELETE_RECEIPT,
                Permission.CASHIER_READ_ALL_RECEIPTS, Permission.CASHIER_READ_MY_RECEIPTS, Permission.CASHIER_READ_RECEIPT
        )));

        rolePermissions.put(Role.STOREKEEPER, new HashSet<>(Arrays.asList(
                Permission.CHANGE_PASSWORD,
                Permission.CREATE_PRODUCT, Permission.DELETE_PRODUCT, Permission.UPDATE_PRODUCT, Permission.READ_ALL_PRODUCTS, Permission.READ_SECTION_PRODUCTS,
                Permission.CREATE_SECTION, Permission.DELETE_SECTION, Permission.UPDATE_SECTION, Permission.READ_ALL_SECTIONS
        )));
    }

    public static boolean hasPermission(Role role, Permission permission) {
        return rolePermissions.getOrDefault(role, Collections.emptySet()).contains(permission);
    }

    public static Set<Permission> getPermissionsForRole(Role role) {
        return rolePermissions.getOrDefault(role, Collections.emptySet());
    }
}
