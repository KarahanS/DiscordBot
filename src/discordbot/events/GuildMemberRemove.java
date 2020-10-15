package discordbot.events;


import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Under Maintenance
 * @author karab
 *
 */
public class GuildMemberRemove extends ListenerAdapter {
	private final String[] messages = { "Our beloved friend [member] has just left.", 
			"Good bye [member]! We'll be waiting for you.",
			"Farewell [member]! Take care of yourself.", 
			"[member] left. See you later, alligator!",
			"[member] is gone. We'll miss him very much."
			};

	public void onGuildMemberLeave(GuildMemberRemoveEvent event) {
		Random rand = new Random();
		
		EmbedBuilder remove = new EmbedBuilder();
		remove.setColor(0xab65e0);
		/**
		 * .getAsMention() puts an "@" symbol at the beginning of the name. 
		 */
		remove.setDescription(messages[rand.nextInt(messages.length)].replace("[member]", event.getMember().getAsMention()));
		/**
		 * Default channel is the channel which you set as "System Messages Channel" while creating the Discord server.
		 * The word Guild is used so frequently in JDA, it simply points to our Discord channel.
		 */
		
		event.getGuild().getDefaultChannel().sendMessage(remove.build()).queue();
		remove.clear();
	}
}
