package discordbot.events;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionAdd extends ListenerAdapter {
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		/**
		 * .getReactionEmote() returns a reaction emote. In order to use it as a String, 
		 * we should use .getName() method. Then we can compare it with the reaction we want to
		 * see if they are same. In addition, we check if the user who sent the message is our bot.
		 * We don't want to react to any message that are written by our bot.
		 * 
		 * event.getMember() denotes a member, event.getJDA() denotes our bot.
		 * .getUser() and .getSelfUser() methods help us to turn them into same type of object so that
		 * we can compare them with each other.
		 * 
		 * I wrote a code under the Commands class that puts a reaction to every messages except bot's.
		 * Since bot drops that reactions, it doesn't get into this if statement. This part is important,
		 * otherwise bot would delete it's own reactions or even messages. We want to avoid that for sure.
		 */
		if(event.getReactionEmote().getName().equalsIgnoreCase("❌") && 
				!event.getMember().getUser().equals(event.getJDA().getSelfUser())) {

			
			/**
			 * Ok, so this is one of the longest statements we need to deal with. What do we have here? Let's start.
			 * We want to check if the owner of the message is also the owner of the reaction. (If a user reacted to his 
			 * own message.) 
			 * Here, event symbolizes the reaction event.
			 * event.getMember() --> returns the member who reacted. [Member]
			 * event.getMember().getUser() --> returns the user (turns member to user) [User]
			 * 
			 * event.getChannel() --> returns the channel to which reaction was sent. [TextChannel]
			 * event.getChannel().retrieveMessageById(String ID) --> returns a RestAction<Message> object which means you need to write .queue or .complete 
			 * for this action to be taken.  [RestAction<Message>]
			 * event.getMessageId() --> returns the ID of the message to which reaction was dropped.    [String]
			 * event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor() --> returns the author of the message.  [User]
			 */
			if(event.getMember().getUser().equals(event.getChannel().retrieveMessageById(event.getMessageId()).complete().getAuthor())) {
				/**
				 * If reaction is dropped by the owner of the message, it deletes the message.
				 */
				event.getChannel().retrieveMessageById(event.getMessageId()).complete().delete().queue();
			} else {  
				/**
				 * If reaction is dropped by someone other than the owner of the message, reaction is just deleted.
				 * Notice that we delete the reaction of the user, not the bot. Bot's reaction should stay so that we
				 * provide users with the possibility to delete their message dropping a reaction.
				 */
				event.getReaction().removeReaction(event.getUser()).queue();;
			}
		}
	}
}

