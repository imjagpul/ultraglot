/*
 * LanguageDefinition.java
 *
 * Created on 9. zברם 2007, 20:37
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.xml.WordType;
import cz.srubarovi.teacher.xml.WordTypes;
import java.util.List;


/**
 *
 * @author Stepan
 */
public class LanguageDefinition {
    
//    private final List<WordType> wordTypes;
    private final WordTypes wordTypes;
    private final String langcode;
    private final String name;
    private final String legalCharacters;
    
    public LanguageDefinition(String name, String langcode, WordTypes wordTypes, String legalCharacters) {
        this.name=name;
        this.langcode=langcode;
        this.wordTypes=wordTypes;
        this.legalCharacters=legalCharacters;
    }
    
    public LanguageDefinition(cz.srubarovi.teacher.xml.Language language) {
        this.name=language.getName();
        this.langcode=language.getLangcode();
        this.wordTypes=language.getWordTypes();
        if(language.getAccentDefinition()!=null)
            this.legalCharacters=language.getAccentDefinition().getLegalCharacters();
        else
            this.legalCharacters=null;
    }
    
    public WordTypes getWordTypes() {
        return wordTypes;
    }
    
    public String getLangcode() {
        return langcode;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isAccentLegal(char forCharacter) {
        //not defined - permit accent on any character
        if(legalCharacters==null) return true;
        
        return legalCharacters.indexOf(forCharacter)!=-1;
    }

    public String toString() {
        return getName();
    }
}
