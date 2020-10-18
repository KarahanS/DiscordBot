package discordbot.commands;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Invite extends ListenerAdapter{
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		if(arguments.length == 1) {
			int time = 3600;
			EmbedBuilder instruction = new EmbedBuilder();
			/**
			 * .setMaxAge() helps us to set a time limit for our invitation link. After the given amount of time passes, link becomes useless.
			 * Time should be in second format. Right now it is scheduled to one hour.
			 */
			instruction.setDescription("You wanna invite someone " + event.getMember().getAsMention() + "? Send him this link : " + event.getChannel().createInvite().setMaxAge(time).complete().getUrl() + 
					" - The invite link expires in " + time + " seconds.");
			instruction.setColor(0x03f4fc);
			event.getChannel().sendMessage(instruction.build()).queue();
			instruction.clear();

		} else {
			
		}
	}
}
