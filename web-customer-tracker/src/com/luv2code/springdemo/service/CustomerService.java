package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.entity.Customer;

// Interface for customer servi
public interface CustomerService {
	
	public List<Customer> getCustomers();

	public void saveCustomer(Customer cust);

	public Customer getCustomer(int id);

	public void deleteCustomer(int id);

	public List<Customer> searchCustomers(String theSearchName);
}