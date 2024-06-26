package github.kasuminova.mmce.client.gui.slot;


import appeng.container.slot.AppEngSlot;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * A proxy for a slot that will always return an itemstack with size 1, if there is an item in the slot.
 * Used to prevent the default item count from rendering.
 */
public class Size1Slot extends AppEngSlot {

    private final AppEngSlot delegate;

    public Size1Slot(AppEngSlot delegate) {
        super(delegate.getItemHandler(), delegate.getSlotIndex(), delegate.xPos, delegate.yPos);
        this.delegate = delegate;
    }

    @Override
    @Nonnull
    public ItemStack getStack() {
        ItemStack orgStack = this.delegate.getStack();
        if (!orgStack.isEmpty()) {
            ItemStack modifiedStack = orgStack.copy();
            modifiedStack.setCount(1);
            return modifiedStack;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean getHasStack() {
        return this.delegate.getHasStack();
    }

    @Override
    public boolean isHere(@Nonnull IInventory inv, int slotIn) {
        return this.delegate.isHere(inv, slotIn);
    }

    @Override
    public int getSlotStackLimit() {
        return this.delegate.getSlotStackLimit();
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return this.delegate.getItemStackLimit(stack);
    }

    @Override
    @Nullable
    @SideOnly(Side.CLIENT)
    public String getSlotTexture() {
        return this.delegate.getSlotTexture();
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return this.delegate.canTakeStack(playerIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isEnabled() {
        return this.delegate.isEnabled();
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getBackgroundLocation() {
        return this.delegate.getBackgroundLocation();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getBackgroundSprite() {
        return this.delegate.getBackgroundSprite();
    }

    @Override
    public int getSlotIndex() {
        return this.delegate.getSlotIndex();
    }

    @Override
    public boolean isSameInventory(Slot other) {
        return this.delegate.isSameInventory(other);
    }
}
