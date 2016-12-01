package com.foxberry.voca.dto;

import java.util.ArrayList;
import java.util.List;

public class TranslateUnit {
    int id;
    private String word;
    private String translate;
    private String text;
    private String pos;
    private List<TranslateUnit> syn;
    private List<TranslateUnit> mean;
    private List<TranslateUnit> ex;
    private List<TranslateUnit> tr;


    public TranslateUnit() {
    }

    public TranslateUnit(int id, String word, String translate) {
        this.id = id;
        this.word = word;
        this.translate = translate;
    }

    public TranslateUnit(int id, String word, String translate, String text,
                         String pos, List<TranslateUnit> syn, List<TranslateUnit> mean,
                         List<TranslateUnit> ex, List<TranslateUnit> tr) {
        this.id = id;
        this.word = word;
        this.translate = translate;
//        this.synonyms = synonyms;
        this.text = text;
        this.pos = pos;
        this.syn = syn;
        this.mean = mean;
        this.ex = ex;
        this.tr = tr;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<TranslateUnit> getSyn() {
        return syn;
    }

    public void setSyn(List<TranslateUnit> syn) {
        this.syn = syn;
    }

    public List<TranslateUnit> getMean() {
        return mean;
    }

    public void setMean(List<TranslateUnit> mean) {
        this.mean = mean;
    }

    public List<TranslateUnit> getEx() {
        return ex;
    }

    public void setEx(List<TranslateUnit> ex) {
        this.ex = ex;
    }

    public List<TranslateUnit> getTr() {
        return tr;
    }

    public List<String> getTranslates(){
        List<String> translates = new ArrayList<>();
        for(TranslateUnit unit : getTr()){
            translates.add(unit.getText());
        }
        return translates;
    }

    public void setTr(List<TranslateUnit> tr) {
        this.tr = tr;
    }

    public List<String> getSynonyms(){
        List<String> synonyms = new ArrayList<>();
        if (getSyn()!=null) {
            for (TranslateUnit unit : getSyn()) {
                synonyms.add(unit.getText());
            }
        }
        return synonyms;
    }
}
