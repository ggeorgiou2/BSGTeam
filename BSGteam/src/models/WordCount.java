package models;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

// WordCount.java
// by Scot Drysdale on 3/21/07

// Solve word count problem posed by Owen Astrachan

public class WordCount {
	private Map<String, Integer> wordCounts;  // Maps words into their frequencies
	
	public WordCount() {
		wordCounts = new TreeMap<String, Integer>();
	};

	// Do word count on file specified by a command-line argument
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File ("Shakespeare.txt"));
		WordCount counter = new WordCount();
		counter.countWordsInFile(sc);
		counter.outputWordsWithCounts();
	}
	
	// Read all words in the Scanner and create a map of words to frequencies
	public void countWordsInFile(Scanner wordScanner) {
		while(wordScanner.hasNext())  {
			String line = wordScanner.next().toLowerCase();
	    StringTokenizer st = new StringTokenizer(line," .,:?'");
	    // space, full stop, comma, etc.
                  // are included as token delimiters
                  // and are thus not tokens themselves
	    while (st.hasMoreTokens()) {
			String word = st.nextToken();
			Integer count = wordCounts.get(word);
			if(count == null)
				wordCounts.put(word, 1);
			else
				wordCounts.put(word, count + 1);
		}}			
	}
	
	// Takes the words with frequencies in wordCounts and prints them in 
	// decreasing order of frequency, with words with the same frequency 
	// in lexicographical order.
	public void outputWordsWithCounts() {
		TreeMap<Integer, TreeSet<String>> wordsByFrequency = 
			new TreeMap<Integer, TreeSet<String>>();;  
		   // Maps frequencies into Sets of words having that freqencies.  As used, 
		   // NEGATIVES of frequencies are keys (so default order will be descending frequency)
		
	    // Convert word/count map to count/set of words map
		Set<String> wordSet = wordCounts.keySet();
		for(String word: wordSet)  {
			Integer count = -wordCounts.get(word);  // negative to reverse order
			TreeSet<String> wordsWithCount = wordsByFrequency.get(count);
			if(wordsWithCount == null)  {
				wordsWithCount = new TreeSet<String>();
				wordsByFrequency.put(count, wordsWithCount);  
			}
			wordsWithCount.add(word);
		}
		
		// Print out the words in decreasing order of frequency, alphabetically
		// for words with the same frequency
		Set<Integer> countSet = wordsByFrequency.keySet();
		for(Integer count: countSet)  {
			TreeSet<String> wordsWithCount = wordsByFrequency.get(count);
			for(String word: wordsWithCount)
				System.out.println(-count + "\t" + word);
		}
	}
}
