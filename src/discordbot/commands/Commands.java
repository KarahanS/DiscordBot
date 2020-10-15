package discordbot.commands;

import java.util.List;

import discordbot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
	private final String BuzzGreet = "Hello everyone! I am Buzz Lightyear from the planet Morph. To infinity and beyond!";
	private final String BuzzEmbedTitle = "🚀 Buzz Lightyear";
	private final String BuzzEmbedDescription = "“To Infinity and Beyond!”\r\n" + 
			"A real space ranger from planet Morph, who's sworn to protect the galaxy from the Evil Emperor Zurg.\r\n" + 
			"..or..\r\n" + 
			"A plastic bilingual spaceman action figure with wings, a laser and a helmet.";
	private final long MutedRoleID = 766228126627266571L;   // Depends on the server.
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		/**
		 * Check the GuildMessageReactionAdd section.
		 */
		if(!event.getMessage().getMember().getUser().equals(event.getJDA().getSelfUser())) {
			event.getMessage().addReaction("❌").queue();
		}
		
		
		/**
		 *  SO FAR PROVIDED COMMANDS - .greet  .embed   .clear (will be edited so that only admin and moderator can clear)   
		 *  .mute (will be edited so that only admin and moderator can mute)
		 */
		
		
		
		String[] arguments = event.getMessage().getContentRaw().split("\\s+");
		
		/**
		 * Keep in mind that discord doesn't allow bots to delete message older than 2 weeks. Also
		 * bot is not able to delete more than 100 messages at the same time.
		 */
		if(arguments[0].equalsIgnoreCase(Bot.prefix + "greet")) {
			/**
			 * event is the thing we do. In this case, it is the message we wrote on Discord.
			 * .getChannel() returns the writing channel on which I wrote the message, like general or announcements etc.
			 * .sendTyping() shows a typing icon for bot, it creates a more realistic atmosphere, that's all.
			 * .sendMessage() sends the meassage to the given channel.
			 * .queue() adds the action to the queue of events. If you don't add queue() method, it doesn't work because you
			 * don't put the action in line.
			 * 
			 */
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(BuzzGreet).queue();
			
			
		} else if (arguments[0].equalsIgnoreCase(Bot.prefix + "embed")) {
			
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
			embed.setTitle(BuzzEmbedTitle);
			embed.setDescription(BuzzEmbedDescription);
			embed.addField("Discord","Tommy Miller", true);
			embed.setColor(0xad42f5);
			embed.setFooter("Created by KarahanS", event.getMember().getUser().getAvatarUrl());
			event.getChannel().sendMessage(embed.build()).queue();
			embed.clear();

		}
		else if( arguments[0].equalsIgnoreCase(Bot.prefix + "clear")) {
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
		else if (arguments[0].equalsIgnoreCase(Bot.prefix + "mute")) {
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
		} else if (arguments[0].equalsIgnoreCase(Bot.prefix + "nickname")) {
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
		// TODO Complete the play command.
		else if (arguments[0].equalsIgnoreCase(Bot.prefix + "play")) {
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

}
