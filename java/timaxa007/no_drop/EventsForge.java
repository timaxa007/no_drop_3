package timaxa007.no_drop;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EventsForge {

	@SubscribeEvent
	public void doPlayerDropsEvent(PlayerDropsEvent event) {
		if (event.drops.isEmpty()) return;

		final ArrayList<ItemStack> save_drops = new ArrayList<ItemStack>();

		for (int i = 0; i < event.drops.size(); ++i) {
			EntityItem ei = event.drops.get(i);
			ItemStack itemStack = ei.getEntityItem();
			if (!itemStack.hasTagCompound()) continue;
			if (itemStack.getTagCompound().hasKey("NoDrop")) {
				save_drops.add(itemStack);
				event.drops.remove(i--);
			}
		}

		if (save_drops.isEmpty()) return;
		NBTTagList list = new NBTTagList();
		for (ItemStack item : save_drops) {
			NBTTagCompound compound_item = new NBTTagCompound();
			item.writeToNBT(compound_item);
			list.appendTag(compound_item);
		}
		event.entityPlayer.getEntityData().setTag("NoDrops", list);

	}

	@SubscribeEvent
	public void doPlayerEventClone(PlayerEvent.Clone event) {
		if (!event.original.getEntityData().hasKey("NoDrops", NBT.TAG_LIST)) return;

		NBTTagList list = event.original.getEntityData().getTagList("NoDrops", NBT.TAG_COMPOUND);
		event.entityPlayer.getEntityData().setTag("NoDrops", list);
	}

	@SubscribeEvent
	public void doItemTooltipEvent(ItemTooltipEvent event) {
		if (event.itemStack == null) return;
		if (!event.itemStack.hasTagCompound()) return;
		if (event.itemStack.getTagCompound().hasKey("NoDrop"))
			event.toolTip.add("No Drop");
	}

}
