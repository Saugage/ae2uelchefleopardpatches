package appeng.util.item;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

/**
 * Lives in {@code appeng.util.item} so it can access the package-private {@link AESharedItemStack}. Callers must hold
 * the {@code AEItemStackRegistry} class lock.
 */
public final class ChefLeopardItemStackRegistry {

    private static final WeakHashMap<AESharedItemStack, WeakReference<AESharedItemStack>> REGISTRY = new WeakHashMap<>();

    private ChefLeopardItemStackRegistry() {}

    /**
     * Returns an {@link AESharedItemStack}, typed as {@link Object} so callers outside this package can use it.
     */
    public static Object getRegisteredStack(final @Nonnull ItemStack itemStack) {
        final int oldStackSize = itemStack.getCount();
        itemStack.setCount(1);

        try {
            AESharedItemStack search = new AESharedItemStack(itemStack);
            WeakReference<AESharedItemStack> weak = REGISTRY.get(search);
            AESharedItemStack ret = weak != null ? weak.get() : null;

            if (ret == null) {
                ret = new AESharedItemStack(itemStack.copy());
                REGISTRY.put(ret, new WeakReference<>(ret));
            }

            return ret;
        } finally {
            itemStack.setCount(oldStackSize);
        }
    }
}
