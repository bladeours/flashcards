package com.flashcard;

import com.flashcard.entity.Flashcard;
import com.flashcard.entity.Score;
import com.flashcard.entity.Set;
import com.flashcard.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    public static void main(String[] argv){

//        SessionFactory factory = new Configuration()
//                .configure()
//                .addAnnotatedClass(Score.class)
//                .addAnnotatedClass(Set.class)
//                .addAnnotatedClass(User.class)
//                .addAnnotatedClass(Flashcard.class)
//                .buildSessionFactory();
//
//        Session session = factory.getCurrentSession();
//
//        try{
//           session.beginTransaction();
//
////            System.out.println(session.createQuery("select f.id, u.name from Flashcard f join User u"));
//            List<Flashcard> flashcards = session.createQuery("from Flashcard",Flashcard.class).getResultList();
//            for(Flashcard flashcard: flashcards){
//                System.out.println(flashcard.getFirstSentence() + " " + flashcard.getSecondSentence() + " " + flashcard.getUser().getName());
//            }
////            System.out.println(list.get(0).toString());
//           session.getTransaction().commit();
//        }
//        finally {
//            session.close();
//            factory.close();
//        }
        try {
            ServerSocket serverSocket = new ServerSocket(7080);
            while (true){
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();

            }
        }catch (IOException e){
            System.out.println("coudn't open socket on port 8080");
        }


    }

}
