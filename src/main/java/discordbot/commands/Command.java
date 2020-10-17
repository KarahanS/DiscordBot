package discordbot.commands;


import discordbot.Bot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		/**
		 * Check the GuildMessageReactionAdd section.
		 */
		if(!event.getMessage().getMember().getUser().equals(event.getJDA().getSelfUser())) {
			event.getMessage().addReaction("❌").queue();
		}
		
		
		/**
		 *  SO FAR PROVIDED COMMANDS - .greet  .embed   .clear (will be edited so that only admin and moderator can clear)   
		 *  .mute (will be edited so that only admin and moderator can mute) .calculate (could be improved - advance) 
		 *  .nickname
		 */
		
		String[] arguments = event.getMessage().getContentRaw().split("\\s+");
		
		switch (arguments[0].toLowerCase()) {
		case (Bot.prefix + "greet"): Greet.Execute(arguments, event);
		break;
		case (Bot.prefix + "embed"): Embed.Execute(arguments, event);
		break;
		case (Bot.prefix + "mute"): Mute.Execute(arguments, event);
		break;
		case (Bot.prefix + "nickname"): Nickname.Execute(arguments, event);
		break;
		case (Bot.prefix + "play"): Play.Execute(arguments, event);
		break;
		case (Bot.prefix + "clear"): Clear.Execute(arguments, event);
		break;
		case (Bot.prefix + "calculate"): Calculate.Execute(arguments, event);
		break;
		}
						
									
	}
		
}
