package lazyae2tweaks.mixin;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.recipe.IRecipeList;
import io.github.phantamanta44.libnine.util.helper.ItemUtils;
import io.github.phantamanta44.threng.recipe.EtchRecipe;
import io.github.phantamanta44.threng.recipe.ThrEngRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrEngRecipes.class)
public class MixinThrEngRecipes {

    @Inject(method = "addRecipes", at = @At("TAIL"), remap = false)
    private static void injectCustomRecipes(CallbackInfo ci) {
        // CrazyAE が導入されているかチェック
        if (!Loader.isModLoaded("crazyae")) {
            return;  // 未導入なら処理しない
        }

        // EtchRecipe レシピリスト取得
        IRecipeList etchRecipes = LibNine.PROXY.getRecipeManager().getRecipeList(EtchRecipe.class);
        IDefinitions defs = AEApi.instance().definitions();

        // アイテムを取得
        Item crazyMaterial = Item.getByNameOrId("crazyae:material");
        Item crazyFluixBlock = Item.getByNameOrId("crazyae:fluixilized_block");

        if (crazyMaterial == null || crazyFluixBlock == null) {
            return;  // 必要なアイテムが見つからない場合は何もしない
        }

        // Quantum Processor のレシピ
        defs.materials().logicProcessor().maybeStack(1).ifPresent(lp -> {
            defs.materials().matterBall().maybeStack(1).ifPresent(mb -> {
                defs.materials().engProcessor().maybeStack(1).ifPresent(ep -> {
                    etchRecipes.add(new EtchRecipe(
                            ItemUtils.matchesWithWildcard(lp),
                            ItemUtils.matchesWithWildcard(ep),
                            ItemUtils.matchesWithWildcard(mb),
                            new ItemStack(crazyMaterial, 1, 16)
                    ));
                });
            });
        });

        // Energy Processor のレシピ
        defs.materials().matterBall().maybeStack(1).ifPresent(mb -> {
            etchRecipes.add(new EtchRecipe(
                    ItemUtils.matchesWithWildcard(new ItemStack(crazyMaterial, 1, 16)),
                    ItemUtils.matchesWithWildcard(new ItemStack(crazyFluixBlock, 1)),
                    ItemUtils.matchesWithWildcard(mb),
                    new ItemStack(crazyMaterial, 1, 48)
            ));
        });
    }
}
