/*
 * Converter.java
 *
 * Created on 27. leden 2007, 16:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package cz.srubarovi.teacher.converters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static cz.srubarovi.teacher.converters.AbstractConverter.CONVERTERS_ARRAY;
/**
 *
 * @author Stepan
 */
public interface Converter {
    String convert(String input);
    String getName();
        
    static Collection<Converter> CONVERTERS=
            Collections.unmodifiableList(Arrays.asList(CONVERTERS_ARRAY));
}
