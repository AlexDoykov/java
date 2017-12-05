package bg.uni.sofia.fmi.mjt.sentiment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

	private String reviewsFileName;
	private String stopwordsFileName;
	private Set<String> stopWords;
	private Map<String, Double> wordsWithSentimentScore;
	
	public MovieReviewSentimentAnalyzer(String reviewsFileName, String stopwordsFileName) {
		this.reviewsFileName = reviewsFileName;
		this.stopwordsFileName = stopwordsFileName;
		stopWords = createStreamFromFile(stopwordsFileName).collect(Collectors.toSet());
		wordsWithSentimentScore = calculateWordsSentimentScore();
	}
	
    /**
     * @param review
     *            the text of the review
     * @return the review sentiment as a floating-point number in the interval [0.0,
     *         4.0] if known, and -1.0 if unknown
     */
	@Override
	public double getReviewSentiment(String review) {
		return 0;
	}

    /**
     * @param review
     *            the text of the review
     * @return the review sentiment as a name: "negative", "somewhat negative",
     *         "neutral", "somewhat positive", "positive"
     */
	@Override
	public String getReviewSentimentAsName(String review) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Map<String, Double> calculateWordsSentimentScore(){
		return null;
	}

    private Stream<String> createStreamFromFile(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
        	return stream;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
	
    /**
     * @param word
     * @return the review sentiment of the word as a floating-point number in the
     *         interval [0.0, 4.0] if known, and -1.0 if unknown
     */
	@Override
	public double getWordSentiment(String word) {
		// TODO Auto-generated method stub
		return 0;
	}

	 /**
     * Returns a collection of the n most frequent words found in the reviews
     */
	@Override
	public Collection<String> getMostFrequentWords(int n) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Returns a collection of the n most positive words in the reviews
     */
	@Override
	public Collection<String> getMostPositiveWords(int n) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Returns a collection of the n most negative words in the reviews
     */
	@Override
	public Collection<String> getMostNegativeWords(int n) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Returns the total number of words with known sentiment score
     */
	@Override
	public int getSentimentDictionarySize() {
		// TODO Auto-generated method stub
		return 0;
	}

    /**
     * Returns whether a word is a stopword
     */
	@Override
	public boolean isStopWord(String word) {
		return stopWords.contains(word);
	}

}
