/*
 * LangDefManager.java
 *
 * Created on 10. zברם 2007, 20:22
 */

package cz.srubarovi.teacher.core;

import cz.srubarovi.teacher.xml.Language;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Stepan
 */
public class LangDefManager {
    
    private static final Map<String, LanguageDefinition> map=new HashMap<String, LanguageDefinition>();
    
    private static final FileFilter DIRECTORY_ONLY=new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory() && !pathname.isHidden() && !pathname.getName().startsWith(".");
        }
    };
    
    private boolean showWordTypes=false;
    
    private LangDefManager() {}
    public static final LangDefManager INSTANCE=new LangDefManager();
    
    public LanguageDefinition getLang(String langcode) {
        return map.get(langcode);
    }
    
    public Collection<LanguageDefinition> getAllLoaded() {
        return map.values();
    }
    
    public void addDir(File startFolder) {
        for(File dir : startFolder.listFiles(DIRECTORY_ONLY)) {
            addDir(dir);
        }

        File[] langdefs=startFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".langdef.xml");
            }
        });
        
        try {
            Unmarshaller unmarsh=XmlDataTest.CONTEXT.createUnmarshaller();
            for(File langdef : langdefs) {
                cz.srubarovi.teacher.xml.Language def=(Language) unmarsh.unmarshal(langdef);
                cz.srubarovi.teacher.core.LanguageDefinition d=new LanguageDefinition(def);
                
                if(d.getWordTypes()!=null) {
                    showWordTypes=true;
                }
                
                map.put(d.getLangcode(), d);
                
                //TODO : handle different languageDefs with same langcode
            }
        } catch(JAXBException exc) {
            exc.printStackTrace();
        }
    
    }
    
    public boolean showWordTypes() {
        return showWordTypes;
    }
}
