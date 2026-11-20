package com.ae2uelchefleopardpatches.mixins.ae2;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import appeng.util.prioritylist.IPartitionList;
import appeng.util.prioritylist.MergedPriorityList;

@Mixin(value = MergedPriorityList.class, remap = false)
public abstract class MixinMergedPriorityList {

    @Inject(method = "addNewList", at = @At("HEAD"), cancellable = true)
    private void chefleopard$skipEmptyList(IPartitionList<?> list, boolean isWhitelist, CallbackInfo ci) {
        if (list.isEmpty()) {
            ci.cancel();
        }
    }
}
