/*
 * ProfileManager.java
 *
 * Created on 24. listopad 2007, 19:30
 */

package cz.srubarovi.teacher;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 *
 * @author Stepan
 */
public class ProfileManager {
    
    private ProfileManager() {}
    
    public static int getProfileCount() {
        return listProfiles().length;
    }
    
    public static String[] listProfiles() {
        File dir=Settings.INSTANCE.getStatsDir();
        
        if(dir==null) return new String[0];
        
        File[] files=dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory() && !pathname.isHidden();
            }
        });
        
        String[] names=new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i]=files[i].getName();
        }
        
        return names;
    }
    
    public static void createProfile(String statsProfile) {
        File profileDir=new File(Settings.INSTANCE.getStatsDir(), statsProfile);
        
        if(!profileDir.exists())
            profileDir.mkdir();        
    }
    
    public static String getTestPathname(File lastfile) {
        try {
            String testname = lastfile.getCanonicalPath();
            String testdir=Settings.INSTANCE.getDictDir().getCanonicalPath();
            
            assert testname.startsWith(testdir);
            
            return testname.substring(testdir.length()+1);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
