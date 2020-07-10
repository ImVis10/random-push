package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	// Need to inject session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query.. sort by last name
		Query<Customer> q = currentSession.createQuery("from Customer order by lastName",
				Customer.class);
		
		// execute query and get result list
		List<Customer> customers = q.getResultList();
		
		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer cust) {
		
		// get current hibernate session
		Session cs = sessionFactory.getCurrentSession();
		
		// save/update the session
		cs.saveOrUpdate(cust);
	}

	@Override
	public Customer getCustomer(int id) {
		
		// get current hibernate session
		Session cs = sessionFactory.getCurrentSession();
		
		// now retrieve customer from DB using id
		Customer cust = cs.get(Customer.class, id);
		
		return cust;
		
	}

	@Override
	public void deleteCustomer(int id) {
		
		// get current hibernate session
		Session cs = sessionFactory.getCurrentSession();
		
		// deleting customer from DB using id
		Query q = cs.createQuery("delete from Customer where id=:customerId");
		
		q.setParameter("customerId", id);
		
		q.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {

            // search for firstName or lastName ... case insensitive
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class);            
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
	}

}
