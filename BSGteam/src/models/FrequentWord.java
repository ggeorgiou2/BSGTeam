package models;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * FrequentWord.java
 * 
 * This class finds the most frequent keywords from a group of tweets
 * 
 * @author BSG Team
 */
public class FrequentWord {
	/**
	 * Analyses a set of tweets to retrieve the most frequent occuring words
	 * 
	 * @param tweets
	 *            group of tweets to be analyzed
	 * @return the frequency of each word in the tweets in sorted order
	 */
	public List<Map.Entry<String, Integer>> countWord(String tweets, HashSet<String> commonWords) {
		// declares wordCounts as variable of type TreeMap
		Map<String, Integer> wordCounts = new TreeMap<String, Integer>();

		String text = tweets;
		// splits the groups of words into tokens
		List<String> list = Arrays.asList(text.split(" "));
		
		Set<String> uniqueWords = new HashSet<String>(list);
		for (String word : uniqueWords) {
			// Analyzes the frequency of each word
			int result = Collections.frequency(list, word);
			if (word.length() > 4 && !word.contains("@")
					&& !word.startsWith("http") && !word.contains("'")
					&& !commonWords.contains(word.toLowerCase()) && result > 1) {
				wordCounts.put(word, result);
			}
		}
		// sorts the result in descending order
		List<Map.Entry<String, Integer>> sorted_list = sortByValue(wordCounts);
		return sorted_list;
	}

	/**
	 * Sorts a frequent wordlist in descending order of frequency
	 * 
	 * @param map
	 *            an argument of type Map<String, Integer>
	 * @return sorted list in descending order
	 */
	private List<Entry<String, Integer>> sortByValue(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(
				map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> a,
					Map.Entry<String, Integer> b) {
				// Sorts in descending order
				if (a.getValue() < b.getValue()) {
					return 1;
				} else if (a.getValue() > b.getValue()) {
					return -1;
				}
				return -a.getKey().compareTo(b.getKey());
			}
		});
		return entries;
	}
}