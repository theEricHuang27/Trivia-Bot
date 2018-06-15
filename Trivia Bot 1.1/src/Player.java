// Class: Player
// Written by: Eric Huang
// Date: 6/11/18
// Description: Class for a player that stores the User and their score to put on the scoreboard
import sx.blah.discord.handle.obj.IUser;

public class Player implements Comparable<Player>{
	private IUser user;
	private int score;
	
	Player(IUser user,int score){
		this.user = user;
		this.score = score;
	}
	
	public IUser getUser() {
		return user;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setUser(IUser user) {
		this.user = user;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void addScore() {
		score++;
	}

	@Override
	public int compareTo(Player o) { // compares the player by score
		if(this.getScore() > o.getScore())
			return -1;
		if(this.getScore() < o.getScore())
			return 1;
		else
			return 0;
	}
	
	
}
