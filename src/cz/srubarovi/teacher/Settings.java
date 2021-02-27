/*
 * Settings.java
 *
 * Created on 17. zברם 2007, 16:43
 */

package cz.srubarovi.teacher;

import cz.srubarovi.teacher.core.StatsManager;
import cz.srubarovi.teacher.questioner.*;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Stepan
 */
public class Settings {
    public static final Settings INSTANCE=new Settings();
    private static final ResourceBundle I18N=ResourceBundle.getBundle("cz/srubarovi/teacher/properties");
    
    public static String i18nText(String key) {
        return I18N.getString(key);
    }
    
    public enum SuggestMode {
        SINGLE,
        APPENDING
//        SEVERAL
    }
    
    
    public static final int TIMER_DISABLED=-1;
    private Settings() {}
    
    private Questioner defaultQuestioner=new EnglishCzech();
    private int timer=TIMER_DISABLED;
    private int timerBonusPerLetter=200;
    private SuggestMode suggestMode=SuggestMode.APPENDING;
    private File dictDir=null;
    private File statsDir=null;
    private String statsProfile=null;
    
    private boolean autoCopyToClipboard=true;
    
    private boolean RTLQuestion=false;
    private boolean RTLAnswer=false;
    
    
    private Font currentFont=new Font("Tahoma", 0, 36);
    
//    private String accentChars=";";
//
//    public String getAccentChars() {
//        return accentChars;
//    }
//
//    public void setAccentChars(String accentChars) {
//        this.accentChars = accentChars;
//    }
    
//    public boolean isAccentChar(char c) {
//        return accentChars.indexOf(c)!=(-1);
//    }
    
    public Collection<Questioner> getQuestioners() {
        return Arrays.asList(new Questioner[] {new EnglishCzech(), new RussianCzech(), new FrenchCzech(), new GermanCzech(), new SpanishEnglish()});
    }
    
    public Questioner getDefaultQuestioner() {
        return defaultQuestioner;
    }
    
    public void setDefaultQuestioner(Questioner defaultQuestioner) {
        this.defaultQuestioner = defaultQuestioner;
    }
    
    public Font getFont() {
        return currentFont;
    }
    
    public void setFont(Font currentFont) {
        this.currentFont = currentFont;
    }
    
    public int getTimer() {
        return timer;
    }
    
    public void setTimer(int timer) {
        this.timer = timer;
    }
    
    public boolean isTimerEnabled() {
        return timer!=TIMER_DISABLED;
    }
    
    public int getTimerBonusPerLetter() {
        return timerBonusPerLetter;
    }
    
    public void setTimerBonusPerLetter(int timerBonusPerLetter) {
        this.timerBonusPerLetter = timerBonusPerLetter;
    }
    
    public File getDictDir() {
        return dictDir;
    }
    
    public void setDictDir(File dictDir) {
        if(!dictDir.isDirectory()) {
            throw new IllegalArgumentException("dictDir has to be a directory.");
        }
        
        this.dictDir = dictDir;
    }
    
    public File getStatsDir() {
        return statsDir;
    }
    
    public void setStatsDir(File statsDir) {
        if(!statsDir.isDirectory()) {
            throw new IllegalArgumentException("statsDir has to be a directory.");
        }
        try {
            
            this.statsDir = statsDir.getCanonicalFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public Settings.SuggestMode getSuggestMode() {
        return suggestMode;
    }
    
    public void setSuggestMode(Settings.SuggestMode suggestMode) {
        if(suggestMode!=null)
            this.suggestMode = suggestMode;
    }
    
    public boolean isRTLQuestion() {
        return RTLQuestion;
    }
    
    public void setRTLQuestion(boolean RTLQuestion) {
        this.RTLQuestion = RTLQuestion;
    }
    
    public boolean isRTLAnswer() {
        return RTLAnswer;
    }
    
    public void setRTLAnswer(boolean RTLAnswer) {
        this.RTLAnswer = RTLAnswer;
    }
    
    public File getProfileStatsDir() {
        return new File(getStatsDir(), getStatsProfile());
    }
    
    public String getStatsProfile() {
        return statsProfile;
    }
    
    public void setStatsProfile(String statsProfile) {
        if(statsProfile.equals(this.statsProfile)) 
            return;
        
        File profileDir=new File(getStatsDir(), statsProfile);
        
        if(!profileDir.exists())
            profileDir.mkdir();
        
        this.statsProfile = statsProfile;
        
        StatsManager.INSTANCE.clearCache();
    }

    public boolean getAutoCopyToClipboard() {
        return autoCopyToClipboard;
    }

    public void setAutoCopyToClipboard(boolean autoCopyToClipboard) {
        this.autoCopyToClipboard = autoCopyToClipboard;
    }
}
