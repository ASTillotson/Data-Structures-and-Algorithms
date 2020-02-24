package deques.palindrome;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OffByOneTest {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void checkOffByOneTest() {
        assertEquals(true, offByOne.equalChars('a', 'b'));
        assertEquals(false, offByOne.equalChars('b', 'b'));
        assertEquals(false, offByOne.equalChars('z', 'z'));
        assertEquals(false, offByOne.equalChars('Z', 'x'));
        assertEquals(true, offByOne.equalChars('j', 'k'));
        assertEquals(false, offByOne.equalChars('.', 'h'));
        assertEquals(false, offByOne.equalChars('h', '1'));
        assertEquals(false, offByOne.equalChars('A', 'b'));
        assertEquals(false, offByOne.equalChars('A', 'a'));
        assertEquals(false, offByOne.equalChars('A', '^'));
        assertEquals(false, offByOne.equalChars(',', '.'));
        assertEquals(false, offByOne.equalChars('-', '_'));
    }
}


