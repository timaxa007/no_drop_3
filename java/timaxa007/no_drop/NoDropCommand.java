package timaxa007.no_drop;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

/**copy - net.minecraft.command.CommandGive</br>remader - timaxa007**/
public class NoDropCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "givenodrop";
	}

	/**Return the required permission level for this command.**/
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(ICommandSender ics) {
		return "commands.givenodrop.usage";
	}

	@Override
	public void processCommand(ICommandSender ics, String[] args) {
		if (args.length < 2) throw new WrongUsageException(getCommandUsage(ics), new Object[0]);
		else {
			EntityPlayerMP player = getPlayer(ics, args[0]);

			Item item = getItemByText(ics, args[1]);
			int count = 1;
			int metadata = 0;
			if (args.length >= 3) count = parseIntBounded(ics, args[2], 1, 64);
			if (args.length >= 4) metadata = parseInt(ics, args[3]);

			ItemStack itemstack = new ItemStack(item, count, metadata);

			if (args.length >= 5) {
				String s = func_147178_a(ics, args, 4).getUnformattedText();

				try {
					NBTBase nbtbase = JsonToNBT.func_150315_a(s);

					if (!(nbtbase instanceof NBTTagCompound)) {
						func_152373_a(ics, this, "commands.give.tagError", new Object[] {"Not a valid tag"});
						return;
					}

					itemstack.setTagCompound((NBTTagCompound)nbtbase);
				} catch (NBTException nbtexception) {
					func_152373_a(ics, this, "commands.give.tagError", new Object[] {nbtexception.getMessage()});
					return;
				}
			}

			if (!itemstack.hasTagCompound()) itemstack.setTagCompound(new NBTTagCompound());

			itemstack.getTagCompound().setBoolean("NoDrop", true);

			if (!player.inventory.addItemStackToInventory(itemstack)) {
				EntityItem entityitem = player.dropPlayerItemWithRandomChoice(itemstack, false);
				entityitem.delayBeforeCanPickup = 0;
				entityitem.func_145797_a(player.getCommandSenderName());
			}
			func_152373_a(ics, this, "commands.give.success", new Object[] {itemstack.func_151000_E(), Integer.valueOf(count), player.getCommandSenderName()});
		}
	}

	/**Adds the strings available in this command to the given list of tab completion options.**/
	@Override
	public List addTabCompletionOptions(ICommandSender ics, String[] args) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : (args.length == 2 ? getListOfStringsFromIterableMatchingLastWord(args, Item.itemRegistry.getKeys()) : null);
	}

	/**Return whether the specified command parameter index is a username parameter.**/
	@Override
	public boolean isUsernameIndex(String[] args, int id) {
		return id == 0;
	}

}
