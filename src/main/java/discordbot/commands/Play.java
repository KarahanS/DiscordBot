package discordbot.commands;

import discordbot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Play  extends ListenerAdapter{ 
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		if(!event.getMessage().getChannelType().equals(ChannelType.VOICE)) {
			EmbedBuilder error = new EmbedBuilder();
			error .setTitle("⚠ Wrong Channel");
			error .setDescription("Please connect to a voice channel to play a song.");
			error .setColor(0xfc0303);
			event.getChannel().sendMessage(error .build()).queue();
			error .clear();
		} else {
			if(arguments.length == 1) {
				EmbedBuilder instruction = new EmbedBuilder();
				instruction.setTitle("▶ Usage: `" + Bot.prefix + "play [video name]`");
				instruction.setDescription("After \".play\", please leave a space and enter a title that denotes the video or music you wanted to play.");
				instruction.setColor(0x03f4fc);
				event.getChannel().sendMessage(instruction.build()).queue();
				instruction.clear();
			} else {
				String video = "";
				for(int i=1; i<arguments.length; i++) video+=arguments[i] + " ";
				video = video.substring(0, video.length() - 1); 
				
			}
		}

	}
}
