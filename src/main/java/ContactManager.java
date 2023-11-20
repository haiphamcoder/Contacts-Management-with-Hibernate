import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ContactManager {
    private SessionFactory sessionFactory;

    public ContactManager() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Contact.class)
                .buildSessionFactory();
    }

    public void close() {
        sessionFactory.close();
    }

    public void addContact(Contact contact) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(contact);
        session.getTransaction().commit();
    }

    public void updateContact(Contact contact) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(contact);
        session.getTransaction().commit();
    }

    public void deleteContact(Contact contact) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(contact);
        session.getTransaction().commit();
    }

    public Contact getContact(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Contact contact = session.get(Contact.class, id);
        session.getTransaction().commit();
        return contact;
    }

    public List<Contact> getAllContacts() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Contact> lstContacts = session.createQuery("from Contact").getResultList();
        session.getTransaction().commit();
        return lstContacts;
    }

    public static void main(String[] args) {
        ContactManager contactManager = new ContactManager();

        // Get all contacts
        List<Contact> lstContacts = contactManager.getAllContacts();
        System.out.println("List of contacts:");
        for (Contact contact : lstContacts) {
            System.out.println(contact);
        }
        System.out.println();

        // Update contact
        Contact contact = contactManager.getContact(14L);
        contact.setName("Nguyen Van 14");
        contact.setEmail("nguyenvan14@gmail.com");
        contact.setPhone("0914141414");
        contactManager.updateContact(contact);
        lstContacts = contactManager.getAllContacts();
        System.out.println("List of contacts after updating:");
        for (Contact c : lstContacts) {
            System.out.println(c);
        }
        System.out.println();
    }
}
