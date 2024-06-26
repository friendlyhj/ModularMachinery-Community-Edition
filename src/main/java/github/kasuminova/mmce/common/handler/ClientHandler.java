package github.kasuminova.mmce.common.handler;

import github.kasuminova.mmce.client.preivew.PreviewPanels;
import hellfirepvp.modularmachinery.client.ClientScheduler;
import hellfirepvp.modularmachinery.common.item.ItemBlockController;
import hellfirepvp.modularmachinery.common.lib.ItemsMM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ClientHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onMEItemBusItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (item != ItemsMM.meItemInputBus && item != ItemsMM.meItemOutputBus) {
            return;
        }

        if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("inventory")) {
            event.getToolTip().add(I18n.format("gui.meitembus.nbt_stored"));
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onBlockControllerItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (!(item instanceof ItemBlockController)) {
            return;
        }

        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null && tag.hasKey("owner")) {
            String ownerUUIDStr = tag.getString("owner");
            try {
                UUID ownerUUID = UUID.fromString(ownerUUIDStr);

                EntityPlayerSP player = Minecraft.getMinecraft().player;
                UUID playerUUID = player.getGameProfile().getId();
                if (playerUUID.equals(ownerUUID)) {
                    event.getToolTip().add(I18n.format("tooltip.item.controller.owner.self"));
                } else {
                    event.getToolTip().add(I18n.format("tooltip.item.controller.owner.not_self"));
                }
            } catch (Exception e) {
                event.getToolTip().add(TextFormatting.RED + "NBT read error.");
            }
        }
    }

}
