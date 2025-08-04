package com.upgrad.restaurantmanagementsystem.services.customer;

import com.upgrad.restaurantmanagementsystem.entities.Customer;

import java.util.List;

public interface ICustomerService {
    void addCustomer(Customer customer);
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
}
