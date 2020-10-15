package discordbot;


import javax.security.auth.login.LoginException;

import discordbot.commands.Commands;
import discordbot.events.GuildMemberJoin;
import discordbot.events.GuildMemberRemove;
import discordbot.events.GuildMessageReactionAdd;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;




public class Bot {
	/**
	 * To put simply, we can say that jda represents our bot.
	 */
	public static JDA jda;  
	public static String prefix = ".";
	 
	public static void main(String[] args) throws LoginException {
		/**
		 * Token is a text that basically makes you connect to your bot.
		 * It should be confidential. Do not share it with anyone else.
		 * Enter your token as the first argument. .build() simply gets
		 * your bot online (not the Discord status, it's different).
		 * I also used .enableIntents() method in order to make my bot
		 * follow who joins and who leaves the server. This intent is not in the default 
		 * gateway intents, don't forget to add it.
		 */
		jda = JDABuilder.createDefault(args[0]).enableIntents(GatewayIntent.GUILD_MEMBERS).build();  // default gateway intents + guild member track
		
		/**
		 * Presence means discord status. .setStatus() helps you to set bot's status (obviously).
		 * setActivity() takes an activity instance. Activity.playing() is a static method, we don't need
		 * to create an instance of Activity. (Actually, Activity is an interface. Static methods are now allowed in interfaces with Java 8.) 
		 * Activity.playing() returns a reference to an Activity instance. (It is interface, so i don't really understand. I googled it and
		 * yeah, apparently it is possible to return an interface from a method. But we don't put a "new" in front of it.)
		 */
		jda.getPresence().setStatus(OnlineStatus.ONLINE);  
		jda.getPresence().setActivity(Activity.playing("The Rise of the Elite Marines of the Assasination Squad 3: "
				+ "The Return of the Apocalypse in the Dawn of Dark Shadows"));
		
		
		jda.addEventListener(new Commands());
		jda.addEventListener(new GuildMemberJoin());
		jda.addEventListener(new GuildMemberRemove());  // Currently, it doesn't work. Under maintenance.
		jda.addEventListener(new GuildMessageReactionAdd());
	}
}
