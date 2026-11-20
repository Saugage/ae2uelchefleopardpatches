package com.ae2uelchefleopardpatches.mixins.ae2;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.ae2uelchefleopardpatches.util.EmptyPartitionList;

import appeng.me.storage.MEInventoryHandler;
import appeng.util.prioritylist.DefaultPriorityList;

@Mixin(value = MEInventoryHandler.class, remap = false)
public abstract class MixinMEInventoryHandler {

    @Redirect(method = "<init>",
            at = @At(value = "NEW", target = "appeng/util/prioritylist/DefaultPriorityList"))
    private DefaultPriorityList<?> chefleopard$sharedEmptyList() {
        return EmptyPartitionList.get();
    }
}
