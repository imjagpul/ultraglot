/*
 * RussianCzech.java
 *
 * Created on 11. zברם 2007, 17:06
 */

package cz.srubarovi.teacher.questioner;

import cz.srubarovi.teacher.core.Question;
import java.awt.Frame;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Stepan
 */
public class RussianCzech extends Questioner {
    
    /** Creates a new instance of RussianCzech */
    public RussianCzech() {
        super("rucz.txt");
    }
    // http://slovnik.seznam.cz/search.py?wd={0}&lg=ru_cz
    protected String DICT="http://slovnik.seznam.cz/search.py?lg=ru_cz&wd=";
    protected String DICT_REVERSE="http://slovnik.seznam.cz/search.py?lg=cz_ru&wd=";
    
    // http://dic.gramota.ru/search.php?lop=x&efr=x&word=
    
    //regexes
    private static final String HEADER=".+?<div\\ id=\\\"results\\\">.+<table\\ id=\\\"words\\\">";
    private static final String ONE_WORD="<a\\ href=\\\"[^\"]*?\\\"[^>]*?>\\s*([^<]+?)\\s*</a>";
    private static final String LI="(?:[^a]*?<(?:tr|dt)>.*?"+ONE_WORD+")?.*?"+ONE_WORD;
    private static final String FOOT="</table>.*";
    private static final String PATTERN=HEADER+"(.+?)"+FOOT;
//    private static final String PATTERN=HEADER+"(?:.*?"+LI+".*?)+"+FOOT;
    private static final String PATTERN_SUB=LI;
    private static final Pattern SEZNAM_REGEX=Pattern.compile(PATTERN);
    private static final Pattern SEZNAM_REGEX_WORD=Pattern.compile(PATTERN_SUB);
    protected List<String> parseSeznam(String page, String... expectedMatches) {
        Matcher m=SEZNAM_REGEX.matcher(page);
        ArrayList<String> translations=null;
        
        if(m.matches()) {
            MatchResult result=m.toMatchResult();
            translations=new ArrayList<String>();
            
            String sub=result.group(1);
            
            m=SEZNAM_REGEX_WORD.matcher(sub);
            
            String foreign=null;
            while(m.find()) {
                String grouptitle=m.group(1);                
                if(grouptitle!=null)
                    foreign=grouptitle;
                
                String translation=m.group(2);
                
                if(foreign==null || expectedMatches.length==0) translations.add(translation);
                else {
                    for (int i = 0; i < expectedMatches.length; i++) {
                        if(foreign.equals(expectedMatches[i])) {
                            translations.add(translation);
                            break;
                        }
                    }
                }
//                } else { //if() //TODO: settings, include volutarily
//                    translations.add(translation);
//            }
            }
        }
        return translations;
        
        //result.
        //header
        //  .+?<div\ id=\"results\">.+<ul\ id=\"list\">
        
        //one listitem
        //  <li>.*?<a\ href=\"[^"]*?\"[^>]*?>([^<]+?)</a>.*?<a\ href=\"[^"]*?\"[^>]*?>([^<]+?)</a>.*?</li>
        
        //foot
        //  </ul>.*
        
        //one word
        //  <a\ href=\"[^"]*?\"[^>]*?>([^<]+?)</a>
        
        
    }
    
    @Override
    public void edit(Question question, Frame parentComponent) {
        
        List<String> translations;
        String q=question.getAnswer();
        boolean reverse=(q==null || q.isEmpty());

        if(reverse){
            q=question.getQuestion();
        }
        
        translations=checkDictionary(q, true);        
        
        try {
            String url= (reverse ? DICT_REVERSE : DICT) +URLEncoder.encode(q, "UTF8");

            String content=readUrl(url);
            if(content!=null) {
                List<String> seznam=parseSeznam(content, q);
                if(seznam!=null) {
                    if(translations==null)
                        translations=seznam;
                    else {
                        translations.addAll(seznam);
                    }
                }
            }
            
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        
        showChoiceBox(translations, question, parentComponent, reverse);
    }
    
    @Override
    public String getSrcLangCode() {
        return LANGCODE_CZ;
    }
    
    @Override
    public String getForeignLangCode() {
        return LANGCODE_RU;
    }
    
    
}
