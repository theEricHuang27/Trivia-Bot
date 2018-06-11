//Class: Trivia
//Written by: Eric Huang
//Date: 6/11/18
//Description: Holds all the information for a specfic trivia game
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.Timer;

import org.json.JSONObject;

import sx.blah.discord.api.events.Event;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import org.json.JSONArray;

public class Trivia {
	private int timeLimit;
	private ArrayList<Player> scoreboard;
	private boolean start;
	private int category;
	private Question question;
	private String url;
	int time;
	Timer t;

	public Trivia(int category, int timeLimit) throws IOException{
		this.category = category;
		this.timeLimit = timeLimit;
		scoreboard = new ArrayList<>();
		start = true;
		if(category == 0)
			url = "http://jservice.io/api/random";
		else
			url = "http://jservice.io/api/clues?category=" + category;
		question = new Question(url);
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public ArrayList<Player> getScoreboard() {
		return scoreboard;
	}
	
	public int getCategory() {
		return category;
	}
	
	public void setNewQuestion() throws IOException {
		question = new Question(url);
	}
	
	
	public boolean isStart() {
		return start;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	
	public void addScoreboard(Player p) {
		scoreboard.add(p);
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public Question getQuestion() {
		return question;
	}
	// Method: checkAnswer
	// input: String s - the answer to check with the real answer
	// output: void
	// Description: checks the answer to see if they are close enough
	public boolean checkAnswer(String s) {
		return similarity(Question.formatAnswer(s), Question.formatAnswer(question.getAnswer())) > .7;
	}
	
	public void tick() {
		time++;
	}
	
	// Method: Similarity
	// input: String s1, s2 - the answer to check with the real answer
	// output: void
	// Description: give a value saying how similar they are
	public static double similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) {
			longer = s2; shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) 
			return 1.0;
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}	
	// Method: edit distance
	// input: String s1,s2 - The Strings to compare
	// output: void
	// Description: Levenshtein distance algorithm that checks how close strings are to each other
	public static int editDistance(String s1, String s2) { //Levenshtein distance algorithm
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue),
									costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}
}
