package net.genachec.makitasmod.datagen;

import net.genachec.makitasmod.block.MakitasModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MakitasModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public MakitasModRecipeProvider(PackOutput pOut) {
        super(pOut);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {

        pIronMakitaBlockCrafting(pWriter);

        pUpgradeMakita(pWriter,
                Items.GOLD_INGOT,
                MakitasModBlocks.IRON_MAKITA_BLOCK.get(),
                MakitasModBlocks.GOLD_MAKITA_BLOCK.get());

        pUpgradeMakita(pWriter,
                Items.DIAMOND,
                MakitasModBlocks.GOLD_MAKITA_BLOCK.get(),
                MakitasModBlocks.DIAMOND_MAKITA_BLOCK.get());

        pEndMakitaBlockCrafting(pWriter);
    }


    protected static void pUpgradeMakita(@NotNull Consumer<FinishedRecipe> pWriter,
                                         Item ingredient,
                                         Block makitaToUpgrade,
                                         Block makitaUpgraded) {
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.MISC,
                        makitaUpgraded)
                .pattern("UUU")
                .pattern("U#U")
                .pattern("UUU")
                .define('U', ingredient)
                .define('#', makitaToUpgrade)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(pWriter);
    }

    protected static void pIronMakitaBlockCrafting(@NotNull Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.MISC,
                        MakitasModBlocks.IRON_MAKITA_BLOCK.get())
                .pattern(" C ")
                .pattern(" #C")
                .pattern("III")
                .define('C', Items.CYAN_CONCRETE)
                .define('#', Items.STONECUTTER)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);
    }

    protected static void pEndMakitaBlockCrafting(@NotNull Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(
                        RecipeCategory.MISC,
                        MakitasModBlocks.END_MAKITA_BLOCK.get()) // Changed from IRON_MAKITA_BLOCK
                .pattern("NEN")
                .pattern("N#N")
                .pattern("NSN")
                .define('N', Items.NETHERITE_INGOT)
                .define('S', Items.NETHER_STAR)
                .define('E', Items.DRAGON_EGG)
                .define('#', MakitasModBlocks.DIAMOND_MAKITA_BLOCK.get())
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(pWriter);
    }
}
