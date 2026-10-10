package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerManager implements CRUDOperations<Customer> {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findOrCreate(String name, String phone) {
        Customer c = customerRepository.findByPhone(phone);
        if (c != null) {
            System.out.println("Welcome back, " + c.getName() + "!");
            return c;
        }

        Customer newCustomer = new Customer(name, phone);
        return customerRepository.save(newCustomer);
    }

    @Override
    public void create(Customer item) {
        customerRepository.save(item);
    }

    @Override
    public List<Customer> read() {
        return customerRepository.findAll();
    }

    @Override
    public boolean update(int id, Customer newItem) {
        if (customerRepository.existsById(id)) {
            newItem.setId(id);
            customerRepository.save(newItem);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    @Transactional
    public boolean deleteCustomerCompletely(String phone) {
        Customer customer = customerRepository.findByPhone(phone);

        if (customer != null) {
            customerRepository.delete(customer);
            System.out.println("Клієнта з телефоном " + phone + " видалено.");
            return true;
        } else {
            throw new RuntimeException("Customer not found with phone: " + phone);
        }
    }
}