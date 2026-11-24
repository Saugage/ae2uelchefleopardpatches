package com.ae2uelchefleopardpatches;

import java.io.File;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

/**
 * Read directly from disk instead of through Forge's {@code @Config}, because mixins are selected before mods are
 * constructed.
 */
public final class ChefLeopardConfig {

    private static final String CATEGORY = "fixes";

    public static boolean fixFluidStorageBusResetSpam = true;
    public static boolean fixFluidStorageBusReconnect = true;
    public static boolean optimizePartitionLists = true;
    public static boolean fixFluidP2PAttunement = true;
    public static boolean fixFluidBusEmptyDrawerCrash = true;
    public static boolean fixItemStackRegistryCollisions = true;
    public static boolean fixAE2StuffHubTOPChannels = true;

    private static boolean loaded;

    private ChefLeopardConfig() {}

    public static synchronized void load() {
        if (loaded) {
            return;
        }
        loaded = true;

        Configuration config = new Configuration(new File(Launch.minecraftHome, "config/" + Tags.MODID + ".cfg"));
        config.load();
        config.setCategoryComment(CATEGORY,
                "Toggle AE2UEL fixes and optimizations.");

        fixFluidStorageBusResetSpam = config.getBoolean("fixFluidStorageBusResetSpam", CATEGORY, true,
                "Fix fluid storage bus reset spam (AE2-UEL PR 588). Keep fixFluidStorageBusReconnect enabled with"
                        + " this, or the bus may not reconnect to a replaced fluid interface.");
        fixFluidStorageBusReconnect = config.getBoolean("fixFluidStorageBusReconnect", CATEGORY, true,
                "Fix the fluid storage bus not reconnecting after a fluid interface is broken and replaced.");
        optimizePartitionLists = config.getBoolean("optimizePartitionLists", CATEGORY, true,
                "Cache the empty state of partition lists to avoid a semi-expensive isEmpty on every insert/extract" + " check.");
        fixFluidP2PAttunement = config.getBoolean("fixFluidP2PAttunement", CATEGORY, true,
                "Fix attuning a P2P tunnel with a fluid container producing an FE P2P.");
        fixFluidBusEmptyDrawerCrash = config.getBoolean("fixFluidBusEmptyDrawerCrash", CATEGORY, true,
                "Fix a crash when a fluid storage bus in Extractable Only mode is placed on an empty fluid drawer.");
        fixItemStackRegistryCollisions = config.getBoolean("fixItemStackRegistryCollisions", CATEGORY, true,
                "Revert AE2UEL 724f3ba: key shared item stacks by equality instead of hash code, so items with"
                        + " colliding hashes are no longer merged.");
        fixAE2StuffHubTOPChannels = config.getBoolean("fixAE2StuffHubTOPChannels", CATEGORY, true,
                "Fix the AE2 Stuff wireless hub TOP display showing the connection count as the channels used."
                        + " Only applies when AE2 Stuff Unofficial is installed.");

        if (config.hasChanged()) {
            config.save();
        }
    }
}
