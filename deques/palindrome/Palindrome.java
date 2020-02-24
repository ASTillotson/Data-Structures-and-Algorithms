package deques.palindrome;

import deques.Deque;
import deques.LinkedDeque;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedDeque<Character> deque = new LinkedDeque<>();
        for (int i = word.length() - 1; i >= 0; i--) {
            deque.addFirst(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
