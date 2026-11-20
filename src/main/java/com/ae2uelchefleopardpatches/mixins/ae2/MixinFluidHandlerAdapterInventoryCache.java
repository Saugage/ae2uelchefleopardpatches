package com.ae2uelchefleopardpatches.mixins.ae2;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "appeng.fluids.parts.FluidHandlerAdapter$InventoryCache", remap = false)
public abstract class MixinFluidHandlerAdapterInventoryCache {

    @Redirect(method = "update",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraftforge/fluids/capability/IFluidHandler;drain(Lnet/minecraftforge/fluids/FluidStack;Z)Lnet/minecraftforge/fluids/FluidStack;"))
    private FluidStack chefleopard$skipEmptyTankDrain(IFluidHandler handler, FluidStack contents, boolean doDrain) {
        return contents == null ? null : handler.drain(contents, doDrain);
    }
}
