package com.upgrad.restaurantmanagementsystem.services.customer;

import com.upgrad.restaurantmanagementsystem.entities.Customer;
import com.upgrad.restaurantmanagementsystem.exceptions.CustomerNotFoundException;
import com.upgrad.restaurantmanagementsystem.repositories.customer.CustomerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            int id = customerDao.save(customer);
            if (id == -1) {
                logger.warn("Customer could not be saved: {}", customer);
                throw new RuntimeException("Failed to add customer.");
            }
            logger.info("Customer added successfully with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error adding customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add customer: " + e.getMessage(), e);
        }
    }

    @Override
    public Customer getCustomer(int id) {
        try {
            Customer customer = customerDao.findById(id);
            if (customer == null) {
                logger.warn("Customer not found with ID: {}", id);
                throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
            }
            logger.info("Customer retrieved with ID: {}", id);
            return customer;
        } catch (Exception e) {
            logger.error("Error retrieving customer with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            List<Customer> customers = customerDao.findAll();
            logger.info("Retrieved {} customers.", customers.size());
            return customers;
        } catch (Exception e) {
            logger.error("Error retrieving all customers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch customers: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            customerDao.update(customer);
            logger.info("Customer updated with ID: {}", customer.getCustomerId());
        } catch (Exception e) {
            logger.error("Error updating customer with ID {}: {}", customer.getCustomerId(), e.getMessage(), e);
            throw new RuntimeException("Failed to update customer: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try {
            customerDao.delete(id);
            logger.info("Customer deleted with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting customer with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete customer: " + e.getMessage(), e);
        }
    }
}
