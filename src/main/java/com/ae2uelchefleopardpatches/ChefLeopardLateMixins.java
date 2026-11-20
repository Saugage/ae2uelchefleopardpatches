package com.ae2uelchefleopardpatches;

import java.util.Collections;
import java.util.List;

import zone.rong.mixinbooter.ILateMixinLoader;

public class ChefLeopardLateMixins implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins." + Tags.MODID + ".json");
    }
}
