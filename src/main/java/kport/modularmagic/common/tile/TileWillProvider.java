package kport.modularmagic.common.tile;

import WayofTime.bloodmagic.demonAura.WorldDemonWillHandler;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import hellfirepvp.modularmachinery.common.machine.IOType;
import hellfirepvp.modularmachinery.common.tiles.base.MachineComponentTile;
import hellfirepvp.modularmachinery.common.tiles.base.TileColorableMachineComponent;
import kport.modularmagic.common.tile.machinecomponent.MachineComponentWillProvider;

import javax.annotation.Nullable;

public abstract class TileWillProvider extends TileColorableMachineComponent implements MachineComponentTile {

    public double getWill(EnumDemonWillType willType) {
        return WorldDemonWillHandler.getCurrentWill(this.world, this.pos, willType);
    }

    public void addWill(double willValue, EnumDemonWillType willType) {
        WorldDemonWillHandler.fillWill(this.world, this.pos, willType, willValue, true);
    }

    public void removeWill(double willValue, EnumDemonWillType willType) {
        WorldDemonWillHandler.drainWill(this.world, this.pos, willType, willValue, true);
    }

    public static class Input extends TileWillProvider {

        @Nullable
        @Override
        public MachineComponentWillProvider provideComponent() {
            return new MachineComponentWillProvider(this, IOType.INPUT);
        }
    }

    public static class Output extends TileWillProvider {

        @Nullable
        @Override
        public MachineComponentWillProvider provideComponent() {
            return new MachineComponentWillProvider(this, IOType.OUTPUT);
        }
    }
}
