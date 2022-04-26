package com.savich.maksim;

import com.savich.maksim.repository.CustomerRepository;

public class Application {

    public static void main(String... args) {
        CustomerRepository customerRepository = new CustomerRepository();

        System.out.println(customerRepository.getCustomerByFirstNameWithHql("Luke"));
    }

}
