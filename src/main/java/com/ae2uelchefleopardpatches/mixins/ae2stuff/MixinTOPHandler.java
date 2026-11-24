package com.ae2uelchefleopardpatches.mixins.ae2stuff;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import appeng.api.networking.IGridConnection;
import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.TextStyleClass;
import net.bdew.ae2stuff.machines.wireless.TileWireless;
import net.bdew.ae2stuff.machines.wireless.TileWirelessHub;
import net.bdew.ae2stuff.top.TOPHandler$;

@Mixin(value = TOPHandler$.class, remap = false)
public abstract class MixinTOPHandler {

    @WrapOperation(method = "addProbeInfo", at = @At(value = "INVOKE",
            target = "Lappeng/core/AEConfig;isFeatureEnabled(Lappeng/core/features/AEFeature;)Z"))
    private boolean chefleopard$showHubChannels(AEConfig config, AEFeature feature, Operation<Boolean> original,
            @Local(argsOnly = true) IProbeInfo probeInfo, @Local(argsOnly = true) World world,
            @Local(argsOnly = true) IProbeHitData data) {
        boolean enabled = original.call(config, feature);
        if (!enabled || feature != AEFeature.CHANNELS) {
            return enabled;
        }
        TileEntity tile = world.getTileEntity(data.getPos());
        if (!(tile instanceof TileWirelessHub)) {
            return true;
        }
        int usedChannels = 0;
        for (TileWireless connector : ((TileWirelessHub) tile).connectionsList()) {
            IGridConnection connection = connector.connection();
            if (connection != null) {
                usedChannels += connection.getUsedChannels();
            }
        }
        probeInfo.text(TextStyleClass.INFO + "{*ae2stuff.top.wireless.channels*}" + " " + usedChannels);
        return false;
    }
}
