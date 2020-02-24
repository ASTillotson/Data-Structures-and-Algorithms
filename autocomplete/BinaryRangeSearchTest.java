package autocomplete;

import edu.princeton.cs.algs4.In;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class BinaryRangeSearchTest {

    private static Autocomplete linearAuto;
    private static Autocomplete binaryAuto;

    private static int N = 0;
    private static Term[] terms = null;

    private static final String INPUT_FILENAME = "data/cities.txt";

    /**
     * Creates LinearRangeSearch and BinaryRangeSearch instances based on the data from cities.txt
     * so that they can easily be used in tests.
     */
    @Before
    public void setUp() {
        if (terms != null) {
            return;
        }

        In in = new In(INPUT_FILENAME);
        N = in.readInt();
        terms = new Term[N];
        for (int i = 0; i < N; i += 1) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query, weight);
        }

        linearAuto = new LinearRangeSearch(terms);
        binaryAuto = new BinaryRangeSearch(terms);
    }

    /**
     * Checks that the terms in the expected array are equivalent to the ones in the actual array.
     */
    private void assertTermsEqual(Term[] expected, Term[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Term e = expected[i];
            Term a = actual[i];
            assertEquals(e.query(), a.query());
            assertEquals(e.weight(), a.weight());
        }
    }

    @Test
    public void testSimpleExample() {
        Term[] moreTerms = new Term[] {
            new Term("hello", 0),
            new Term("world", 0),
            new Term("welcome", 0),
            new Term("to", 0),
            new Term("autocomplete", 0),
                new Term("me", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] expected = new Term[]{new Term("autocomplete", 0)};
        assertTermsEqual(expected, brs.allMatches("auto"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullQueryTest() {
        Term[] moreTerms = new Term[]{
                new Term(null, 0)
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPrefixTest() {
        Term[] moreTerms = new Term[]{
                new Term("hello", 0),
                new Term("world", 0),
                new Term("welcome", 0),
                new Term("to", 0),
                new Term("autocomplete", 0),
                new Term("me", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        brs.allMatches(null);
    }

    @Test
    public void myTest() {
        Term[] moreTerms = new Term[]{
                new Term("aaaaa", 0),
                new Term("aaaa", 0),
                new Term("alice", 0),
                new Term("zack", 0),
                new Term("alpine", 0),
                new Term("alpto", 0),
                new Term("aloof", 0),
                new Term("apple", 0),
                new Term("cab", 0),
                new Term("call", 0),
                new Term("car", 0),
                new Term("caffeine", 0),
                new Term("cha", 0),
                new Term("chief", 0),
                new Term("chili", 0),
                new Term("china", 0),
                new Term("chirp", 0),
                new Term("dean", 0),
                new Term("dog", 0),
                new Term("home", 0),
                new Term("life", 0),
                new Term("job", 0),
                new Term("zendcheck", 0)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        LinearRangeSearch expected = new LinearRangeSearch(moreTerms);
        assertTermsEqual(brs.allMatches(""), expected.allMatches(""));
        Term[] tempNullTest = brs.allMatches("");
        assertTermsEqual(brs.allMatches("z"), expected.allMatches("z"));
        Term[] temp1 = brs.allMatches("z");
        System.out.println(temp1.length);
        assertTermsEqual(brs.allMatches("chi"), expected.allMatches("chi"));
        Term[] temp2 = brs.allMatches("chi");
        System.out.println(temp2.length);
        assertTermsEqual(brs.allMatches("ch"), expected.allMatches("ch"));
        Term[] temp3 = brs.allMatches("ch");
        System.out.println(temp3.length);
        assertTermsEqual(brs.allMatches("aaa"), expected.allMatches("aaa"));
        Term[] temp4 = brs.allMatches("aaa");
        System.out.println(temp4.length);
        assertTermsEqual(brs.allMatches("a"), expected.allMatches("a"));
        Term[] temp5 = brs.allMatches("a");
        System.out.println(temp5.length);
        assertTermsEqual(brs.allMatches("al"), expected.allMatches("al"));
        Term[] temp6 = brs.allMatches("al");
        System.out.println(temp6.length);
    }

    @Test
    public void weightTest() {
        Term[] moreTerms = new Term[]{
                new Term("z", 26),
                new Term("a", 1),
                new Term("y", 25),
                new Term("b", 2),
                new Term("x", 24),
                new Term("c", 3),
                new Term("w", 23),
                new Term("d", 4),
                new Term("v", 22),
                new Term("e", 5),
                new Term("u", 21),
                new Term("f", 6),
                new Term("t", 20),
                new Term("g", 7),
                new Term("s", 19),
                new Term("h", 8),
                new Term("r", 18),
                new Term("i", 9),
                new Term("q", 17),
                new Term("j", 10),
                new Term("p", 16),
                new Term("k", 11),
                new Term("o", 15),
                new Term("l", 12),
                new Term("n", 14),
                new Term("m", 13),
                new Term("zz", 29),
                new Term("zzz", 28),
                new Term("zzzz", 27)
        };
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        Term[] temp = brs.allMatches("z");
    }
    // Write more unit tests below.
}
