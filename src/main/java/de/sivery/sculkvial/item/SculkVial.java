package de.sivery.sculkvial.item;


import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.PlainTextContent;
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

            @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

            NbtCompound requiredNbt = new NbtCompound();
            //nbtComponent.put

            if (nbtComponent != null && nbtComponent.contains("sculkvial.experience")) {

                NbtCompound compound = nbtComponent.copyNbt();

                int xp_saved = compound.getInt("sculkvial.experience");
                int xp_player = user.totalExperience;

                int xp_toTake = Math.min(Math.max(1395-xp_saved, 0), xp_player);

                stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentNbt -> {
                    currentNbt.putInt("sculkvial.experience", xp_saved + xp_toTake);
                }));

                // stack.set(DataComponentTypes.CUSTOM_DATA nbtComponent)

                user.addExperience(-xp_toTake);

            } else {

                NbtCompound compound = new NbtCompound();

                int xp_saved = 0;
                int xp_player = user.totalExperience;

                int xp_toTake = Math.min(Math.max(1395-xp_saved, 0), xp_player);

                stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentNbt -> {
                    currentNbt.putInt("sculkvial.experience", xp_saved + xp_toTake);
                }));

                // stack.set(DataComponentTypes.CUSTOM_DATA nbtComponent)

                user.addExperience(-xp_toTake);
            }


            // Remove XP from player

            return TypedActionResult.pass(user.getStackInHand(hand));
        } else {
            @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

            if (nbtComponent != null && nbtComponent.contains("sculkvial.experience")) {

                NbtCompound compound = nbtComponent.copyNbt();

                int xp_saved = compound.getInt("sculkvial.experience");


                stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentNbt -> {
                    currentNbt.putInt("sculkvial.experience", 0);
                }));

                user.addExperience(xp_saved);
            }


            return TypedActionResult.success(user.getStackInHand(hand));
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {

        @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

        NbtCompound requiredNbt = new NbtCompound();

        return nbtComponent != null && nbtComponent.contains("sculkvial.experience") && nbtComponent.copyNbt().getInt("sculkvial.experience") != 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        int xp_saved = 0;

        @Nullable var nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

        if (nbtComponent != null) {
            NbtCompound requiredNbt = new NbtCompound();

            NbtCompound compound = nbtComponent.copyNbt();

            if (nbtComponent.contains("sculkvial.experience")) {

                xp_saved = compound.getInt("sculkvial.experience");
            }
        }


        tooltip.add(Text.translatable("tooltip.sculk-vial.sculk_vial").append(String.valueOf(xp_saved) + "/1395"));



    }
}
