package github.kasuminova.mmce.common.tile.base;

import appeng.api.AEApi;
import appeng.api.storage.channels.IItemStorageChannel;
import hellfirepvp.modularmachinery.common.util.IOInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MEItemBus extends MEMachineComponent {

    protected final IItemStorageChannel channel = AEApi.instance().storage().getStorageChannel(IItemStorageChannel.class);
    protected IOInventory inventory = buildInventory();

    public abstract IOInventory buildInventory();

    public IOInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        Capability<IItemHandler> cap = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        if (capability == cap) {
            return cap.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readCustomNBT(final NBTTagCompound compound) {
        super.readCustomNBT(compound);

        if (compound.hasKey("inventory")) {
            readInventoryNBT(compound.getCompoundTag("inventory"));
        }
    }

    public void readInventoryNBT(final NBTTagCompound tag) {
        this.inventory = IOInventory.deserialize(this, tag);

        int[] slotIDs = new int[inventory.getSlots()];
        for (int slotID = 0; slotID < slotIDs.length; slotID++) {
            slotIDs[slotID] = slotID;
        }
        inventory.setStackLimit(Integer.MAX_VALUE, slotIDs);
    }

    @Override
    public void writeCustomNBT(final NBTTagCompound compound) {
        super.writeCustomNBT(compound);

        compound.setTag("inventory", inventory.writeNBT());
    }

    public boolean hasItem() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
