package discordbot.commands;

import discordbot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Calculate extends ListenerAdapter {
	public static void Execute(String[] arguments, GuildMessageReceivedEvent event) {
		if (arguments.length == 1) {
			EmbedBuilder instruction = new EmbedBuilder();
			instruction.setTitle("🧮 Usage: `" + Bot.prefix + "calculate [arithmetic expression]`");
			instruction.setDescription(
					"After \".calculate\", please leave a space and enter the arithmetic expression clearly. Please do not use any non-numeric characters apart from operators and parentheses. For now, I support addition (+), substraction (-),"
							+ "multiplication (*) and division (/). You can also use parentheses to indicate order of operations as you want.");
			instruction.setColor(0x03f4fc);
			event.getChannel().sendMessage(instruction.build()).queue();
			instruction.clear();
		} else {
			String calculation = "";
			String answer = "";
			for (int i = 1; i < arguments.length; i++) {
				calculation += arguments[i];

			}
			try {
				// Calculation Algorithm

				calculation = "(" + calculation + ")";
				while (calculation.contains("(")) { // This process will be implemented as long as our statement
													// involves parentheses.
					String str = ""; // String str will be used to store the content of the most inner parentheses.
					for (int i = calculation.indexOf(")"); i >= 0; i--) { // This for loop used to find where the
																			// beginning of the parenthesis is.
						if (calculation.charAt(i) == '(') {
							str = calculation.substring(i, calculation.indexOf(")") + 1);
							break;
						}
					}

					if (str.contains("/") || str.contains("*")) { // At this point we are looking for
																	// divison/multiplication operators.
						for (int n = 0; n < str.length(); n++) {
							// n will be the index of operation sign.
							if (str.charAt(n) == '/' || str.charAt(n) == '*') {
								// Calculation_Update method performs the necessary operation and returns the
								// result.
								calculation = Calculation_Update(str, n, calculation);
								break;
							}
						}

					} else if (str.contains("+") || str.contains("-")) { // At this point we are looking for
																			// summation/subtraction operators.
						for (int n = 0; n < str.length(); n++) {
							// n will be the index of operation sign.
							if (str.charAt(n) == '+' || str.charAt(n) == '-') {
								calculation = Calculation_Update(str, n, calculation);
								// Calculation_Update method performs the necessary operation and returns the
								// result.
								break;
							}
						}

					} else {
						// If there is no operator to perform left, then we are going to remove the
						// parentheses, and update the calculation statement.
						calculation = calculation.replace(str, str.substring(1, str.length() - 1));
					}
				}
				// Print out the calculation without redundant empty spaces.

				for (int k = 0; k < calculation.length(); k++) {
					if (calculation.charAt(k) != ' ')
						answer += "" + calculation.charAt(k);
				}
				EmbedBuilder success = new EmbedBuilder();
				success.setDescription("Answer = " + answer);
				success.setColor(0x35b81d);
				event.getChannel().sendMessage(success.build()).queue();
				success.clear();
			} catch (Exception e) {
				EmbedBuilder error = new EmbedBuilder();
				error.setDescription(
						"I encountered with an error, thus I'm not able to return an answer. Please make sure that you input the arithmetic"
								+ " operation in proper order. Some common mistakes are using unmatched number of parentheses, using a non-numeric character other than"
								+ " operator signs and parentheses, using an unsupported operator, not putting operators between two numbers and not putting numbers "
								+ "between two operatos. Error description in code --> " + e.toString());
				error.setColor(0xfc0303);
				event.getChannel().sendMessage(error.build()).queue();
				error.clear();
			}

		}
	}

// Calculation_Update() is the most essential method we have. At the previous methods we extracted first and second values from the string str.
	// With Operation_String() we found at which point our operation starts and at
	// which point it ends.
	public static String Calculation_Update(String str, int n, String calculation) {
		// n is the index of operation sign. We defined it in the main method.
		char operator = str.charAt(n);
		// We have already gone over the Operation_String(), we just need to assign it
		// to a variable called opt (operation).
		String opt = Operation_String(str, operator);
		// We send opt (operation string), calculation statement, operator statement,
		// first value and second value of the operation into the Operation() method.
		// It will give the latest form of calculation after performing the operation.
		calculation = Operation(opt, calculation, operator, first_value(opt, operator), second_value(opt, operator));
		return calculation;
	}

	// Operation_String() examines the content of str string which we initialized at
	// the very beginning of the code.
	// Then it takes the substring which includes only one operation sign and two
	// numbers.
	// We need to implement this process since original str string can involve more
	// than one operations.
	public static String Operation_String(String str, char operator) {
		// end and start integers will be the beginning and the end indices of the
		// substring.
		int end = 0, start = 0;
		for (int i = str.indexOf(operator) - 1; i >= 0; i--) {
			// Here we start to move back from the index of operator sign, and we will
			// continue until we see another operator sign or parenthesis.
			if (!((str.charAt(i) <= '9' && str.charAt(i) >= '0') || str.charAt(i) == '.' || str.charAt(i) == ' ')) {
				// We stored at what point our substring should begin.
				start = i + 1;
				break;
			}
		}
		for (int m = str.indexOf(operator) + 1; m < str.length(); m++) {
			// Here we start to move forward from the index of operator sign, and we will
			// continue until we see another operator sign or parenthesis.
			if (!((str.charAt(m) <= '9' && str.charAt(m) >= '0') || str.charAt(m) == '.' || str.charAt(m) == ' ')) {
				// We stored at what point our substring should end.
				end = m;
				break;
			}
		}
		return str.substring(start, end);
	}

	// first_value() is used to extract the first number from operation (opt)
	// string.
	public static String first_value(String opt, char operator) {
		// We will store the value into the string first_value.
		String first_value = "";
		for (int t = 0; t < opt.indexOf(operator); t++) {
			// With this for loop, we examine each character before the operation sign and
			// add the integers to the first_value until opt is completely examined.
			if (opt.charAt(t) != ' ')
				first_value = first_value + opt.charAt(t);
		}
		return first_value;
	}

	// second_value() is used to extract the second number from operation (opt)
	// string.
	public static String second_value(String opt, char operator) {
		// We will store the value into the string second_value.
		String second_value = "";
		for (int l = opt.indexOf(operator) + 1; l < opt.length(); l++) {
			// With this for loop, we examine each character after the operation sign and
			// add the integers to the second_value until opt is completely examined.
			if (opt.charAt(l) != ' ' && opt.charAt(l) != ')') {
				second_value = second_value + opt.charAt(l);
			}
		}
		return second_value;
	}

	// Operation() method performs the requested operation and returns the latest
	// calculation.
	public static String Operation(String opt, String calculation, char operator, String first_value,
			String second_value) {

		// Here, it makes the calculation according to the operator.
		if (operator == '+')
			calculation = calculation.replace(opt,
					Double.toString(Double.parseDouble(first_value) + Double.parseDouble(second_value)));
		if (operator == '-')
			calculation = calculation.replace(opt,
					Double.toString(Double.parseDouble(first_value) - Double.parseDouble(second_value)));
		if (operator == '*')
			calculation = calculation.replace(opt,
					Double.toString(Double.parseDouble(first_value) * Double.parseDouble(second_value)));
		if (operator == '/')
			calculation = calculation.replace(opt,
					Double.toString(Double.parseDouble(first_value) / Double.parseDouble(second_value)));

		return calculation;
	}

}
