package discordbot.commands;


import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Greet extends ListenerAdapter {
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		/**
		 * event is the thing we do. In this case, it is the message we wrote on Discord.
		 * .getChannel() returns the writing channel on which I wrote the message, like general or announcements etc.
		 * .sendTyping() shows a typing icon for bot, it creates a more realistic atmosphere, that's all.
		 * .sendMessage() sends the meassage to the given channel.
		 * .queue() adds the action to the queue of events. If you don't add queue() method, it doesn't work because you
		 * don't put the action in line.
		 * 
		 */
		event.getChannel().sendTyping().queue();
		event.getChannel().sendMessage("Hello everyone! I am Buzz Lightyear from the planet Morph. To infinity and beyond!").queue();

	}
}
