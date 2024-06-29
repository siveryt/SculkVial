package de.sivery.sculkvial;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SculkVial implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("sculk-vial");

	public static final String MOD_ID = "sculk-vial";

	public static final Item SculkVial = new de.sivery.sculkvial.item.SculkVial(new Item.Settings().maxCount(1).component(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(new NbtCompound())));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Register SculkVial Item
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "sculk_vial"), SculkVial);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			content.add(SculkVial);
		});

		LOGGER.info("Hello Fabric world!");
	}
}