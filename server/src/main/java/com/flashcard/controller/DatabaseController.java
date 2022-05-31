package com.flashcard.controller;

import com.flashcard.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseController {

    private SessionFactory factory;
    public DatabaseController() {
        factory = new Configuration()
                .configure()
                .addAnnotatedClass(Score.class)
                .addAnnotatedClass(Set.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Flashcard.class)
                .addAnnotatedClass(Color.class)
                .buildSessionFactory();
    }

    public String getColor(){
        Session session = factory.getCurrentSession();
        try{
           session.beginTransaction();
            Flashcard flashcard = session.get(Flashcard.class,4);
            System.out.println(flashcard.getSet().getUser());

           session.getTransaction().commit();
        }
        finally {
            session.close();
            factory.close();
        }

        return null;
    }

}
