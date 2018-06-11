// Project: Trivia Bot
// Written by: Eric Huang
// Date: 6/11/18
// Description: A bot that can play trivia with people through discord
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.DiscordException;

public class TriviaBotMain {
	public static void main(String[] args) {
		
		IDiscordClient client = createClient("NDQ3MjI4Mjg2NTQ5MDk4NDk4.DeRpZw.oe2vpFzHcpicsDnNmSJhjIhHFyI",true);
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new InterfaceListener());
       
	}
	public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the
																					// Discord client
		ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
		clientBuilder.withToken(token); // Adds the login info to the builder
		try {
			if (login) {
				return clientBuilder.login(); // Creates the client instance and logs the client in
			} else {
				return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you
												// would have to call client.login() yourself
			}
		} catch (DiscordException e) { // This is thrown if there was a problem building the client
			e.printStackTrace();
			return null;
		}
	}

}
