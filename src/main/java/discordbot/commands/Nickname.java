package discordbot.commands;

import discordbot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Nickname extends ListenerAdapter {
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		if(arguments.length == 1) {
			EmbedBuilder instruction = new EmbedBuilder();
			instruction.setTitle("🎭 Usage: `" + Bot.prefix + "nickname [nickname]`");
			instruction.setDescription("After \".nickname\", please leave a space and enter the nickname you want. Nickname is simply"
					+ " the name you want to be displayed on this server.");
			instruction.setColor(0x03f4fc);
			event.getChannel().sendMessage(instruction.build()).queue();
			instruction.clear();
		} else {
			String name = "";
			for(int i=1; i<arguments.length; i++) name+=arguments[i] + " ";
			name = name.substring(0, name.length() - 1); 
			event.getGuild().modifyNickname(event.getMember(), name).queue();
			EmbedBuilder success = new EmbedBuilder();
			success.setDescription("🖋  Your nickname is succesfully changed " + event.getMember().getAsMention() +" .");
			success.setColor(0x35b81d);
			event.getChannel().sendMessage(success .build()).queue();
			success.clear();
		}

	}
}
