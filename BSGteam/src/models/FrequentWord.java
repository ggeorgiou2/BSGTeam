package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * FrequentWord.java This class finds the most frequent keywords from a group of
 * tweets
 * 
 * @author BSG Team
 */
public class FrequentWord {
	/**
	 * @param tweets
	 *          group of tweets to be analyze
	 * @return the frequency of each word in the tweets in sorted order
	 */
	public List<Map.Entry<String, Integer>> countWord(String tweets) {
		// declares wordCounts as variable of type TreeMap
		Map<String, Integer> wordCounts = new TreeMap<String, Integer>();

		String text = tweets;
		// splits the groups of words into tokens
		List<String> list = Arrays.asList(text.split(" "));
		// declared uniqeWors as variable of type HashSet and assigns the splits
		// result to it.
		Set<String> uniqueWords = new HashSet<String>(list);
		for (String word : uniqueWords) {
			// Analyzes the frequency of each token
			int result = Collections.frequency(list, word);
			if (word.length() > 4 && !word.contains("@") && !word.contains("tweet") && !word.contains("'")
					&& result > 1)
				wordCounts.put(word, result);
		}
		// sorts the result in descending order
		List<Map.Entry<String, Integer>> sorted_list = sortByValue(wordCounts);
		return sorted_list;
	}

	/**
	 * @param map
	 *          an argument of type Map<String, Integer>
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
