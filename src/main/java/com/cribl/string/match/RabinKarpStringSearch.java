package com.cribl.string.match;

import java.util.Random;

public class RabinKarpStringSearch extends SubStringBase {

	int[] primeNumbers = { 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271,
			277, 281 };
	int prime;
	public final static int ALPHABETS = Character.MAX_VALUE;
	private char[] searchCharArray;

	public RabinKarpStringSearch(String searchString) {
		super(searchString);
		// TO avoid attacks, pick a random number. Can make it more complex
		prime = primeNumbers[new Random().nextInt(primeNumbers.length)];
		searchCharArray = searchString.toCharArray();
	}

	@Override
	protected boolean isSubStringInternal(String text) {
		int M = searchCharArray.length;
        int N = text.length();
        if (N< M) return false;
        int i, j;
        int p = 0; // hash value for searchString
        int t = 0; // hash value for text
        int h = 1;
  
        // The value of h would be "pow(d, M-1)%q"
        for (i = 0; i < M - 1; i++)
            h = (h * ALPHABETS) % prime;
  
        // Calculate the hash value of pattern and first
        // window of text
        for (i = 0; i < M; i++) {
            p = (ALPHABETS * p + searchCharArray[i]) % prime;
            t = (ALPHABETS * t + text.charAt(i)) % prime;
        }
  
        // Slide the pattern over text one by one
        for (i = 0; i <= N - M; i++) {
  
            // Check the hash values of current window of text
            // and pattern. If the hash values match then only
            // check for characters on by one
            if (p == t) {
                /* Check for characters one by one */
                for (j = 0; j < M; j++) {
                    if (text.charAt(i + j) != searchCharArray[j])
                        break;
                }
  
                // if p == t and pat[0...M-1] = text[i, i+1, ...i+M-1]
                if (j == M)
                {
                	return true;
                }
                
            }
  
            // Calculate hash value for next window of text: Remove
            // leading digit, add trailing digit
            if (i < N - M) {
                t = (ALPHABETS * (t - text.charAt(i) * h) + text.charAt(i + M)) % prime;
  
                // We might get negative value of t, converting it
                // to positive
                if (t < 0)
                    t = (t + prime);
            }
        }
		return false;
	}

}
