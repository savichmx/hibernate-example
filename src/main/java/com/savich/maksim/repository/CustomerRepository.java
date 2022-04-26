package com.savich.maksim.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.savich.maksim.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CustomerRepository {

    public void create(Customer customer) {
        Transaction transaction = HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
        try {
            HibernateUtils.getSessionFactory().getCurrentSession().save(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void update(Customer customer) {
        Transaction transaction = HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
        try {
            HibernateUtils.getSessionFactory().getCurrentSession().update(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void delete(Customer customer) {
        Transaction transaction = HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
        try {
            HibernateUtils.getSessionFactory().getCurrentSession().delete(customer);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Customer getById(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Customer.class, id);
        }
    }

    public List<Customer> getCustomerByFirstNameWithSql(String firstName) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String sql = "SELECT id, first_name, last_name FROM customers where first_name = :first_name";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("first_name", firstName);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            return query.list();
        }
    }

    public List<Customer> getCustomerByFirstNameWithHql(String firstName) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String hql = "SELECT new Customer(c.id, c.firstName) FROM Customer c WHERE c.firstName = :first_name";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("first_name", firstName);
            return query.list();
        }
    }

    public List<Customer> getCustomerByFirstNameAndAgeWithCriteriaApi(String firstName, int age) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            criteriaQuery.select(root).where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("firstName"), firstName),
                            criteriaBuilder.equal(root.get("age"), age)
                    )
            );

            Query<Customer> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }



}
