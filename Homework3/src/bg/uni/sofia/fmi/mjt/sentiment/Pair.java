package bg.uni.sofia.fmi.mjt.sentiment;

public class Pair {
	private double sentiment;
	private int matched;
	
	public Pair(int rating) {
		sentiment = rating;
		matched = 1;
	}
	
	public void updateSentiment(int rating) {
		matched += 1;
		sentiment += rating;
	}
	
	public double getSentimentScore() {
		return sentiment / matched;
	}

	public int compareTo(Pair value) {
		if (matched < value.matched) {
			return 1;
		}
		
		if (matched > value.matched) {
			return -1;
		}
		
		return 0;
	}
	
	public int compareTo2(Pair value) {
		if (getSentimentScore() > value.getSentimentScore()) {
			return 1;
		}
		
		if (getSentimentScore() < value.getSentimentScore()) {
			return -1;
		}
		
		return 0;
	}
	
	public int compareTo3(Pair value) {
		if (getSentimentScore() < value.getSentimentScore()) {
			return 1;
		}
		
		if (getSentimentScore() > value.getSentimentScore()) {
			return -1;
		}
		
		return 0;
	}
}
