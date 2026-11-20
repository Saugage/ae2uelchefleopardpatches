package com.ae2uelchefleopardpatches.mixins.ae2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import appeng.api.parts.IPart;
import appeng.api.util.AEPartLocation;
import appeng.fluids.parts.PartFluidInterface;
import appeng.fluids.parts.PartFluidStorageBus;
import appeng.fluids.tile.TileFluidInterface;
import appeng.tile.networking.TileCableBus;

@Mixin(value = PartFluidStorageBus.class, remap = false)
public abstract class MixinPartFluidStorageBusResetSpam {

    @Shadow
    private int handlerHash;

    @Shadow
    protected abstract int createHandlerHash(TileEntity target);

    @Shadow
    protected abstract void resetCache(boolean fullReset);

    @Shadow
    protected abstract void resetCache();

    /**
     * @author ChefLeopard
     * @reason Port of AE2-UEL PR 588 (e4bffc7): only reset fluid interface handlers when the handler hash changed.
     */
    @Overwrite
    public void onNeighborChanged(IBlockAccess w, BlockPos pos, BlockPos neighbor) {
        AEPartLocation side = ((PartFluidStorageBus) (Object) this).getSide();
        if (pos.offset(side.getFacing()).equals(neighbor)) {

            final TileEntity te = w.getTileEntity(neighbor);

            // In case the TE was destroyed, we have to do a full reset immediately.
            if (te instanceof TileCableBus) {
                IPart iPart = ((TileCableBus) te).getPart(side.getOpposite());
                if (iPart == null) {
                    this.resetCache(true);
                    this.resetCache();
                } else if (iPart instanceof PartFluidInterface) {
                    if (this.createHandlerHash(te) != this.handlerHash) {
                        this.resetCache(true);
                        this.resetCache();
                    }
                }
            } else if (te == null) {
                this.resetCache(true);
                this.resetCache();
            } else if (te instanceof TileFluidInterface) {
                if (this.createHandlerHash(te) != this.handlerHash) {
                    this.resetCache(true);
                    this.resetCache();
                }
            } else {
                this.resetCache(false);
            }
        }
    }
}
