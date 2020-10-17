package discordbot.commands;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Embed  extends ListenerAdapter{
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		/**
		 * Embed is a little information box. We can create an embed using EmbedBuilder.
		 * .setTitle() simply takes the title to be shown at the uppermost part of the embed.
		 * .addField() adds a subtitle and a description below it respectively. For now I don't know
		 * why we add a boolean value. When I find out, I'll write.
		 * .setColor() sets the color of embed. You can find color codes from google. You need to
		 * copy paste the code without "#" and put "0x" at the very beginning.
		 * Then you need to send this embed as a message. sendMessage takes a String, .build() returns
		 * the embed in a string form.
		 * .clear() erases the embed object. We need to do it since it takes lots of space if we leave them behind.
		 * Actually you should be still calling that object after using clear() method, however any subtraction or 
		 * adding won't effect your embed anymore.
		 * .setFooter() also creates a footer design with a text and an extremely tiny image. It takes an URL as a parameter,
		 * I wrote a code so that it will take the image URL of the member who asks for it's embed.
		 */
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle( "🚀 Buzz Lightyear");
		embed.setDescription("“To Infinity and Beyond!”\r\n" + 
				"A real space ranger from planet Morph, who's sworn to protect the galaxy from the Evil Emperor Zurg.\r\n" + 
				"..or..\r\n" + 
				"A plastic bilingual spaceman action figure with wings, a laser and a helmet.");
		embed.addField("Discord","Tommy Miller", true);
		embed.setColor(0xad42f5);
		embed.setFooter("Created by KarahanS", event.getMember().getUser().getAvatarUrl());
		event.getChannel().sendMessage(embed.build()).queue();
		embed.clear();

	}
}
