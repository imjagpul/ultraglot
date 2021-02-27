/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.srubarovi.teacher;

import java.util.ArrayList;
import cz.srubarovi.teacher.core.Answer;
import java.util.List;

/**
 *
 */
public class DiffMarker {

    // These are "constants" which indicate a direction in the backtracking array.
    private static final int NEITHER = 0;
    private static final int UP = 1;
    private static final int LEFT = 2;
    private static final int UP_AND_LEFT = 3;

    /**
     * This is an implementation, in Java, of the Longest Common Subsequence 
     * algorithm.
     * That is, given two strings A and B, this program will find the longest 
     * sequence of letters that are common and ordered in A and B.
     *
     * There are only two reasons you are reading this:
     *   - you don't care what the algorithm is but you need a piece of code to do it
     *   - you're trying to understand the algorithm, and a piece of code might help
     * In either case, you should either read an entire chapter of an algorithms 
     * textbook on the subject of dynamic programming, or you should consult 
     * a webpage that describes this particular algorithm. It is important, 
     * for example, that we use arrays of size
     * |A|+1 x |B|+1. 
     *
     * This code is provided AS-IS.
     * You may use this code in any way you see fit, EXCEPT as the answer to a homework
     * problem or as part of a term project in which you were expected to arrive at this
     * code yourself.  
     * 
     * Copyright (C) 2005 Neil Jones.
     *
     */
    static String LCSAlgorithmOld(String a, String b) {
        int n = a.length();
        int m = b.length();
        int S[][] = new int[n + 1][m + 1];
        int R[][] = new int[n + 1][m + 1];
        int ii, jj;

        // It is important to use <=, not <.  The next two for-loops are initialization
        for (ii = 0; ii <= n; ++ii) {
            S[ii][0] = 0;
            R[ii][0] = UP;
        }
        for (jj = 0; jj <= m; ++jj) {
            S[0][jj] = 0;
            R[0][jj] = LEFT;
        }

        // This is the main dynamic programming loop that computes the score and
        // backtracking arrays.
        for (ii = 1; ii <= n; ++ii) {
            for (jj = 1; jj <= m; ++jj) {

                if (a.charAt(ii - 1) == b.charAt(jj - 1)) {
                    S[ii][jj] = S[ii - 1][jj - 1] + 1;
                    R[ii][jj] = UP_AND_LEFT;
                } else {
                    S[ii][jj] = S[ii - 1][jj - 1] + 0;
                    R[ii][jj] = NEITHER;
                }

                if (S[ii - 1][jj] >= S[ii][jj]) {
                    S[ii][jj] = S[ii - 1][jj];
                    R[ii][jj] = UP;
                }

                if (S[ii][jj - 1] >= S[ii][jj]) {
                    S[ii][jj] = S[ii][jj - 1];
                    R[ii][jj] = LEFT;
                }
            }
        }

        // The length of the longest substring is S[n][m]
        ii = n;
        jj = m;
        int pos = S[ii][jj] - 1;
        char lcs[] = new char[pos + 1];

        // Trace the backtracking matrix.
        while (ii > 0 || jj > 0) {
            if (R[ii][jj] == UP_AND_LEFT) {
                ii--;
                jj--;
                lcs[pos--] = a.charAt(ii);
            } else if (R[ii][jj] == UP) {
                ii--;
            } else if (R[ii][jj] == LEFT) {
                jj--;
            }
        }

        return new String(lcs);
    }

    static void markDiff(ArrayList<Integer> diffMissing,
            ArrayList<Integer> diffWrong,
            ArrayList<Integer> diffRedundant,
            String answer,
            List<Answer> expected) {

        //convert list to array
        String[] expectedArray = new String[expected.size()*2];
        for (int i = 0; i < expected.size(); i++) {
            expectedArray[i] = expected.get(i).getAnswer();
            expectedArray[expected.size()+i] = expected.get(i).getAnswerRaw();
        }

        markDiff(diffMissing, diffWrong, diffRedundant, answer, expectedArray);
    }

    static String getMostSimilar(String expected, String... values) {
        if (values.length == 1) {
            return values[0];
        }
        double maxPercentage = 0;
        int maxIndex = -1;
        for (int i = 0; i < values.length; i++) {
            String string = values[i];
            Lcs lcs = Lcs.compute(string, expected);

            double p = lcs.getPercentage();
            if (maxIndex == -1 || maxPercentage < p) {
                maxPercentage = p;
                maxIndex = i;
            }
        }

        return values[maxIndex];
    }

