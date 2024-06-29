package de.sivery.sculkvial.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SculkVial extends Item {
    public SculkVial(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);


        if (user.isSneaking()) {

            // Fill XP into vial

            @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
            NbtCompound compound;

            int xp_saved;

            if (nbtComponent != null && nbtComponent.contains("sculkvial.experience")) {

                compound = nbtComponent.copyNbt();

                xp_saved = compound.getInt("sculkvial.experience");

            } else {
                xp_saved = 0;
            }

            int xp_player = user.totalExperience;

            int xp_toTake = Math.min(Math.max(1395-xp_saved, 0), xp_player);

            stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentNbt -> currentNbt.putInt("sculkvial.experience", xp_saved + xp_toTake)));

            user.addExperience(-xp_toTake);

            return TypedActionResult.pass(user.getStackInHand(hand));
        } else {

            // Remove XP from vial

            @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

            if (nbtComponent != null && nbtComponent.contains("sculkvial.experience")) {

                NbtCompound compound = nbtComponent.copyNbt();

                int xp_saved = compound.getInt("sculkvial.experience");


                stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentNbt -> currentNbt.putInt("sculkvial.experience", 0)));

                user.addExperience(xp_saved);
            }


            return TypedActionResult.success(user.getStackInHand(hand));
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {

        @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

        return nbtComponent != null && nbtComponent.contains("sculkvial.experience") && nbtComponent.copyNbt().getInt("sculkvial.experience") != 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        int xp_saved = 0;

        @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

        if (nbtComponent != null) {

            NbtCompound compound = nbtComponent.copyNbt();

            if (nbtComponent.contains("sculkvial.experience")) {

                xp_saved = compound.getInt("sculkvial.experience");
            }
        }


        tooltip.add(Text.translatable("tooltip.sculk-vial.sculk_vial").append(xp_saved + "/1395"));



    }
}
