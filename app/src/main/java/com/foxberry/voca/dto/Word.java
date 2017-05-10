package com.foxberry.voca.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Word {
    @Id(autoincrement = true)
    private long id;

    private String word;
    private String language;
    @Generated(hash = 1990290252)
    public Word(long id, String word, String language) {
        this.id = id;
        this.word = word;
        this.language = language;
    }
    @Generated(hash = 3342184)
    public Word() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getLanguage() {
        return this.language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

}
