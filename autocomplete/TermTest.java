package autocomplete;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TermTest {
    @Test
    public void testSimpleCompareTo() {
        Term a = new Term("autocomplete", 0);
        Term b = new Term("me", 0);
        assertTrue(a.compareTo(b) < 0); // "autocomplete" < "me"
        Term test1 = new Term("aaaaa", 100);
        Term test2 = new Term("a", 10);
        assertTrue(test1.compareTo(test2) == 4);
        Term test7 = new Term("aa", 0);
        Term test8 = new Term("az", 0);
        assertTrue(test7.compareTo(test8) == -25);
        Term test9 = new Term("same", 0);
        Term test10 = new Term("same", 0);
        assertTrue(test9.compareTo(test10) == 0);
        assertTrue(test1.compareToByReverseWeightOrder(test2) == 100);
        Term test3 = new Term("disbelief", 0);
        Term test4 = new Term("disengage", 0);
        assertTrue(test3.compareToByPrefixOrder(test4, 4) == -3);
        Term test5 = new Term("higherWeight", 20);
        Term test6 = new Term("lowerWeight", 10);
        assertTrue(test5.compareTo(test6) == -4);
        assertTrue(test5.compareToByReverseWeightOrder(test6) == 20);
    }

    // Write more unit tests below.
}
