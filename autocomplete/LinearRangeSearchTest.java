package autocomplete;

import edu.princeton.cs.algs4.In;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class LinearRangeSearchTest {

    private static Autocomplete linearAuto;

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
                new Term("me", 0),
                new Term(null, 0)
        };
        LinearRangeSearch lrs = new LinearRangeSearch(moreTerms);
        Term[] expected = new Term[]{new Term("autocomplete", 0)};
        assertTermsEqual(expected, lrs.allMatches("auto"));
    }
    @Test
    public void myTest() {
        Term[] moreTerms = new Term[] {
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
                new Term("job", 0)
        };
        LinearRangeSearch lrs = new LinearRangeSearch(moreTerms);
        Arrays.sort(moreTerms);
        Term[] testTerms1 = new Term[]{
                new Term("chief", 0),
                new Term("chili", 0),
                new Term("china", 0),
                new Term("chirp", 0)
        };
        assertTermsEqual(testTerms1, lrs.allMatches("chi"));
    }

    // Write more unit tests below.
}
