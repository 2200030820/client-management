package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ClientDemo {

    private static SessionFactory factory;

    static {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        factory = configuration.buildSessionFactory();
    }

    public static void main(String[] args) {
        insertClient(new Client("chetan", "Male", 21, "guntur", "chetan@gmail.com", "9876542231"));
        
        List<Client> clients = getAllClients();
        for (Client client : clients) {
            System.out.println(client.getName() + ", " + client.getGender() + ", " + client.getAge());
            
        }
        
        factory.close();
    }

    public static void insertClient(Client client) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Client> getAllClients() {
        Session session = factory.openSession();
        List<Client> clients = null;

        try {
            clients = session.createQuery("FROM Client").list();
        } finally {
            session.close();
        }
        
        return clients;
    }
}
