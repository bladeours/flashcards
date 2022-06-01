package com.flashcard.controller;

import com.flashcard.dto.ColorDTO;
import com.flashcard.entity.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String getColors(){
        Session session = factory.getCurrentSession();
        String colorsJson;
        try{
           session.beginTransaction();
           ArrayList<ColorDTO> colorsArrayList = new ArrayList<>();
            List<Color> colors = session.createQuery("From Color", Color.class).getResultList();
            for(Color color: colors){
                ColorDTO colorDTO = new ColorDTO(color.getId(),color.getCode());
                colorsArrayList.add(colorDTO);
            }
            session.getTransaction().commit();

            Gson gson = new Gson();
            colorsJson = gson.toJson(colorsArrayList);

        }
        finally {
            session.close();
//            factory.close();
        }

        return colorsJson;
    }

    public void createSet(JsonObject setJson){
        Session session = factory.getCurrentSession();
        try{
            //get sentences
            JsonElement sentencesJson = setJson.get("sentences");
            Type type = new TypeToken<ArrayList<Map<String,String>>>(){}.getType();
            ArrayList<Map<String,String>> sentences = new Gson().fromJson(sentencesJson,type);
            System.out.println("sentences: "+sentences);

            session.beginTransaction();
            //get color id
            Query query = session.createQuery("from Color where code =:code ",Color.class)
                    .setParameter("code", setJson.get("color").getAsString());
            Color color = (Color) query.uniqueResult();

            Set set = new Set(setJson.get("setName").getAsString(),color);

            ArrayList<Flashcard> flashcards = new ArrayList<>();
            for(Map<String, String> sentence : sentences) {
                    Flashcard flashcard = new Flashcard(set,sentence.get("firstSentence"),
                            sentence.get("secondSentence"));
                    flashcards.add(flashcard);
                }
            set.setFlashcards(flashcards);
            session.persist(set);
            session.getTransaction(). commit();
        }
        finally {
            session.close();
        }

    }

}
