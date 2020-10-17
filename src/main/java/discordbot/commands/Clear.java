package discordbot.commands;

import java.util.List;

import discordbot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter{
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		if(arguments.length < 2) { // amount of lines to be deleted is not entered.
			
			EmbedBuilder instruction = new EmbedBuilder();
			instruction.setTitle("🧹 Usage: `" + Bot.prefix + "clear [# of messages]`");
			instruction.setDescription("After \".clear\", please leave a space and input the amount of messages you want to be deleted. You can"
					+ " delete 1 to 100 messages at the same time. No more, no less. You cannot delete messages older than 2 weeks.");
			instruction.setColor(0x03f4fc);
			event.getChannel().sendMessage(instruction.build()).queue();
			instruction.clear();

			
		} else {
			int lines;
			try {	
				lines = Integer.parseInt(arguments[1]);
				/**
				 * We need to create a List of messages in order to give them as a parameter to .deleteMessages(). 
				 * .getHistory() returns a MessageHistory object. This object has a method named .retrievePast(int amount).
				 * .retrievePast() method retrieves the amount of messages that is given to it. In order to get a List<Message> 
				 * type at the end, we need to call .complete() method. Truth be told, I don't know why, I'll find out. 
				 * Notice that .retrievePast(int amount) doesn't return List<Message>, returns RestAction<List<Message>>.
				 * 
				 * This is the information about RestAction in JDA github page. 
				 * 
				 * If you understand RestAction you understand JDA.
				 * In JDA 3.0 we introduced the new RestAction class which basically is a terminal between the JDA user and the Discord REST API.
				 * The RestAction is a step between specifying what the user wants to do and executing it, it allows the user to specify how JDA should deal with their Request.
				 * However this only works if you actually tell the RestAction to do something. That is why we recommend checking out whether or not 
				 * something in JDA returns a RestAction. If that is the case you have to execute it using one of the RestAction execution operations:
				 * 
				 * queue(), queue(Consumer), queue(Consumer, Consumer)
				 * These operations are asynchronous and will not execute within the same Thread.
				 * This means that you cannot use procedural logic when you use queue(), unless you use the callback Consumers.
				 * Only similar requests are internally executed in sequence such as sending messages in the same channel or adding reactions 
				 * to the same message.
				 * submit() = Provides request future to cancel tasks later and avoid callback hell.
				 * complete() = This operation will block the current Thread until the request has been finished and will return the response type.
				 * Note: We recommend using queue() or submit() when possible as blocking the current Thread can cause downtime and will use more resources.
				 */
				List<Message> messages = event.getChannel().getHistory().retrievePast(lines).complete();
				event.getChannel().deleteMessages(messages).queue();
				
				EmbedBuilder success = new EmbedBuilder();
				success.setTitle("✅ Successfully deleted " + arguments[1] + " messages.");
				success.setColor(0x35b81d);
				event.getChannel().sendMessage(success .build()).queue();
				success.clear();
			} catch (IllegalArgumentException e) {
				EmbedBuilder error = new EmbedBuilder();
				error .setTitle("⚠ Invalid Input");
				error .setDescription("Message retrieval limit is between 1 and 100. No more, no less. Make sure input doesn't contain any non numeric character." +
				" Keep in mind that messages older than 2 weeks cannot be deleted.");
				error .setColor(0xfc0303);
				event.getChannel().sendMessage(error .build()).queue();
				error .clear();
			}
		}
	}
}
