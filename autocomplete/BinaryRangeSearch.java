package autocomplete;

import java.util.Arrays;

public class BinaryRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Term[] terms) {
        if (terms == null || terms[0] == null) {
            throw new IllegalArgumentException();
        }
        Arrays.sort(terms);
        this.terms = terms;
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        int searchLeftBound = binaryRangeSearchLower(prefix);
        int searchRightBound = binaryRangeSearchUpper(prefix);
        Term[] ans = Arrays.copyOfRange(terms, searchLeftBound, searchRightBound);
        Arrays.sort(ans, TermComparators.byReverseWeightOrder());
        return ans;
    }

    private int binaryRangeSearchLower(String prefix) {
        int n = terms.length;
        int leftBound = 0;
        int rightBound = n - 1;
        int mid = 0;
        while (leftBound <= rightBound) {
            mid = (int) Math.floor((leftBound + rightBound) / 2);
            if (terms[mid].query().compareTo(prefix) < 0) {
                leftBound = mid + 1;
            } else if (mid > 0 && terms[mid].query().startsWith(prefix) && !terms[mid - 1].query().startsWith(prefix)) {
                return mid;
            } else if (terms[mid].query().compareTo(prefix) >= 0) {
                rightBound = mid - 1;
            }
        }
        return mid;
    }

    private int binaryRangeSearchUpper(String prefix) {
        int n = terms.length;
        int leftBound = 0;
        int rightBound = n - 1;
        int mid = 0;
        while (leftBound <= rightBound) {
            mid = (int) Math.floor((leftBound + rightBound) / 2);
            if (terms[mid].query().compareTo(prefix) < 0 || terms[mid].query().startsWith(prefix)) {
                leftBound = mid + 1;
                mid = leftBound;
            } else if (mid < this.terms.length && terms[mid].query().startsWith(prefix)
                    && !terms[mid + 1].query().startsWith(prefix)) {
                return mid;
            } else if (terms[mid].query().compareTo(prefix) > 0) {
                rightBound = mid - 1;
            }
        }
        return mid;
    }
}
