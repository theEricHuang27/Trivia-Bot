// Class: Trivia Bot
// Written by: Eric Huang
// Date: 6/11/18
// Description: Place where it handles when messages are recieved and runs the game
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Timer;

import org.eclipse.jetty.io.ssl.ALPNProcessor.Client;
import org.json.simple.parser.ParseException;

import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class InterfaceListener implements IListener<MessageReceivedEvent>, Runnable{// The event type in IListener<> can be any class which extends Event
	private Trivia trivia;
	Timer t;
	int time;
	MessageReceivedEvent event;

	public InterfaceListener() {
		trivia = null;
		time = 0;
		t = new Timer(5,new ClockListener(this));
		this.event = null;
	}

	// Method: handle
	// input: Messagereceivedevent event: called when ever someone types a message
	// output: void
	// Description: called when someone types in the server when the bot is on and reads the message.
	public void handle(MessageReceivedEvent event) {
		this.event = event;
		System.out.println(event.getMessage().getContent());
		String[] command;
		if(event.getMessage().getContent().substring(0, 1).equals("!")) {
			command = event.getMessage().getContent().substring(1).split(" ");
			if(command[0].equals("hi"))
				event.getChannel().sendMessage("```\nhello\n```");
			else if(command[0].equals("start") && (trivia == null || !trivia.isStart())) { //!start command - starts a game with the parameters
				if(command.length <= 1) {
					try {
						trivia = new Trivia(0,15);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else if(command.length <= 2) {
					try {
						trivia = new Trivia(Integer.parseInt(command[1]),15);
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
						}
					}
				else if(command.length <= 3) {
					try {
						trivia = new Trivia(Integer.parseInt(command[1]),Integer.parseInt(command[2]));
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
					}
				}
				trivia.setStart(true);
				new Thread(this).start();
			}
			else if(command[0].equals("stop")) { //!stop command - stops a running game
				if(trivia != null) {
					event.getChannel().sendMessage("```Game stopped!```");
					trivia.setStart(false);
					time = 3001;
				}
			}
			else if(command[0].equals("score")) { //!score command - prints the scoreboard
				if(trivia != null) {
					printScoreboard();
				}
			}
			else if(command[0].equals("help")) { //!help command - shows the user all the commands
				event.getChannel().sendMessage("```"
						+ "!help - list commands\n"
						+ "!start (categoryID) (Time Per Question) - starts a game with the specified parameters () = optional parameters\n"
						+ "!stop - stops the game\n"
						+ "!score - displays the scoreboard\n"
						+ "!category - lists some popular categories and thier IDs"
						+ "!hi - hello```");
			}
			else if(command[0].equals("category")) { //!category commmand - pms the player the categories
				event.getAuthor().getOrCreatePMChannel().sendMessage("```Name ID\n"
						+ "Potpourriiii	306\n" + 
						"Stupid Answers	136\n" + 
						"Sports	42\n" + 
						"American History	780\n" + 
						"Animals	21\n" + 
						"3 Letter Words	105\n" + 
						"Science	25\n" + 
						"Transportation	103\n" + 
						"U.S. Cities	7\n" + 
						"People	442\n" + 
						"Television	67\n" + 
						"Hodgepodge	227\n" + 
						"State Capitals	109\n" + 
						"History	114\n" + 
						"The Bible	31\n" + 
						"Business & Industry	176\n" + 
						"U.S. Geography	582\n" + 
						"Annual Events	1114\n" + 
						"Common Bonds	508\n" + 
						"Food	49\n" + 
						"Rhyme Time	561\n" + 
						"Word Origins	223\n" + 
						"Pop Music	770\n" + 
						"Holidays & Observances	622\n" + 
						"Americana	313\n" + 
						"Food & Drink	253\n" + 
						"Weights & Measures	420\n" + 
						"Potent Potables	83\n" + 
						"Musical Instruments	184\n" + 
						"Bodies Of Water	211\n" + 
						"4 Letter Words	51\n" + 
						"Museums	539\n" + 
						"Nature	267\n" + 
						"Organizations	357\n" + 
						"World History	530\n" + 
						"Travel & Tourism	369\n" + 
						"Colleges & Universities	672\n" + 
						"Nonfiction	793\n" + 
						"World Capitals	78\n" + 
						"Literature	574\n" + 
						"Fruits & Vegetables	777\n" + 
						"Mythology	680\n" + 
						"U.S. History	50\n" + 
						"Religion	99\n" + 
						"The Movies	309\n" + 
						"First Ladies	41\n" + 
						"Fashion	26\n" + 
						"Homophones	249\n" + 
						"Quotations	1420\n" + 
						"Science & Nature	218\n" + 
						"Foreign Words & Phrases	1145\n" + 
						"Around The World	1079\n" + 
						"5 Letter Words	139\n" + 
						"Double Talk	89\n" + 
						"U.S. States	17\n" + 
						"Books & Authors	197\n" + 
						"Nursery Rhymes	37\n" + 
						"Brand Names	2537\n" + 
						"Familiar Phrases	705\n" + 
						"Before & After	1800\n" + 
						"Body Language	897\n" + 
						"Number, Please	1195\n```" + "go to http://jservice.io/search to find more");
			}
		}
	}
	// Method: run
	// input: void
	// output: void
	// Description: called when someone starts a new trivia game and runs at the same time as the handle method
	public void run() {
		while(trivia.isStart()) {
			t.start();
			event.getChannel().sendMessage("```Category: "+trivia.getQuestion().getCategory()+"\n" + trivia.getQuestion().getQuestion() + "```");
			boolean moveOn = false;
			
			System.out.println(trivia.getQuestion().getQuestion());
			System.out.println(event.getMessage().getContent());
			System.out.println(Question.formatAnswer(trivia.getQuestion().getAnswer()));
			System.out.println(Question.formatAnswer(event.getMessage().getContent()));
			while(time < (trivia.getTimeLimit()/5 * 1000) && !moveOn) {	
				if(trivia.checkAnswer(event.getMessage().getContent())){
					event.getChannel().sendMessage("Correct! +1 for " + event.getAuthor().getName());
					boolean inScoreboard = false;
					for(Player i:trivia.getScoreboard())
						if(i.getUser().getName().equals(event.getAuthor().getName())) {
							i.addScore();
							inScoreboard = true;
						}
					if(!inScoreboard)
					trivia.getScoreboard().add(new Player(event.getAuthor(),1));
					moveOn = true;
					Collections.sort(trivia.getScoreboard());
				}
			}
			if(!moveOn && trivia.isStart())
				event.getChannel().sendMessage("```Time's up! The answer is:\n" + trivia.getQuestion().getAnswer() + "```");
			try {
				trivia.setNewQuestion();
			} catch (IOException e) {
				e.printStackTrace();
			}
			t.stop();
			time = 0;
		}
	}


	public void tick() { //ticks the clock listener
		time++;
	}
	// Method: Printscoreboard
	// input: void
	// output: void
	// Description: prints the scoreboard out to the channel
	public void printScoreboard() {
		String output = "```Score : Player";
		for(Player p:trivia.getScoreboard())
			output += "\n" + p.getScore() +" : " + p.getUser().getName();
		event.getChannel().sendMessage(output + "```");
	}
	

}