    static void markDiff(ArrayList<Integer> diffMissing,
            ArrayList<Integer> diffWrong,
            ArrayList<Integer> diffRedundant,
            String answer,
            String... expected) {

        assert expected.length > 0;

        String expectedOne = getMostSimilar(answer, expected);
        String diff = LCSAlgorithm(answer, expectedOne);

        int expectedIndex = 0;
        int diffIndex = 0;
        int answerIndex = 0;

        char EOL = 0;

        diffMissing.clear();
        diffWrong.clear();
        diffRedundant.clear();

        while (true) {
            char e = expectedIndex < expectedOne.length() ? expectedOne.charAt(expectedIndex) : EOL;
            char d = diffIndex < diff.length() ? diff.charAt(diffIndex) : EOL;
            char a = answerIndex < answer.length() ? answer.charAt(answerIndex) : EOL;


            if (d == e) {
                if (d == a) { //all three matching, move on

                    if (d == EOL) {
                        break; //all three finished

                    }
                    expectedIndex++;
                    diffIndex++;
                    answerIndex++;
                } else { // d==e && d!=a
                    //exp & diff matches, answer different

                    diffRedundant.add(answerIndex);
                    answerIndex++;
                }
            } else { // d!=e

                if (d == a) {
                    Integer aI = answerIndex;
                    if (!diffMissing.contains(aI)) {
                        diffMissing.add(aI);
                    }
                    expectedIndex++;

                } else { // e!= d && a != d

                    diffWrong.add(answerIndex);

                    answerIndex++;
                    expectedIndex++;
                }
            }
        }

    }

    static String LCSAlgorithm(String a, String b) {
        Lcs lcs = Lcs.compute(a, b);
        return lcs.getString();
    }
}

/**
 * Implementation of the <em>Longest Common Subsequence</em> algorithm.
 *
 * @author Caitlin Howell
 * @version 1.0
 *
 * @see <a
 *      href="http://www.ics.uci.edu/~eppstein/161/960229.html">http://www.ics.uci.edu/~eppstein/161/960229.html</a>
 * @see <a
 *      href="http://hissa.nist.gov/dads/HTML/longestcommn.html">http://hissa.nist.gov/dads/HTML/longestcommn.html</a>
 */
final class Lcs {
    //~ Static variables/initializers ----------------------------------------------------

    private static final String EMPTY_STRING = "" /* NOI18N */;

    //~ Instance variables ---------------------------------------------------------------
    /** Lcs stores the longest common subsequence. */
    private String _lcs = EMPTY_STRING;
    /** s1 and s2 are the two strings that we are finding the Lcs of. */
    private String _s1;
    /** s1 and s2 are the two strings that we are finding the Lcs of. */
    private String _s2;
    /** The table we will use to determine the Lcs. */
    private Cell[][] _table;
    /** Did we compute a Lcs already? */
    private boolean _done;
    /** Is the processing table initialized yet? */
    private boolean _init;

    /* The number of columns in the table. */
    private int _columns;
    /** The number of rows  in the table. */
    private int _rows;

    //~ Constructors ---------------------------------------------------------------------
    /**
     * Creates a new Lcs object.
     */
    public Lcs() {
    }

    /**
     * Creates a new Lcs object for the two given strings.
     *
     * @param string1 first string.
     * @param string2 second string.
     */
    public Lcs(
            String string1,
            String string2) {
        _s1 = string1;
        _s2 = string2;
        init();
    }

    //~ Methods --------------------------------------------------------------------------
    /**
     * Computes the longest common subsequence for the two given strings.
     *
     * @param s1 first string.
     * @param s2 second string.
     *
     * @return Lcs object with the computed information. Use the accessor methods to get
     *         the piece of information you need.
     *
     * @see #getString
     * @see #getLength
     * @see #getPercentage
     */
    public static Lcs compute(
            String s1,
            String s2) {
        Lcs lcs = new Lcs(s1, s2);

        return lcs;
    }

    /**
     * Returns the longest common subsequence length.
     *
     * @return the longest common subsequence length.
     */
    public int getLength() {
        if (!_done) {
            compute();
        }

        return _lcs.length();

    // return _table[_rows - 1][_columns - 1].total();
    }

    /**
     * Returns the similarity of the strings in percent.
     *
     * @return the similarity of the strings in percent.
     */
    public double getPercentage() {
        return (getLength() * 200) / (_s1.length() + _s2.length());
    }

    /**
     * Returns the longest common subsequence.
     *
     * @return the longest common subsequence.
     */
    public String getString() {
        if (!_done) {
            compute();
        }

        return _lcs;
    }

