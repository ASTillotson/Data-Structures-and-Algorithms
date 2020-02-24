package deques.palindrome;

import deques.Deque;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PalindromeTest {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertEquals(true, palindrome.isPalindrome("abba"));
        assertEquals(false, palindrome.isPalindrome("baba"));
        assertEquals(true, palindrome.isPalindrome("a"));
        assertEquals(true, palindrome.isPalindrome("aaaaaaaa"));
        assertEquals(true, palindrome.isPalindrome(""));
        assertEquals(false, palindrome.isPalindrome("abbabbad"));
        assertEquals(false, palindrome.isPalindrome("Aba"));
        assertEquals(true, palindrome.isPalindrome("%%%%%%%"));
    }

    @Test
    public void testCCIsPalindrome() {
        CharacterComparator offByOne = new OffByOne();
        assertEquals(true, palindrome.isPalindrome("flake", offByOne));
        assertEquals(false, palindrome.isPalindrome("abba", offByOne));
        assertEquals(false, palindrome.isPalindrome("%%%%", offByOne));
        assertEquals(false, palindrome.isPalindrome("ABABAbA", offByOne));
        assertEquals(false, palindrome.isPalindrome("AAAAAAA", offByOne));
    }
}
