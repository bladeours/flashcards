package com.flashcard.controller;

import com.flashcard.dto.ColorDTO;
import com.flashcard.dto.FlashcardDTO;
import com.flashcard.dto.ScoreDTO;
import com.flashcard.dto.SetDTO;
import com.flashcard.entity.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public void editSet(JsonObject setJson) {
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

            Set setToEdt = session.get(Set.class,Integer.valueOf(setJson.get("setToEditId").getAsString()));
            setToEdt.setColor(color);
            setToEdt.setName(setJson.get("setName").getAsString());

            int flashcardId;
            for(Map<String, String> sentence : sentences) {
                System.out.println(sentence.get("flashcardId"));
                if(!sentence.get("flashcardId").equals("null")){
                    flashcardId = Integer.parseInt(sentence.get("flashcardId"));
                    Flashcard flashcardToEdit = session.get(Flashcard.class,flashcardId);
                    flashcardToEdit.setFirstSentence(sentence.get("firstSentence"));
                    flashcardToEdit.setSecondSentence(sentence.get("secondSentence"));
                    System.out.println("flashcard to edit" + flashcardToEdit);
                }else{
                    Flashcard flashcardNew = new Flashcard(setToEdt,sentence.get("firstSentence"),
                        sentence.get("secondSentence"));
                    session.persist(flashcardNew);
                }

            }
            session.getTransaction(). commit();
        }
        finally {
            session.close();
        }
    }


    public String getSets(){
        Session session = factory.getCurrentSession();
        String setsJson;
        try{
            session.beginTransaction();
            List<Set> sets = session.createQuery("From Set",Set.class).getResultList();

            ArrayList<SetDTO> setsDTO = new ArrayList<>();
            for(Set set: sets){
                setsDTO.add(new SetDTO(set.getId(),set.getName(),set.getColor().getCode()));
            }
            setsJson = new Gson().toJson(setsDTO);
            session.getTransaction().commit();
        }finally {
            session.close();
        }

        return setsJson;
    }



    public String getFlashcardsForSet(int set_id) {
        Session session = factory.getCurrentSession();
        String flashcardsJson;
        try{
            session.beginTransaction();

            List<Flashcard> flashcards = session.createQuery("from Flashcard f where f.set.id =:set_id",Flashcard.class)
                    .setParameter("set_id",set_id).getResultList();

            ArrayList<FlashcardDTO> flashcardsDTO = new ArrayList<>();

            for(Flashcard flashcard: flashcards){
                flashcardsDTO.add(new FlashcardDTO(flashcard.getId(),flashcard.getFirstSentence(),flashcard.getSecondSentence(),
                        flashcard.getFirstCorrect(),flashcard.getSecondCorrect()));
            }

            flashcardsJson = new Gson().toJson(flashcardsDTO);
            session.getTransaction().commit();


        }finally {
            session.close();
        }
        return flashcardsJson;
    }

    public void updateCorrectSentence(int flashcard_id, String whichSentence) {
        Session session = factory.getCurrentSession();
        try{
            session.beginTransaction();
            Flashcard flashcard = session.get(Flashcard.class,flashcard_id);
            if(whichSentence.equals("first")){
                flashcard.setFirstCorrect(1);
            }else{
                flashcard.setSecondCorrect(1);
            }

            session.getTransaction().commit();


        }finally {
            session.close();
        }

    }

    public void removeSet(int set_id) {
        Session session = factory.getCurrentSession();
        try{
            session.beginTransaction();
            Set set = session.get(Set.class,set_id);
            session.remove(set);

            session.getTransaction().commit();


        }finally {
            session.close();
        }
    }


    public String getAllScores() {
        String scoresJson;
        Session session = factory.getCurrentSession();
        try{
            session.beginTransaction();

            List<Score> scores = session.createQuery("from Score",Score.class).getResultList();

            ArrayList<ScoreDTO> scoresDTO = new ArrayList<>();

            for(Score score: scores){
                scoresDTO.add(new ScoreDTO(score.getId(),score.getSet().getId(),score.getSet().getName(),
                        score.getScoreFirst(),score.getScoreSecond(), score.getSet().getFlashcards().size()));
            }
//            System.out.println(scoresDTO);
            scoresJson = new Gson().toJson(scoresDTO);
            System.out.println(scoresJson);
            session.getTransaction().commit();


        }finally {
            session.close();
        }
        return scoresJson;
    }

    public void resetFirstScore(int setId) {
        Session session = factory.getCurrentSession();
        try{
            session.beginTransaction();

            List<Flashcard> flashcards = session.createQuery("from Flashcard where set.id=:setId",Flashcard.class)
                    .setParameter("setId",setId)
                    .getResultList();

            for(Flashcard flashcard: flashcards){
                flashcard.setFirstCorrect(0);
            }
            session.getTransaction().commit();


        }finally {
            session.close();
        }
    }

    public void resetSecondScore(int setId) {
        Session session = factory.getCurrentSession();
        try{
            session.beginTransaction();

            List<Flashcard> flashcards = session.createQuery("from Flashcard where set.id=:setId",Flashcard.class)
                    .setParameter("setId",setId)
                    .getResultList();

            for(Flashcard flashcard: flashcards){
                flashcard.setSecondCorrect(0);
            }
            session.getTransaction().commit();


        }finally {
            session.close();
        }
    }
}
