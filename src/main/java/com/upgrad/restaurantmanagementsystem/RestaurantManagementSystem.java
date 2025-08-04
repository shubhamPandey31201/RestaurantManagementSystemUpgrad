package com.upgrad.restaurantmanagementsystem;

import com.upgrad.restaurantmanagementsystem.entities.*;
import com.upgrad.restaurantmanagementsystem.repositories.customer.*;
import com.upgrad.restaurantmanagementsystem.repositories.kitchenOrder.*;
import com.upgrad.restaurantmanagementsystem.repositories.menuItem.*;
import com.upgrad.restaurantmanagementsystem.repositories.order.*;
import com.upgrad.restaurantmanagementsystem.repositories.orderItem.*;
import com.upgrad.restaurantmanagementsystem.repositories.restaurantTable.*;
import com.upgrad.restaurantmanagementsystem.repositories.role.*;
import com.upgrad.restaurantmanagementsystem.repositories.user.*;
import com.upgrad.restaurantmanagementsystem.repositories.waiterDetail.*;
import com.upgrad.restaurantmanagementsystem.services.customer.*;
import com.upgrad.restaurantmanagementsystem.services.kitchenOrder.*;
import com.upgrad.restaurantmanagementsystem.services.menuItem.*;
import com.upgrad.restaurantmanagementsystem.services.order.*;
import com.upgrad.restaurantmanagementsystem.services.orderItem.*;
import com.upgrad.restaurantmanagementsystem.services.role.*;
import com.upgrad.restaurantmanagementsystem.services.salesReport.*;
import com.upgrad.restaurantmanagementsystem.services.tableBooking.*;
import com.upgrad.restaurantmanagementsystem.services.waterDetail.*;
import com.upgrad.restaurantmanagementsystem.utils.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.*;

@SpringBootApplication
public class RestaurantManagementSystem {

    private static final Logger LOGGER = Logger.getLogger(RestaurantManagementSystem.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementSystem.class, args);
        LOGGER.info("Restaurant Management System is running...");

        try {
            CustomerServiceImpl customerService = new CustomerServiceImpl(new CustomerDaoImpl());
            Customer newCustomer = new Customer.Builder()
                    .name("Alice Johnson")
                    .phone("9876543210")
                    .email("alice@example.com")
                    .address("123 Main St, Springfield")
                    .build();
            customerService.addCustomer(newCustomer);
            LOGGER.info("Customer added successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while adding customer", e);
        }

        try {
            RestaurantTableDao tableDao = new RestaurantTableDaoImpl();
            RestaurantTable newTable = new RestaurantTable.Builder()
                    .capacity(4)
                    .locationDescription("Near window")
                    .build();
            tableDao.save(newTable);
            LOGGER.info("Saved new table.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while saving table", e);
        }

        try {
            MenuItemDao menuItemDao = new MenuItemDaoImpl();
            MenuItemService menuItemService = new MenuItemServiceImpl(menuItemDao);
            MenuItem item = new MenuItem.Builder()
                    .name("Veg Biryani")
                    .description("Spiced rice with vegetables")
                    .price(180.00)
                    .build();
            menuItemService.addMenuItem(item);
            LOGGER.info("Menu item added.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while adding menu item", e);
        }

        try {
            OrderItemDao orderItemDao = new OrderItemDaoImpl();
            OrderItemService orderItemService = new OrderItemServiceImpl(orderItemDao);
            OrderItem newItem = new OrderItem.Builder()
                    .orderId(1)
                    .menuId(101)
                    .quantity(2)
                    .totalPrice(500.0)
                    .build();
            orderItemService.addOrderItem(newItem);
            LOGGER.info("Order item saved.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while saving order item", e);
        }

