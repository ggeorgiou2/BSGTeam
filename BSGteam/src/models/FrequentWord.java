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
	public List<Map.Entry<String, Integer>> countWord(String tweets) {
		// declares wordCounts as variable of type TreeMap
		Map<String, Integer> wordCounts = new TreeMap<String, Integer>();

		String text = tweets;
		// splits the groups of words into tokens
		List<String> list = Arrays.asList(text.split(" "));

		String[] commonWordsString = { "able", "about", "above", "according",
				"accordingly", "across", "actually", "after", "afterwards",
				"again", "against", "albeit", "all", "allow", "allows",
				"almost", "alone", "along", "already", "also", "although",
				"always", "among", "amongst", "amoungst", "amount", "an",
				"and", "another", "any", "anybody", "anyhow", "anyone",
				"anything", "anyway", "anyways", "anywhere", "apart", "appear",
				"appreciate", "appropriate", "around", "aside", "ask",
				"asking", "associated", "available", "away", "awfully", "back",
				"became", "because", "become", "becomes", "becoming", "been",
				"before", "beforehand", "behind", "being", "believe", "below",
				"beside", "besides", "best", "better", "between", "beyond",
				"bill", "both", "bottom", "brief", "call", "came", "can",
				"cannot", "cant", "cause", "causes", "certain", "certainly",
				"changes", "clearly", "co", "com", "come", "comes",
				"comprises", "computer", "con", "concerning", "consequently",
				"consider", "considering", "contain", "containing", "contains",
				"corresponding", "could", "couldn't", "couldnt", "course",
				"cry", "currently", "d", "de", "definitely", "describe",
				"described", "desired", "despite", "detail", "did",
				"different", "do", "does", "don't", "doing", "done", "down",
				"downwards", "due", "during", "e", "each", "edu", "eg",
				"eight", "either", "eleven", "else", "elsewhere", "empty",
				"enough", "entirely", "especially", "et", "etc", "even",
				"ever", "every", "everybody", "everyone", "everything",
				"everywhere", "ex", "exactly", "example", "except", "f", "far",
				"few", "fifteen", "fifth", "fify", "fill", "find", "fire",
				"first", "five", "followed", "following", "follows", "for",
				"former", "formerly", "forth", "forty", "found", "four",
				"from", "front", "full", "further", "furthermore", "g",
				"generally", "get", "gets", "getting", "give", "given",
				"gives", "go", "goes", "going", "gone", "got", "gotten",
				"greetings", "h", "had", "happens", "hardly", "has", "hasnt",
				"have", "having", "he", "he's", "hello", "help", "hence",
				"her", "here", "hereafter", "hereby", "herein", "hereupon",
				"hers", "herself", "herse", "hi", "him", "himself", "himse",
				"his", "hither", "hopefully", "how", "howbeit", "however",
				"hundred", "i", "ie", "if", "ignored", "immediate", "in",
				"inasmuch", "inc", "incl", "indeed", "indicate", "indicated",
				"indicates", "inner", "insofar", "instead", "interest", "into",
				"inward", "is", "it", "its", "itse", "itself", "j", "just",
				"k", "keep", "keeps", "kept", "know", "known", "knows", "l",
				"last", "lately", "later", "latter", "latterly", "least",
				"less", "lest", "let", "let's", "like", "liked", "likely",
				"little", "look", "looking", "looks", "ltd", "m", "made",
				"mainly", "many", "may", "maybe", "me", "mean", "means",
				"meanwhile", "merely", "might", "mill", "mine", "more",
				"moreover", "most", "mostly", "move", "much", "must", "my",
				"myself", "myse", "n", "name", "namely", "nd", "near",
				"nearly", "necessary", "need", "needs", "neither", "never",
				"nevertheless", "new", "next", "nine", "no", "nobody", "non",
				"none", "noone", "nor", "normally", "not", "nothing", "novel",
				"now", "nowhere", "o", "obviously", "of", "off", "often", "oh",
				"ok", "okay", "old", "on", "once", "one", "ones", "only",
				"onto", "or", "other", "others", "otherwise", "ought", "our",
				"ours", "ourselves", "out", "outside", "over", "overall",
				"own", "people", "part", "particular", "particularly", "per",
				"perhaps", "placed", "please", "plus", "possible",
				"preferably", "preferred", "present", "presumably", "probably",
				"provides", "put", "q", "que", "quite", "qv", "r", "rather",
				"rd", "re", "really", "reasonably", "regarding", "regardless",
				"regards", "relatively", "respectively", "right", "s", "said",
				"same", "saw", "say", "saying", "says", "search", "second",
				"secondly", "see", "seeing", "seem", "seemed", "seeming",
				"seems", "seen", "self", "selves", "sensible", "sent",
				"serious", "seriously", "seven", "several", "shall", "she",
				"should", "show", "side", "since", "sincere", "six", "sixty",
				"so", "some", "somebody", "somehow", "someone", "something",
				"sometime", "sometimes", "somewhat", "somewhere", "soon",
				"sorry", "specified", "specify", "specifying", "still", "sub",
				"such", "suitable", "sup", "sure", "system", "t", "take",
				"taken", "tell", "ten", "tends", "th", "than", "thank",
				"thanks", "thanx", "that", "thats", "the", "their", "theirs",
				"them", "themselves", "then", "thence", "there", "thereafter",
				"thereby", "therefor", "therefore", "therein", "thereof",
				"theres", "thereto", "thereupon", "these", "they", "thick",
				"thin", "think", "third", "this", "thorough", "thoroughly",
				"those", "though", "three", "through", "throughout", "thru",
				"thus", "to", "together", "too", "took", "top", "toward",
				"towards", "tried", "tries", "truly", "try", "trying",
				"twelve", "twenty", "twice", "two", "u", "un", "under",
				"unfortunately", "unless", "unlikely", "until", "unto", "up",
				"upon", "us", "use", "used", "useful", "uses", "using",
				"usually", "uucp", "v", "value", "various", "very", "via",
				"viz", "vs", "w", "want", "wants", "was", "way", "we",
				"welcome", "well", "went", "were", "what", "whatever",
				"whatsoever", "when", "whence", "whenever", "whensoever",
				"where", "whereafter", "whereas", "whereat", "whereby",
				"wherefrom", "wherein", "whereinto", "whereof", "whereon",
				"whereto", "whereunto", "whereupon", "wherever", "wherewith",
				"whether", "which", "whichever", "whichsoever", "while",
				"whilst", "whither", "who", "whoever", "whole", "whom",
				"whomever", "whomsoever", "whose", "whosoever", "why", "will",
				"willing", "wish", "with", "within", "without", "wonder",
				"would", "your", "yours", "yourself", "yourselves", "zero",
				"http", "tweet", "twitter", "google", "yahoo" };
		HashSet<String> commonWords = new HashSet<String>(
				Arrays.asList(commonWordsString));

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
