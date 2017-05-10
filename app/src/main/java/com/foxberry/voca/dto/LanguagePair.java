package com.foxberry.voca.dto;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LanguagePair {
    private String name;
    private String firstLanguage;
    private String secondLanguage;
    @Generated(hash = 1262549715)
    public LanguagePair(String name, String firstLanguage, String secondLanguage) {
        this.name = name;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
    }
    @Generated(hash = 76104420)
    public LanguagePair() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFirstLanguage() {
        return this.firstLanguage;
    }
    public void setFirstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
    }
    public String getSecondLanguage() {
        return this.secondLanguage;
    }
    public void setSecondLanguage(String secondLanguage) {
        this.secondLanguage = secondLanguage;
    }
}
