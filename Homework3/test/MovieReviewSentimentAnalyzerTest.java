import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import bg.uni.sofia.fmi.mjt.sentiment.MovieReviewSentimentAnalyzer;

public class MovieReviewSentimentAnalyzerTest {
	
	private MovieReviewSentimentAnalyzer analyzer;
	private final int DICTIONARY_SIZE = 8;
	
	
	@Test
	public void testIsStopWordExpextedTrue() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviews.txt", "stopwords.txt");
		
		assertTrue(analyzer.isStopWord("a"));
	}
	
	@Test
	public void testIsStopWordExpextedFalse() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviews.txt", "stopwords.txt");
		
		assertFalse(analyzer.isStopWord("dog"));
	}
	
	@Test
	public void testGetSentimentDictionarySize() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviews.txt", "stopwords.txt");

		assertTrue(analyzer.getSentimentDictionarySize() == DICTIONARY_SIZE);
	}
	
	@Test
	public void testGetMostNegativeWords() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsMostWords.txt", "stopwords.txt");

		Collection<String> expected = Arrays.asList("series", "equal", "independent", "seeking", "worth");
		assertTrue(analyzer.getMostNegativeWords(5).equals(expected));
	}
	
	@Test
	public void testGetMostPositiveWords() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsMostWords.txt", "stopwords.txt");

		Collection<String> expected = Arrays.asList("quiet", "introspective", "entertaining", "equal", "independent");
		assertTrue(analyzer.getMostPositiveWords(5).equals(expected));
	}
	
	@Test
	public void testGetMostFrquentWords() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsMostFrequentWords.txt", "stopwords.txt");

		Collection<String> expected = Arrays.asList("entertaining", "introspective", "independent", "quiet", "seeking");
		assertTrue(analyzer.getMostFrequentWords(5).equals(expected));
	}
	
	@Test
	public void testGetWordSentiment() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsWordSentiment.txt", "stopwords.txt");
		
		assertTrue(analyzer.getWordSentiment("series") == 2.6666666666666665);
	}
	
	@Test
	public void testGetWordSentimentExpectedUnknown() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsWordSentiment.txt", "stopwords.txt");
		
		assertTrue(analyzer.getWordSentiment("ghost") == -1);
	}
	
	@Test
	public void testGetReviewSentiment() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentiment("series") == 0.25);
	}
	
	@Test
	public void testGetReviewSentimentExpectedUnknown() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentiment("ghost") == -1);
	}
	
	@Test
	public void testGetReviewSentimentExpectedNegative() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentimentAsName("series").equals("negative"));
	}
	
	@Test
	public void testGetReviewSentimentExpectedSomewhatNegative() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentimentAsName("dog hence eating").equals("somewhat negative"));
	}
	
	@Test
	public void testGetReviewSentimentExpectedNeutral() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentimentAsName("cat fish something neutral").equals("neutral"));
	}
	
	@Test
	public void testGetReviewSentimentExpectedSomewhatPositive() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentimentAsName("car stop").equals("somewhat positive"));
	}
	
	@Test
	public void testGetReviewSentimentExpectedPositive() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentimentAsName("positive thinking").equals("positive"));
	}
	
	@Test
	public void testGetReviewSentimentExpectedUnknownAsName() {
		analyzer = new MovieReviewSentimentAnalyzer("movieReviewsSentiment.txt", "stopwords.txt");
		assertTrue(analyzer.getReviewSentimentAsName("ghost").equals("unknown"));
	}
}
