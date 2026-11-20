package com.ae2uelchefleopardpatches;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class ChefLeopardMixinPlugin implements IMixinConfigPlugin {

    private static final Logger LOGGER = LogManager.getLogger(Tags.MODID);
    private static final String MIXIN_PACKAGE = "com.ae2uelchefleopardpatches.mixins.";

    private static final class Toggle {

        final String key;
        final BooleanSupplier enabled;

        Toggle(String key, BooleanSupplier enabled) {
            this.key = key;
            this.enabled = enabled;
        }
    }

    private final Map<String, Toggle> toggles = new HashMap<>();

    private void register(String key, BooleanSupplier enabled, String... mixins) {
        Toggle toggle = new Toggle(key, enabled);
        for (String mixin : mixins) {
            this.toggles.put(mixin, toggle);
        }
    }

    @Override
    public void onLoad(String mixinPackage) {
        ChefLeopardConfig.load();

        register("fixFluidStorageBusResetSpam", () -> ChefLeopardConfig.fixFluidStorageBusResetSpam,
                "ae2.MixinPartFluidStorageBusResetSpam");
        register("fixFluidStorageBusReconnect", () -> ChefLeopardConfig.fixFluidStorageBusReconnect,
                "ae2.MixinPartFluidStorageBusHandlerHash");
        register("optimizePartitionLists", () -> ChefLeopardConfig.optimizePartitionLists,
                "ae2.MixinPrecisePriorityList", "ae2.MixinFuzzyPriorityList", "ae2.MixinMergedPriorityList",
                "ae2.MixinMEInventoryHandler");
        register("fixFluidP2PAttunement", () -> ChefLeopardConfig.fixFluidP2PAttunement,
                "ae2.MixinTunnelType");
        register("fixFluidBusEmptyDrawerCrash", () -> ChefLeopardConfig.fixFluidBusEmptyDrawerCrash,
                "ae2.MixinFluidHandlerAdapterInventoryCache");
        register("fixItemStackRegistryCollisions", () -> ChefLeopardConfig.fixItemStackRegistryCollisions,
                "ae2.MixinAEItemStackRegistry");
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String name = mixinClassName.startsWith(MIXIN_PACKAGE) ? mixinClassName.substring(MIXIN_PACKAGE.length())
                : mixinClassName;
        Toggle toggle = this.toggles.get(name);
        if (toggle == null || toggle.enabled.getAsBoolean()) {
            return true;
        }
        LOGGER.info("Fix '{}' disabled by config, skipping {}", toggle.key, name);
        return false;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName,
            IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName,
            IMixinInfo mixinInfo) {}
}
