package com.kseb.electricbillingservice.services;

import com.kseb.electricbillingservice.dto.CustomerDto;
import com.kseb.electricbillingservice.entity.Customer;
import com.kseb.electricbillingservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    /**
     * Method to save customer information to db
     * @param customerData
     * @return
     */
    @Transactional
    public CustomerDto addCustomer(CustomerDto customerData){

        //perform any business logic

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerData,customer);
        customerRepository.save(customer);

        return customerData;
    }
}
