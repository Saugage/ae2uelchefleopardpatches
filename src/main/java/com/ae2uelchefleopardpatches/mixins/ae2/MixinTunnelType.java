package com.ae2uelchefleopardpatches.mixins.ae2;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.api.AEApi;
import appeng.api.config.TunnelType;

@Mixin(value = TunnelType.class, remap = false)
public abstract class MixinTunnelType {

    @Shadow
    private Supplier<ItemStack> partItemStackSupplier;

    @Inject(method = "getPartItemStack", at = @At("HEAD"))
    private void chefleopard$useFluidTunnelPart(CallbackInfoReturnable<ItemStack> cir) {
        if ((Object) this == TunnelType.FLUID && this.partItemStackSupplier != null) {
            this.partItemStackSupplier = () -> AEApi.instance().definitions().parts().p2PTunnelFluids()
                    .maybeStack(1).orElse(ItemStack.EMPTY);
        }
    }
}
