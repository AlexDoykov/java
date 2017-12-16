package bg.uni.sofia.fmi.mjt.sentiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

	private String reviewsFileName;
	private String stopwordsFileName;
	private Set<String> stopWords;
	private Map<String, Pair> wordsWithSentimentScore;
	private final int NEGATIVE = 0;
	private final int SOMEWHAT_NEGATIVE = 1;
	private final int NEUTRAL = 2;
	private final int SOMEWHAT_POSITIVE = 3;
	private final int POSITIVE = 4;
	private final int UNKNOWN = -1;
	
	public MovieReviewSentimentAnalyzer(String reviewsFileName, String stopwordsFileName) {
		this.reviewsFileName = reviewsFileName;
		this.stopwordsFileName = stopwordsFileName;
		createSetFromStopWordsFile();
		wordsWithSentimentScore = new HashMap<>();
		calculateWordsSentimentScore();
	}
	
    /**
     * @param review
     *            the text of the review
     * @return the review sentiment as a floating-point number in the interval [0.0,
     *         4.0] if known, and -1.0 if unknown
     */
	@Override
	public double getReviewSentiment(String review) {
		int i = 0,count = 0;
		double sumSentimentScore = 0.0;
		String[] splittedReview = review.split(" ");
		while(i < splittedReview.length) {
			if(!stopWords.contains(splittedReview[i])) {
				if(wordsWithSentimentScore.containsKey(splittedReview[i])) {
					sumSentimentScore += wordsWithSentimentScore.get(splittedReview[i]).getSentimentScore();
					count += 1;
				}
			}
			i++;
		}
		
		if (count == 0) {
			return -1;
		}
		return sumSentimentScore / count;
	}

    /**
     * @param review
     *            the text of the review
     * @return the review sentiment as a name: "negative", "somewhat negative",
     *         "neutral", "somewhat positive", "positive"
     */
	@Override
	public String getReviewSentimentAsName(String review) {
		double reviewSentiment = getReviewSentiment(review);

		if (reviewSentiment == UNKNOWN) {
			return "unknown";
		}
		
		if (Math.round(reviewSentiment) < SOMEWHAT_NEGATIVE) {
			return "negative";
		}
		
		if (Math.round(reviewSentiment) < NEUTRAL) {
			return "somewhat negative";
		}
		
		if (Math.round(reviewSentiment) < SOMEWHAT_POSITIVE) {
			return "neutral";
		}
		
		if (Math.round(reviewSentiment) < POSITIVE) {
			return "somewhat positive";
		}
		
		return "positive";
		
	}
	
	private void calculateWordsSentimentScore(){
		try(BufferedReader r = new BufferedReader(new FileReader(reviewsFileName))) {
			String line;
			while((line = r.readLine()) != null) {
				String[] splittedLine = line.split(" ");
				int rating = Integer.parseInt(splittedLine[0]);
				int j = 1;
				while(j < splittedLine.length) {
					if((!stopWords.contains(splittedLine[j].toLowerCase()))  && !splittedLine[j].trim().equals(".") && !splittedLine[j].trim().equals(",")) {
						if(wordsWithSentimentScore.containsKey(splittedLine[j].toLowerCase())) {
							wordsWithSentimentScore.get(splittedLine[j].toLowerCase()).updateSentiment(rating);
						}else {
							wordsWithSentimentScore.put(splittedLine[j].toLowerCase(), new Pair(rating));
						}
					}
					j++;
				}
			};
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    private void createSetFromStopWordsFile() {
    	try(Stream<String> stream = Files.lines(Paths.get(stopwordsFileName))) {
    		stopWords = stream.collect(Collectors.toSet());
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
    /**
     * @param word
     * @return the review sentiment of the word as a floating-point number in the
     *         interval [0.0, 4.0] if known, and -1.0 if unknown
     */
	@Override
	public double getWordSentiment(String word) {
		if(!wordsWithSentimentScore.containsKey(word)) {
			return UNKNOWN;
		}
		return wordsWithSentimentScore.get(word).getSentimentScore();
	}

	 /**
     * Returns a collection of the n most frequent words found in the reviews
     */
	@Override
	public Collection<String> getMostFrequentWords(int n) {
		Collection<String> result;
		List<Map.Entry<String, Pair>> list = new ArrayList<>();
		list.addAll(wordsWithSentimentScore.entrySet());
		Collections.sort(list, (element1, element2) -> element1.getValue().compareTo(element2.getValue()));
		result = list.stream().map((t) -> t.getKey()).limit(n).collect(Collectors.toList());
		return result;
	}

    /**
     * Returns a collection of the n most positive words in the reviews
     */
	@Override
	public Collection<String> getMostPositiveWords(int n) {
		Collection<String> result;
		List<Map.Entry<String, Pair>> list = new ArrayList<>();
		list.addAll(wordsWithSentimentScore.entrySet());
		Collections.sort(list, (element1, element2) -> element1.getValue().compareTo3(element2.getValue()));
		result = list.stream().map((t) -> t.getKey()).limit(n).collect(Collectors.toList());
		return result;
	}

    /**
     * Returns a collection of the n most negative words in the reviews
     */
	@Override
	public Collection<String> getMostNegativeWords(int n) {
		Collection<String> result;
		List<Map.Entry<String, Pair>> list = new ArrayList<>();
		list.addAll(wordsWithSentimentScore.entrySet());
		Collections.sort(list, (element1, element2) -> element1.getValue().compareTo2(element2.getValue()));
		result = list.stream().map((t) -> t.getKey()).limit(n).collect(Collectors.toList());
		return result;
	}

    /**
     * Returns the total number of words with known sentiment score
     */
	@Override
	public int getSentimentDictionarySize() {
		//wordsWithSentimentScore.entrySet().stream().forEach(System.out::println);
		return wordsWithSentimentScore.size();
	}

    /**
     * Returns whether a word is a stopword
     */
	@Override
	public boolean isStopWord(String word) {
		return stopWords.contains(word);
	}

}
