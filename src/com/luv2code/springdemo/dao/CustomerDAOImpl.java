package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
			
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
		// create a query  ... sort by last name
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by lastName",
											Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save the customer if new or updates for existing customer
		currentSession.saveOrUpdate(theCustomer);
		
		
		
	}

	@Override
	public Customer getCustomer(int cid) {
		// TODO Auto-generated method stub
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		// read the data from database using id
		
		Customer cust = currentSession.get(Customer.class, cid);
		
		return cust;
	}

	@Override
	public void deleteCustomer(int id) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		
		theQuery.setParameter("customerId", id);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public List<Customer> searchCustomer(String searchName) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = null;
		
		if(searchName != null && searchName.trim().length() > 0){
			
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like:theName or lower(lastName) like :theName",Customer.class);
		
			theQuery.setParameter("theName", "%"+searchName.toLowerCase()+"%");
		}
		else
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
	}

}






