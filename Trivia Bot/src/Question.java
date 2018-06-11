// Class: Question
// Written by: Eric Huang
// Date: 6/11/18
// Description: Takes data from the json string and puts it into a class as a question
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;

public class Question {
	private URL url;
	private String question;
	private String answer;
	private JSONArray json;
	private String category;
	
	Question() throws IOException{
		this("http://jservice.io/api/random");
	}
	public Question(String input) throws IOException {
		this.url = new URL(input);
		String temp = "";
		Scanner s = new Scanner(url.openStream());
		while(s.hasNext())
			 temp += s.nextLine();
		JSONArray json = new JSONArray(temp);
		int random = (int)(Math.random() * json.length());
		question = json.getJSONObject(random).getString("question").replaceAll("(<i>|</i>|<b>|</b>)", "");
		answer = json.getJSONObject(random).getString("answer").replaceAll("(<i>|</i>|<b>|</b>)", "");
		category = json.getJSONObject(random).getJSONObject("category").getString("title").replaceAll("(<i>|</i>|<b>|</b>)", "");
	}
	public URL getUrl() {
		return url;
	}
	public String getQuestion() {
		return question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setUrl(URL url) {
		this.url = url;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	// Method: formatAnswer
	// input: String s - The String to format
	// output: String output - a formatted String
	// Description: Changes a string so it has no spaces and no contraction so it is easy to compare with another string
	public static String formatAnswer(String s) {
		String output = "";
		output = s.replaceAll(" ", "").trim().toLowerCase();
		output = output.replaceAll("\\b(the|a|an|is|are|was|were|what|whats|where|wheres|who|whos|and)\\b", "").replaceAll("(\\?|\\!|\\-|\\.|\\$|<i>|</i>|<b>|</b>)", "");
		output = s.replaceAll(" ", "").trim().toLowerCase();
		return output;
	}
}
