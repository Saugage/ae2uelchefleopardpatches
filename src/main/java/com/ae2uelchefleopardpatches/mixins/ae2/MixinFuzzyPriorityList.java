package com.ae2uelchefleopardpatches.mixins.ae2;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import appeng.api.config.FuzzyMode;
import appeng.api.storage.data.IItemList;
import appeng.util.prioritylist.FuzzyPriorityList;

@Mixin(value = FuzzyPriorityList.class, remap = false)
public abstract class MixinFuzzyPriorityList {

    @Unique
    private boolean chefleopard$isEmpty;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void chefleopard$cacheIsEmpty(IItemList<?> in, FuzzyMode mode, CallbackInfo ci) {
        this.chefleopard$isEmpty = in.isEmpty();
    }

    /**
     * @author ChefLeopard
     * @reason Port of MasonD 75fd8e4: IItemList.isEmpty() builds an iterator on every call, and this is hit on every
     *         insert/extract check in MEInventoryHandler. The list is not modified after wrapping, so cache it.
     */
    @Overwrite
    public boolean isEmpty() {
        return this.chefleopard$isEmpty;
    }
}
