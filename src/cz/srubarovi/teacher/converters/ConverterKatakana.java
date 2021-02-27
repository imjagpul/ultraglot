/*
 * ConverterKatakana.java
 *
 * Created on 16. duben 2007, 21:51
 */

package cz.srubarovi.teacher.converters;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stepan
 */
public class ConverterKatakana extends AbstractConverter {
    private static final Map<String, String> map;
    static {
        map=new HashMap<String, String>();
        map.put("a", "\u30A2");
        map.put("i", "\u30A4");
        map.put("u", "\u30A6");
        map.put("e", "\u30A8");
        map.put("o", "\u30AA");
        map.put("ka", "\u30AB");
        map.put("ga", "\u30AC");
        map.put("ki", "\u30AD");
        map.put("gi", "\u30AE");
        map.put("ku", "\u30AF");
        map.put("gu", "\u30B0");
        map.put("ke", "\u30B1");
        map.put("ge", "\u30B2");
        map.put("ko", "\u30B3");
        map.put("go", "\u30B4");
        map.put("sa", "\u30B5");
        map.put("za", "\u30B6");
        map.put("si", "\u30B7");
        map.put("zi", "\u30B8");
        map.put("su", "\u30B9");
        map.put("zu", "\u30BA");
        map.put("se", "\u30BB");
        map.put("ze", "\u30BC");
        map.put("so", "\u30BD");
        map.put("zo", "\u30BE");
        map.put("ta", "\u30BF");
        map.put("da", "\u30C0");
        map.put("ti", "\u30C1");
        map.put("di", "\u30C2");
        map.put("tu", "\u30C4");
        map.put("du", "\u30C5");
        map.put("te", "\u30C6");
        map.put("de", "\u30C7");
        map.put("to", "\u30C8");
        map.put("do", "\u30C9");
        map.put("na", "\u30CA");
        map.put("ni", "\u30CB");
        map.put("nu", "\u30CC");
        map.put("ne", "\u30CD");
        map.put("no", "\u30CE");
        map.put("ha", "\u30CF");
        map.put("ba", "\u30D0");
        map.put("pa", "\u30D1");
        map.put("hi", "\u30D2");
        map.put("bi", "\u30D3");
        map.put("pi", "\u30D4");
        map.put("hu", "\u30D5");
        map.put("bu", "\u30D6");
        map.put("pu", "\u30D7");
        map.put("he", "\u30D8");
        map.put("be", "\u30D9");
        map.put("pe", "\u30DA");
        map.put("ho", "\u30DB");
        map.put("bo", "\u30DC");
        map.put("po", "\u30DD");
        map.put("ma", "\u30DE");
        map.put("mi", "\u30DF");
        map.put("mu", "\u30E0");
        map.put("me", "\u30E1");
        map.put("mo", "\u30E2");
        map.put("ya", "\u30E4");
        map.put("yu", "\u30E6");
        map.put("yo", "\u30E8");
        map.put("ra", "\u30E9");
        map.put("ri", "\u30EA");
        map.put("ru", "\u30EB");
        map.put("re", "\u30EC");
        map.put("ro", "\u30ED");
        map.put("wa", "\u30EF");
        map.put("wi", "\u30F0");
        map.put("we", "\u30F1");
        map.put("wo", "\u30F2");
        map.put("n", "\u30F3");
        map.put("vu", "\u30F4");
        map.put("va", "\u30F7");
        map.put("vi", "\u30F8");
        map.put("ve", "\u30F9");
        map.put("vo", "\u30FA");
    }
    protected Map<String, String> getMap() {
        return map;
    }
    
    public String getName() {
        return "Katakana ";
    }
}