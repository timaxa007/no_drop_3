package timaxa007.no_drop;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class EventsFML {

	@SubscribeEvent
	public void doPlayerEventPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
		if (!event.player.getEntityData().hasKey("NoDrops", NBT.TAG_LIST)) return;
		NBTTagList list = event.player.getEntityData().getTagList("NoDrops", NBT.TAG_COMPOUND);

		for (int i = 0; i < list.tagCount(); ++i) {
			ItemStack itemStack = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
			if (!event.player.inventory.addItemStackToInventory(itemStack))
				event.player.dropPlayerItemWithRandomChoice(itemStack, false);
		}

		event.player.getEntityData().removeTag("NoDrops");

	}

}