    /**
     * Computes the longest common subsequence for the two given strings.
     *
     * @see #init(String, String)
     * @see #getString
     * @see #getLength
     * @see #getPercentage
     */
    public void compute() {
        if (!_init) {
            init();
        }

        _lcs = EMPTY_STRING;
        recursiveLcs(_s2, _rows - 1, _columns - 1);

        // release the processing table
        _table = null;
        _init = false;
        _done = true;
    }

    /**
     * Sets the two strings to compute the longest common subsequence for  and
     * initializes the processing table.
     *
     * @param s1 first string.
     * @param s2 second string.
     */
    public void init(
            String s1,
            String s2) {
        _s1 = s1;
        _s2 = s2;
        init();
    }

    /**
     * Initialization of the processing table.
     *
     * @throws IllegalStateException if either one of the strings is <code>null</code>.
     */
    private void init() {
        if ((_s1 == null) || (_s2 == null)) {
            throw new IllegalStateException(
                    "both strings must be specified and non-null");
        }

        int temp1 = 0;
        int temp2 = 0;
        _columns = _s1.length() + 1;
        _rows = _s2.length() + 1;
        _table = new Cell[_rows][];

        for (int i = 0; i < _rows; i++) {
            _table[i] = new Cell[_columns];

            for (int j = 0; j < _columns; j++) {
                _table[i][j] = new Cell(0, Cell.UNDEFINED);
            }
        }

        for (int l = 1; l < _rows; l++) {
            _table[l][0].changeTotal(0);
            _table[l][0].changeArrow(Cell.UNDEFINED);
        }

        for (int k = 0; k < _columns; k++) {
            _table[0][k].changeTotal(0);
            _table[0][k].changeArrow(Cell.UNDEFINED);
        }

        for (int i = 1; i < _rows; i++) {
            for (int j = 1; j < _columns; j++) {
                if (_s1.charAt(j - 1) == _s2.charAt(i - 1)) {
                    temp1 = _table[i - 1][j - 1].total();
                    _table[i][j].changeTotal(temp1 + 1);
                    _table[i][j].changeArrow(Cell.DIRECTIONAL);
                } else {
                    temp1 = _table[i - 1][j].total();
                    temp2 = _table[i][j - 1].total();

                    if (temp1 >= temp2) {
                        _table[i][j].changeTotal(temp1);
                        _table[i][j].changeArrow(Cell.UP);
                    } else {
                        _table[i][j].changeTotal(temp2);
                        _table[i][j].changeArrow(Cell.LEFT);
                    }
                }
            }
        }

        _init = true;
        _done = false;
    }

    private String recursiveLcs(
            String x,
            int i,
            int j) {
        if ((i == 0) || (j == 0)) {
            return EMPTY_STRING;
        }

        if (_table[i][j] != null) {
            if (_table[i][j].arrow() == Cell.DIRECTIONAL) {
                recursiveLcs(x, i - 1, j - 1);

                String temp = String.valueOf(x.charAt(i - 1));
                _lcs = _lcs.concat(temp);
            } else if (_table[i][j].arrow() == Cell.UP) {
                recursiveLcs(x, i - 1, j);
            } else {
                recursiveLcs(x, i, j - 1);
            }
        }

        return EMPTY_STRING;
    }

    //~ Inner Classes --------------------------------------------------------------------
    /**
     * Cell makes up each individual cell in the Lcs table.  Each cell stores a
     * directional arrow and the total length of the longest common string.
     */
    private static final class Cell {

        /** Represents the directional value. */
        public static final char DIRECTIONAL = '\\';
        /** Represents the left value. */
        public static final char LEFT = '<';
        /** Represents an undefined value. */
        public static final char UNDEFINED = '+';
        /** Represents the up value. */
        public static final char UP = '^';
        /**
         * Because Java contains no enumerated types, and it seems like a waste to
         * declare a whole new class to represent a 2-bit value, char is used to
         * represent the directional value.  '\' ('\\' in Java) is diagonal  '^' is up
         * '&lt;' is left  '+' is undefined
         */
        private char _arrow;
        /**
         * total stores the total length of the longest common string  that the cell
         * describes.
         */
        private int _total;

        public Cell(
                int total,
                char arrow) {
            _total = total;
            _arrow = arrow;
        }

        public char arrow() {
            return _arrow;
        }

        public void changeArrow(char arrow) {
            _arrow = arrow;
        }

        public void changeTotal(int total) {
            _total = total;
        }

        public int total() {
            return _total;
        }
    }
}

