package discordbot.commands;

import discordbot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Mute extends ListenerAdapter {
	
	private final static long MutedRoleID = 766228126627266571L;   // Depends on the server.
	
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		if(arguments.length == 1) {
			EmbedBuilder instruction = new EmbedBuilder();
			instruction.setTitle("🔇 Usage: `" + Bot.prefix + "mute [user as mention] [time in seconds{optional}]`");
			instruction.setDescription("After \".mute\", please leave a space and input the user as a mention who you want to be muted. You can also"
					+ " input the mute duration. Time limit is optional. You can also unmute an already"
					+ " muted user with the same syntax.");
			instruction.setColor(0x03f4fc);
			event.getChannel().sendMessage(instruction.build()).queue();
			instruction.clear();
		}
		else if(arguments.length == 2) {
			// <@!763808791229628416>  =  @Buzz Lightyear
			
			/**
			 * .retrieve and .get are two different methods. .retrieve can be used for uncached information.
			 * We use .retrieve here since we do not cache the members. (Don't we? I'll make some search about that.)
			 */
			Member member = event.getGuild().retrieveMemberById(arguments[1].replace("<@!", "").replace(">", "")).complete();
			// Muted role ID
			Role muteRole = event.getGuild().getRoleById(MutedRoleID);
			
			// We check if the user is already muted.
			if(!member.getRoles().contains(muteRole)) {
				event.getGuild().addRoleToMember(member, muteRole).queue();
				EmbedBuilder mute = new EmbedBuilder();
				mute.setDescription("🔈  " + arguments[1] + " is muted.");
				mute.setColor(0x83838f);
				event.getChannel().sendMessage(mute.build()).queue();
				mute.clear();
				
			} else {
				event.getGuild().removeRoleFromMember(member, muteRole).queue();
				EmbedBuilder unmute = new EmbedBuilder();
				unmute.setDescription("🔊  " + arguments[1]+ " is unmuted.");
				unmute.setColor(0x87f081);
				event.getChannel().sendMessage(unmute.build()).queue();
				unmute.clear();
			}
		} else if (arguments.length == 3) {
			
			Member member = event.getGuild().retrieveMemberById(arguments[1].replace("<@!", "").replace(">", "")).complete();
			Role muteRole = event.getGuild().getRoleById(MutedRoleID);
			
			if(!member.getRoles().contains(muteRole)) {
				event.getGuild().addRoleToMember(member, muteRole).queue();
				EmbedBuilder mute = new EmbedBuilder();
				mute.setDescription("🔈  " + arguments[1] + " is muted for " + arguments[2] + " seconds.");
				mute.setColor(0x83838f);
				event.getChannel().sendMessage(mute.build()).queue();
				mute.clear();
				
				/**
				 * This piece of code simply executes the given run() method after the
				 * given amount of seconds are passed. There is a possibility that a muted user
				 * is unmuted manually before it's time is up. So, run() method checks if the user 
				 * still has the Muted role.
				 */
				new java.util.Timer().schedule(
						new java.util.TimerTask() {
							public void run() {
								if(member.getRoles().contains(muteRole)) {
									event.getGuild().removeRoleFromMember(member, muteRole).queue();
									EmbedBuilder unmute = new EmbedBuilder();
									unmute.setDescription("🔊  " + arguments[1]+ " is unmuted.");
									unmute.setColor(0x87f081);
									event.getChannel().sendMessage(unmute.build()).queue();
									unmute.clear();
									}	
								}
							},
						Integer.parseInt(arguments[2]) * 1000 // Seconds
						);
				
			} else {
		
				EmbedBuilder unmute = new EmbedBuilder();
				unmute .setTitle("⚠ Invalid Input");
				unmute.setDescription(arguments[1]+ " is already muted. If you want to unmute the user, please input"
						+  " `"+ Bot.prefix + "mute [user as mention]`");
				unmute.setColor(0xfc0303);
				event.getChannel().sendMessage(unmute.build()).queue();
				unmute.clear();
			}
		} else {
			EmbedBuilder error = new EmbedBuilder();
			error .setTitle("⚠ Invalid Input");
			error .setDescription("Please input the user as mention and mute duration if you want. It is optional, you don't need to"
					+ " enter a specific time limit. Do not forget to leave spaces between arguments.");
			error .setColor(0xfc0303);
			event.getChannel().sendMessage(error .build()).queue();
			error .clear();

		}

	}
}