        try {
            KitchenOrderDao kitchenOrderDao = new KitchenOrderDaoImpl();
            KitchenOrderService kitchenOrderService = new KitchenOrderServiceImpl(kitchenOrderDao);
            KitchenOrder order = new KitchenOrder.Builder()
                    .orderItemId(101)
                    .status(KitchenOrderStatus.PREPARING)
                    .lastUpdatedTime(LocalDateTime.now())
                    .build();
            kitchenOrderService.createOrder(order);
            LOGGER.info("Kitchen order saved.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while creating kitchen order", e);
        }

        try {
            SalesReportService salesReportService = new SalesReportServiceImpl();
            SalesReport report = new SalesReport.Builder()
                    .generatedByUserId(1)
                    .generatedDate(LocalDateTime.now())
                    .totalRevenue(15250.75)
                    .totalOrders(32)
                    .build();
            salesReportService.createReport(report);
            LOGGER.info("Sales report created.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while creating sales report", e);
        }

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/restaurant_db", "postgres", "your_password")) {
            WaiterDetailDaoImpl waiterDetailsDao = new WaiterDetailDaoImpl(connection);
            WaiterDetailService waiterDetailsService = new WaiterDetailServiceImpl(waiterDetailsDao);
            WaiterDetail waiter = new WaiterDetail.Builder()
                    .userId(101)
                    .shift("Evening")
                    .build();
            waiterDetailsService.addWaiterDetails(waiter);
            LOGGER.info("Waiter detail added.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while adding waiter details", e);
        }

        try {
            OrderDao orderDao = new OrderDaoImpl();
            OrderService orderService = new OrderServiceImpl(orderDao);
            Customer customer = new Customer.Builder()
                    .customerId(1)
                    .name("Shubham")
                    .phone("9876543210")
                    .email("shubham@example.com")
                    .build();
            MenuItem item1 = new MenuItem.Builder().menuId(1).name("Pasta").price(250.0).build();
            MenuItem item2 = new MenuItem.Builder().menuId(2).name("Pizza").price(400.0).build();
            List<MenuItem> itemList = new ArrayList<>();
            itemList.add(item1);
            itemList.add(item2);
            Order newOrder = new Order.Builder()
                    .orderId(0)
                    .customer(customer)
                    .items(itemList)
                    .totalAmount(650.0)
                    .build();
            orderService.createOrder(newOrder);
            LOGGER.info("Order created.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while creating order", e);
        }

        try (Connection connection = DatabaseUtil.getConnection()) {
            RoleDao roleDao = new RoleDaoImpl(connection);
            RoleService roleService = new RoleServiceImpl(roleDao);
            Role newRole = new Role.Builder().roleId(1).roleName(RoleName.ADMIN).build();
            roleService.addRole(newRole);
            LOGGER.info("Role added.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while adding role", e);
        }

        try {
            CustomerDao customerDao = new CustomerDaoImpl();
            Customer customer = new Customer.Builder()
                    .name("Shubham Pandey")
                    .phone("9876543210")
                    .email("shubham@example.com")
                    .address("Some address")
                    .build();
            int customerId = customerDao.save(customer);
            if (customerId == -1) {
                LOGGER.warning("Customer insert failed!");
                return;
            }
            Customer savedCustomer = new Customer.Builder()
                    .customerId(customerId)
                    .name(customer.getName())
                    .phone(customer.getPhone())
                    .email(customer.getEmail())
                    .address(customer.getAddress())
                    .build();
            TableBooking booking = new TableBooking.Builder()
                    .tableNumber(5)
                    .customer(savedCustomer)
                    .bookingTime(LocalDateTime.now())
                    .build();
            TableBookingService bookingService = new TableBookingServiceImpl();
            bookingService.bookTable(booking);
            LOGGER.info("Table booked.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while booking table", e);
        }

        try {
            UserDao userDao = new UserDaoImpl();
            Role role = new Role.Builder().roleId(1).roleName(RoleName.ADMIN).build();
            User user = new User.Builder()
                    .userId(0)
                    .username("shubham_admin")
                    .password("secure123")
                    .role(role)
                    .build();
            userDao.save(user);
            LOGGER.info("User saved successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while saving user", e);
        }
    }
}
