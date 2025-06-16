package lazyae2tweaks.mixin;

import io.github.phantamanta44.threng.tile.base.TileSimpleProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(targets = "io.github.phantamanta44.threng.tile.base.TileSimpleProcessor$UpgradeSlot")
public abstract class MixinSlotLimit {

    /**
     * Overwrites the default slot limit for the upgrade slot to 64.
     * @return Always returns 64.
     */
    @Overwrite
    public int getSlotLimit() {
        return 64;
    }
}
