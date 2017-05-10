package com.foxberry.voca.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WordPair {
    @Id(autoincrement = true)
    private long id;
    private int languagePairId;
    private long wordId;
    private long translateId;
    @Generated(hash = 2132140628)
    public WordPair(long id, int languagePairId, long wordId, long translateId) {
        this.id = id;
        this.languagePairId = languagePairId;
        this.wordId = wordId;
        this.translateId = translateId;
    }
    @Generated(hash = 1310993386)
    public WordPair() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getLanguagePairId() {
        return this.languagePairId;
    }
    public void setLanguagePairId(int languagePairId) {
        this.languagePairId = languagePairId;
    }
    public long getWordId() {
        return this.wordId;
    }
    public void setWordId(long wordId) {
        this.wordId = wordId;
    }
    public long getTranslateId() {
        return this.translateId;
    }
    public void setTranslateId(long translateId) {
        this.translateId = translateId;
    }
}
