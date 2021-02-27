/*
 * GermanCzech.java
 *
 * Created on 24. zברם 2007, 18:33
 */
package cz.srubarovi.teacher.questioner;

import java.util.List;
import java.util.regex.*;

/**
 *
 * @author Stepan
 */
public class FrenchCzech extends RussianCzech {

    /** Creates a new instance of GermanCzech */
    public FrenchCzech() {
        DICT = "http://slovnik.seznam.cz/search.py?lg=fr_cz&wd=";
    }
//    private static final Pattern MATCH=Pattern.compile("(?:([mf])\\s+)?(.+)|(.+),\\s*([mf])\\s*");
    private static final Pattern MATCH = Pattern.compile("(?:([mf])\\s+)?(.+)");

    @Override
    public String getForeignLangCode() {
        return LANGCODE_FR;
    }

    @Override
    protected List<String> parseSeznam(String page, String... expectedMatches) {
        if (expectedMatches.length != 1) {
            return super.parseSeznam(page, expectedMatches);
        }

        String answer = expectedMatches[0];

        List<String> result = null;
        List<String> queries;

        Matcher m = MATCH.matcher(answer);

        if (m.matches()) {
//            assert m.groupCount()==4;
//            assert m.group(1) == null || m.group(4) == null;
//            assert m.group(2) == null || m.group(3) == null;

//            String gender= m.group(1)!=null ? m.group(1) : m.group(4); //can be null also
//            String wordRaw= m.group(2)!=null ? m.group(2) : m.group(3);

            assert m.groupCount() == 2;
            String gender = m.group(1); //can be null also            

            String wordRaw = m.group(2);
            assert wordRaw != null; //shouldn't be null, since the pattern matched

            if (gender != null) {
                String test = wordRaw + ", " + gender;
                return super.parseSeznam(page, answer, test, wordRaw);
            } else { //gender is null, permit both
                return super.parseSeznam(page, wordRaw,
                        wordRaw + ", m", wordRaw + ", f");
            }
        }

        return super.parseSeznam(page, answer);
    }
}
