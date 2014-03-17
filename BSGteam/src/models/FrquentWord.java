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
 
public class FrquentWord  {
	
 private Map<String, Integer> wordCounts = new TreeMap<String, Integer>();
	
  public Map<String, Integer> countWord(String sentence){
 
        String text = sentence;
       // String answer = null;
        List<String> list = Arrays.asList(text.split(" "));
 
        Set<String> uniqueWords = new HashSet<String>(list);
        for (String word : uniqueWords) {
        	
        	int result = Collections.frequency(list, word);
        	
        	wordCounts.put(word, result);
          // answer += (word + " : " + Collections.frequency(list, word) + "\n ");
        }
    	//System.out.println(wordCounts);

		return wordCounts;
  }  
  
  
  public static void main(String[] args){
	  String name = "My name name name name name name name name name nam is is bas salsiu the the the the the the sheffield sheffield the sheffied she money money money sex sex gay lesbain marriage asd s fes dfd";
	  FrquentWord w = new FrquentWord();
	  Map<String, Integer> result = w.countWord(name);
	  List<Entry<String, Integer>> ee = w.sortByValue(result);
	  System.out.println(result);
	  System.out.println(ee);
  }
  
  
  
  List<Entry<String, Integer>> sortByValue(Map<String, Integer> map) {
	  List<Map.Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(map.entrySet());
	  
	  
	  Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
		    @Override
		    public int compare(Map.Entry<String, Integer >a, Map.Entry<String, Integer>b) {
		        if (a.getValue() < b.getValue()) { // Descending values
		            return 1;
		        } else if (a.getValue() > b.getValue()) {
		            return -1;
		        }
		        return -a.getKey().compareTo(b.getKey()); // Descending keys
		    }    
		});
	//  System.out.println(entries);
	return entries;
	  
	  
	  
  }
	  
	  
}
 