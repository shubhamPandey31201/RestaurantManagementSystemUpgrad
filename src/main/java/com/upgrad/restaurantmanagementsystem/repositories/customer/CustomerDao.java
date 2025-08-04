package com.upgrad.restaurantmanagementsystem.repositories.customer;

import com.upgrad.restaurantmanagementsystem.entities.Customer;

import java.util.List;

public interface CustomerDao {
    int save(Customer customer);
    Customer findById(int id);
    List<Customer> findAll();
    void update(Customer customer);
    void delete(int id);
}
