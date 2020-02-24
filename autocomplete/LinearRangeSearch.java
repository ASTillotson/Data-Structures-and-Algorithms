package autocomplete;


import java.util.Arrays;

public class LinearRangeSearch implements Autocomplete {
    private Term[] terms;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Term[] terms) {
        if (terms == null || terms[0] == null) {
            throw new IllegalArgumentException();
        }
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
        int count = 0;
        Term[] ans = new Term[terms.length];
        int ansIterator = 0;
        for (int i = 0; i < this.terms.length; i++) {
            String debugHelper = terms[i].queryPrefix(prefix.length());
            if (terms[i].query().startsWith(prefix)) {
                ans[ansIterator] = terms[i];
                count++;
                ansIterator++;
            }
        }
        ans = Arrays.copyOf(ans, count);
        Arrays.sort(ans, TermComparators.byReverseWeightOrder());
        return ans;
    }
}

