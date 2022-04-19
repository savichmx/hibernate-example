package com.savich.maksim;

import com.savich.maksim.repository.HibernateUtils;

public class Application {

    public static void main(String... args) {
        HibernateUtils.getSessionFactory();
    }

}
