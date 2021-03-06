package gaidadym.javaForTesters.addressbook.appmanager;

import gaidadym.javaForTesters.addressbook.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.*;

public class DbHelper {
    private final SessionFactory sessionFactory;

    public DbHelper(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public Groups groups(boolean withDeleted){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result;
        if (withDeleted){
            result = session.createQuery( "from GroupData" ).list();
        }else{
            result = session.createQuery( "from GroupData where deprecated = '0000-00-00 00:00:00'" ).list();
        }
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public List<ContactGroups> groupsForContact(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactGroups> result;
        result = session.createQuery( "from ContactGroups where deprecated = '0000-00-00 00:00:00' and id = "+id).list();
        session.getTransaction().commit();
        session.close();
        List <ContactGroups>groups = null;
        return result;
    }

    public HashSet<ContactGroups> groupsForAllContacts(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactGroups> result;
        result = session.createQuery( "from ContactGroups where deprecated = 0000-00-00").list();
        session.getTransaction().commit();
        session.close();
        return new HashSet<ContactGroups>(result);
    }

    public Contacts contacts(boolean withDeleted){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result;
        if (withDeleted) {
            result = session.createQuery("from ContactData").list();
        }
        else{
            result = session.createQuery("from ContactData where deprecated = 0000-00-00").list();
        }
        session.getTransaction().commit();
        session.close();
        return new Contacts(result);
    }
    public ContactData refreshContact(int id){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result;
        result = session.createQuery( "from ContactData where deprecated = '0000-00-00 00:00:00' and id = "+id).list();
        session.getTransaction().commit();
        session.close();
        ContactData refreshedContact = new ContactData();
        refreshedContact = result.get(0);
        return refreshedContact;
    }


    public List <ContactGroups> groupDataToContGroups(int groupId, int contactId){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactGroups> result;
        result = session.createQuery(String.format("from ContactGroups where id = %s and group_id = %s",contactId,groupId)).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
