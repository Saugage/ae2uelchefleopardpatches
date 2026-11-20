package com.ae2uelchefleopardpatches.mixins.ae2;

import java.util.Objects;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import appeng.api.AEApi;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.IStorageMonitorable;
import appeng.api.storage.IStorageMonitorableAccessor;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.util.AEPartLocation;
import appeng.capabilities.Capabilities;
import appeng.fluids.parts.PartFluidStorageBus;

@Mixin(value = PartFluidStorageBus.class, remap = false)
public abstract class MixinPartFluidStorageBusHandlerHash {

    @Shadow
    private IActionSource source;

    /**
     * @author ChefLeopard
     * @reason Match item storage bus hashing: include the resolved inventory so a fluid interface that was not yet
     *         accessible (break-and-replace) produces a new hash once its grid is ready.
     */
    @Overwrite
    private int createHandlerHash(TileEntity target) {
        if (target == null) {
            return 0;
        }

        AEPartLocation side = ((PartFluidStorageBus) (Object) this).getSide();
        final EnumFacing targetSide = side.getFacing().getOpposite();

        if (target.hasCapability(Capabilities.STORAGE_MONITORABLE_ACCESSOR, targetSide)) {
            IStorageMonitorableAccessor accessor = target.getCapability(Capabilities.STORAGE_MONITORABLE_ACCESSOR,
                    targetSide);
            if (accessor != null) {
                IStorageMonitorable inventory = accessor.getInventory(this.source);
                if (inventory != null) {
                    return Objects.hash(target, inventory
                            .getInventory(AEApi.instance().storage().getStorageChannel(IFluidStorageChannel.class)));
                }
            }
            return Objects.hash(target, target.getCapability(Capabilities.STORAGE_MONITORABLE_ACCESSOR, targetSide));
        }

        final IFluidHandler fluidHandler = target.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
                targetSide);

        if (fluidHandler != null) {
            return Objects.hash(target, fluidHandler, fluidHandler.getTankProperties().length);
        }

        return 0;
    }
}
