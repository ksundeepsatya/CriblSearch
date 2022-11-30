package com.cribl.string.match;

public class BoyerMooreStringSearch extends SubStringBase{
	
	private int[] charTable;

	private int[] offsetTable;
	char[] searchCharArray;
	
	public BoyerMooreStringSearch(String searchString) { 
		super(searchString);

		searchCharArray = searchString.toCharArray();
		charTable = makeCharTable(searchCharArray);
		offsetTable = makeOffsetTable(searchCharArray);
	}
	
	@Override
	protected boolean isSubStringInternal(String text) {
		char[] textCharArray = text.toCharArray();
		char[] searchCharArray = searchString.toCharArray();
        if (searchCharArray.length == 0) {
            return true;
        }
        for (int i = searchCharArray.length - 1, j; i < textCharArray.length;) {
            for (j = searchCharArray.length - 1; searchCharArray[j] == textCharArray[i]; --i, --j) {
                if (j == 0) {
                    return true;
                }
            }
            // i += needle.length - j; // For naive method
            i += Math.max(offsetTable[searchCharArray.length - 1 - j], charTable[textCharArray[i]]);
        }
        return false;
    }

    /**
     * Makes the jump table based on the mismatched character information.
     */
    private static int[] makeCharTable(char[] search) {
        final int ALPHABET_SIZE = Character.MAX_VALUE + 1; 
        int[] table = new int[ALPHABET_SIZE];
        for (int i = 0; i < table.length; ++i) {
            table[i] = search.length;
        }
        for (int i = 0; i < search.length; ++i) {
            table[search[i]] = search.length - 1 - i;
        }
        return table;
    }

    /**
     * Makes the jump table based on the scan offset which mismatch occurs.
     * (bad character rule).
     */
    private static int[] makeOffsetTable(char[] search) {
        int[] table = new int[search.length];
        int lastPrefixPosition = search.length;
        for (int i = search.length; i > 0; --i) {
            if (isPrefix(search, i)) {
                lastPrefixPosition = i;
            }
            table[search.length - i] = lastPrefixPosition - i + search.length;
        }
        for (int i = 0; i < search.length - 1; ++i) {
            int slen = 0;
            for (int k = i, l = search.length - 1;
                    k >= 0 && search[k] == search[l]; --k, --l) {
               slen += 1;
            }
            table[slen] = search.length - 1 - i + slen;
        }
        return table;
    }

    /**
     * Is needle[p:end] a prefix of needle?
     */
    private static boolean isPrefix(char[] search, int p) {
        for (int i = p, j = 0; i < search.length; ++i, ++j) {
            if (search[i] != search[j]) {
                return false;
            }
        }
        return true;
    }

   
}
