/*
 * AbstractConverter.java
 *
 * Created on 27. leden 2007, 16:09
 */

package cz.srubarovi.teacher.converters;

import java.util.Map;

/**
 *
 * @author Stepan
 */
abstract class AbstractConverter implements Converter {

    static Converter[] CONVERTERS_ARRAY=new Converter[] {
        new ConverterEsperanto(),
        new ConverterKatakana()
    };
    
    
    private Map<String, String> replacement;

    protected abstract Map<String, String> getMap();
    
    protected AbstractConverter() {
        this.replacement=getMap();
    }

    public String convert(String input) {
        String result=input;
        for (String key : replacement.keySet()) {
            String val=replacement.get(key);
            
            result=result.replace(key, val);
        }
        
        return result;
    }
    
}
