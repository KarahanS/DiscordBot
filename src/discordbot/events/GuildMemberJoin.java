package discordbot.events;


import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter {
	private final String[] messages = { "[member] joined. Say hi folk!", 
			"Welcome [member]. We've been expecting you.",
			"Hey! [member] has joined! Infinity and Beyond!", 
			"It's so exciting to see you around here [member].",
			"Sssh! [member] has just joined. Be careful.", 
			"Greetings [member]! How dost thou?",
			"Salutations [member]! Please have a seat.", 
			"Good morrow [member], howdy?",
			"Sit you down and have a laugh [member].",
			"Hey [member], where have you been? Come, join us."
			};


	/**
	 * This method is provoked when a member joins the channel.
	 * 
	 * @param event
	 */
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Random rand = new Random();
		
		EmbedBuilder join = new EmbedBuilder();
		join.setColor(0xf2ee0f);
		/**
		 * .getAsMention() puts an "@" symbol at the beginning of the name. 
		 */
		join.setDescription(messages[rand.nextInt(messages.length)].replace("[member]", event.getMember().getAsMention()));
		/**
		 * Default channel is the channel which you set as "System Messages Channel" while creating the Discord server.
		 * The word Guild is used so frequently in JDA, it simply points to our Discord channel.
		 */
		
		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
		join.clear();
		
		// Add role 
		
		/**
		 *  In order to get the ID's of members and roles, you need to open developer mode from channel settings on Discord.
		 *  In addition, you need to put an "L" at the end of the ID to show that that number is a long. Otherwise, Java
		 *  will confuse it with an integer and give an error. Instead of showing it as a long, we could input the number as String
		 *  without "L". That could also work since .getRoleById can take a String as a parameter too. (Function overloading)
		 */
		
		// TODO Create a .addRole command that adds ID's of roles to a String array. Then, bot will be able to assing roles randomly.
		event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(763729409709834261L)).queue();
		
	}
}
