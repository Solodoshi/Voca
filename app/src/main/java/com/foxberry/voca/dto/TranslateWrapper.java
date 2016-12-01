package com.foxberry.voca.dto;


import java.util.List;

public class TranslateWrapper {
    List<TranslateUnit> def;

    public TranslateUnit getDef() {
        if(def!=null && def.size()>0) {
            return def.get(0);
        }
        return null;
    }
}
