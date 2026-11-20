package com.ae2uelchefleopardpatches.mixins.ae2;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import appeng.util.item.AEItemStackRegistry;
import appeng.util.item.ChefLeopardItemStackRegistry;

@Mixin(value = AEItemStackRegistry.class, remap = false)
public abstract class MixinAEItemStackRegistry {

    @Inject(method = "getRegisteredStack", at = @At("HEAD"), cancellable = true)
    private static void chefleopard$equalityRegistry(ItemStack itemStack, CallbackInfoReturnable<Object> cir) {
        if (itemStack.isEmpty()) {
            throw new IllegalArgumentException("stack cannot be empty");
        }
        cir.setReturnValue(ChefLeopardItemStackRegistry.getRegisteredStack(itemStack));
    }
}
