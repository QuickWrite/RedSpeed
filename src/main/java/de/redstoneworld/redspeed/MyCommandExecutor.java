package de.redstoneworld.redspeed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyCommandExecutor implements CommandExecutor {

	protected final RedSpeed plugin;

	public MyCommandExecutor(RedSpeed plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (!(sender instanceof Player)) {
			sender.sendMessage(plugin.getLang("prefix") + plugin.getLang("onlyIngame"));
			return false;
		}

		player = (Player) sender;

		/**
		 * Shows / edits the walk speed of the player.
		 * 
		 * ingame command syntax: /redwalkspeed [walk-speed | 'back' | player]
		 */

		if (cmd.getName().equalsIgnoreCase("redwalkspeed")) {

			// without arguments --> syntax help messages
			if (args.length == 0) {
				WalkSpeedCommand walkSpeedCmd = new WalkSpeedCommand(plugin, player);
				walkSpeedCmd.sendWalkSpeedMsg();
				return true;
			}

			if (args.length == 1) {

				// e.g. "/wspeed back"
				if (args[0].equalsIgnoreCase("back") || args[0].equalsIgnoreCase("default")) {
					WalkSpeedCommand walkSpeedCmd = new WalkSpeedCommand(plugin, player);
					walkSpeedCmd.setDefaultSpeed();
					return true;
				}

				else {
					// e.g. "/wspeed 1"
					if (matchSpeedArgument(args[0])) {
						String matchedString = (args[0]).replaceFirst(",", ".");
						float speed = Float.parseFloat(matchedString);

						WalkSpeedCommand walkSpeedCmd = new WalkSpeedCommand(plugin, player);
						walkSpeedCmd.setSpecificSpeed(speed);
						return true;

					} else {
						// e.g. "/wspeed TestUser"
						// if x is a string (player name) = admin commands
						WalkSpeedCommand walkSpeedCmd = new WalkSpeedCommand(plugin, player, args[0]);
						walkSpeedCmd.sendWalkSpeedMsgOther();
						return true;
					}
				}
			}

			// = admin commands :
			if (args.length == 2) {

				// e.g. "/wspeed back TestUser"
				if (args[0].equalsIgnoreCase("back") || args[0].equalsIgnoreCase("default")) {
					WalkSpeedCommand walkSpeedCmd = new WalkSpeedCommand(plugin, player, args[1]);
					walkSpeedCmd.setDefaultSpeedOther();
					return true;
				}

				else {
					// e.g. "/wspeed 1 TestUser"
					if (matchSpeedArgument(args[0])) {
						String matchedString = (args[0]).replaceFirst(",", ".");
						float speed = Float.parseFloat(matchedString);

						WalkSpeedCommand walkSpeedCmd = new WalkSpeedCommand(plugin, player, args[1]);
						walkSpeedCmd.setSpecificSpeedOther(speed);
						return true;

					} else {
						player.sendMessage(plugin.getLang("prefix") + plugin.getLang("syntaxError.wspeed"));
						return true;
					}
				}
			}

			player.sendMessage(plugin.getLang("prefix") + plugin.getLang("syntaxError.wspeed"));
			return true;

		}

		/**
		 * Shows / edits the fly speed of the player.
		 * 
		 * ingame command syntax: /redflyspeed [fly-speed | 'back' | player]
		 */

		if (cmd.getName().equalsIgnoreCase("redflyspeed")) {

			// without arguments --> syntax help messages
			if (args.length == 0) {
				FlySpeedCommand flySpeedCmd = new FlySpeedCommand(plugin, player);
				flySpeedCmd.sendFlySpeedMsg();
				return true;
			}

			if (args.length == 1) {

				// e.g. "/fspeed back"
				if (args[0].equalsIgnoreCase("back") || args[0].equalsIgnoreCase("default")) {
					FlySpeedCommand flySpeedCmd = new FlySpeedCommand(plugin, player);
					flySpeedCmd.setDefaultSpeed();
					return true;
				}

				else {
					// e.g. "/fspeed 1"
					if (matchSpeedArgument(args[0])) {
						String matchedString = (args[0]).replaceFirst(",", ".");
						float speed = Float.parseFloat(matchedString);

						FlySpeedCommand flySpeedCmd = new FlySpeedCommand(plugin, player);
						flySpeedCmd.setSpecificSpeed(speed);
						return true;

					} else {
						// "/fspeed TestUser"
						// if x is a string (player name) = admin commands
						FlySpeedCommand flySpeedCmd = new FlySpeedCommand(plugin, player, args[0]);
						flySpeedCmd.sendFlySpeedMsgOther();
						return true;
					}
				}
			}

			// = admin commands :
			if (args.length == 2) {

				// "/fspeed back TestUser"
				if (args[0].equalsIgnoreCase("back") || args[0].equalsIgnoreCase("default")) {
					FlySpeedCommand flySpeedCmd = new FlySpeedCommand(plugin, player, args[1]);
					flySpeedCmd.setDefaultSpeedOther();
					return true;
				}

				else {
					// e.g. "/fspeed 1 TestUser"
					if (matchSpeedArgument(args[0])) {
						String matchedString = (args[0]).replaceFirst(",", ".");
						float speed = Float.parseFloat(matchedString);

						FlySpeedCommand flySpeedCmd = new FlySpeedCommand(plugin, player, args[1]);
						flySpeedCmd.setSpecificSpeedOther(speed);
						return true;

					} else {
						player.sendMessage(plugin.getLang("prefix") + plugin.getLang("syntaxError.fspeed"));
						return true;
					}
				}
			}

			player.sendMessage(plugin.getLang("prefix") + plugin.getLang("syntaxError.fspeed"));
			return true;

		}

		return false;

	}

	/**
	 * Returns the validation of the command argument if it is a <speed> or
	 * <playername> input.
	 * 
	 * @return 'true' if the input is a rationale number
	 */
	public boolean matchSpeedArgument(String cmdInput) {
		if (cmdInput.matches("^[-]?[0-9]+([\\.,][0-9])?[0-9]*$")) {
			return true;
		}
		return false;
	}

}
