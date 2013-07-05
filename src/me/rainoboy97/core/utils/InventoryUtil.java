package me.rainoboy97.core.utils;

import java.util.HashMap;

import net.minecraft.server.v1_6_R1.EntityHuman;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R1.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/** Class/methods taken and modified from MCore - MassiveCraft **/

public class InventoryUtil {
	
	public static boolean isOutside(InventoryClickEvent event) {
		return event.getRawSlot() < 0;
	}

	public static boolean isTopInventory(InventoryClickEvent event) {
		if (isOutside(event))
			return false;
		return event.getRawSlot() < event.getInventory().getSize();
	}

	public static boolean isBottomInventory(InventoryClickEvent event) {
		if (isOutside(event))
			return false;
		return event.getRawSlot() >= event.getInventory().getSize();
	}

	public static boolean isGiving(InventoryClickEvent event) {
		if (isOutside(event))
			return false;
		boolean topClicked = isTopInventory(event);

		boolean ret = false;

		if (topClicked) {
			ret = (event.getCursor() != null && event.getCursor().getAmount() > 0);
		} else {
			ret = event.isShiftClick();
		}

		return ret;
	}

	public static boolean isTaking(InventoryClickEvent event) {
		if (isOutside(event))
			return false;
		boolean topClicked = isTopInventory(event);

		boolean ret = false;

		if (topClicked) {
			ret = (event.getCurrentItem() != null && event.getCurrentItem().getAmount() > 0);
		}

		return ret;
	}

	public static boolean isAltering(InventoryClickEvent event) {
		return isGiving(event) || isTaking(event);
	}

	public static ItemStack isEquipping(InventoryClickEvent event) {

		boolean isShiftClick = event.isShiftClick();
		InventoryType inventoryType = event.getInventory().getType();
		SlotType slotType = event.getSlotType();
		ItemStack cursor = event.getCursor();
		ItemStack currentItem = event.getCurrentItem();

		if (isShiftClick) {
			if (inventoryType != InventoryType.CRAFTING)
				return null;
			if (slotType == SlotType.CRAFTING)
				return null;
			if (slotType == SlotType.ARMOR)
				return null;
			if (slotType == SlotType.RESULT)
				return null;
			if (currentItem.getType() == Material.AIR)
				return null;
			return currentItem;
		} else {
			if (slotType == SlotType.ARMOR) {
				return cursor;
			}
			return null;
		}
	}

	public static boolean isEmpty(Inventory inv) {
		if (inv == null)
			return true;

		for (ItemStack itemStack : inv.getContents()) {
			if (isSomething(itemStack))
				return false;
		}

		if (inv instanceof PlayerInventory) {
			PlayerInventory pinv = (PlayerInventory) inv;

			if (isSomething(pinv.getHelmet()))
				return false;
			if (isSomething(pinv.getChestplate()))
				return false;
			if (isSomething(pinv.getLeggings()))
				return false;
			if (isSomething(pinv.getBoots()))
				return false;
		}

		return true;
	}

	public static boolean isNothing(ItemStack itemStack) {
		if (itemStack == null)
			return true;
		if (itemStack.getAmount() == 0)
			return true;
		if (itemStack.getTypeId() == 0)
			return true;
		return false;
	}

	public static boolean isSomething(ItemStack itemStack) {
		return !isNothing(itemStack);
	}

	public static ItemStack[] cloneItemStacks(ItemStack[] itemStacks) {
		ItemStack[] ret = new ItemStack[itemStacks.length];
		for (int i = 0; i < itemStacks.length; i++) {
			ItemStack stack = itemStacks[i];
			if (stack == null)
				continue;
			ret[i] = new ItemStack(itemStacks[i]);
		}
		return ret;
	}

	public static Inventory cloneInventory(Inventory inventory) {
		if (inventory == null)
			return null;

		Inventory ret = null;

		int size = inventory.getSize();
		InventoryHolder holder = inventory.getHolder();
		String title = inventory.getTitle();

		if (inventory instanceof PlayerInventory) {
			PlayerInventory pret = (PlayerInventory) new net.minecraft.server.v1_6_R1.PlayerInventory((EntityHuman) inventory.getHolder());
			ret = pret;

			PlayerInventory pinventory = (PlayerInventory) inventory;

			pret.setHelmet(pinventory.getHelmet() == null ? null : new ItemStack(pinventory.getHelmet()));
			pret.setChestplate(pinventory.getChestplate() == null ? null : new ItemStack(pinventory.getChestplate()));
			pret.setLeggings(pinventory.getLeggings() == null ? null : new ItemStack(pinventory.getLeggings()));
			pret.setBoots(pinventory.getBoots() == null ? null : new ItemStack(pinventory.getBoots()));
		} else {
			ret = new CraftInventoryCustom(holder, size, title);
		}

		ItemStack[] contents = cloneItemStacks(inventory.getContents());
		ret.setContents(contents);

		return ret;
	}

	public static PlayerInventory cloneInventory(PlayerInventory inventory) {
		return (PlayerInventory) cloneInventory((Inventory) inventory);
	}

	public static void setAllContents(Inventory from, Inventory to) {
		to.setContents(from.getContents());
		if (from instanceof PlayerInventory) {
			PlayerInventory pfrom = (PlayerInventory) from;
			if (to instanceof PlayerInventory) {
				PlayerInventory pto = (PlayerInventory) to;

				pto.setHelmet(pfrom.getHelmet());
				pto.setChestplate(pfrom.getChestplate());
				pto.setLeggings(pfrom.getLeggings());
				pto.setBoots(pfrom.getBoots());
			}
		}
	}

	public static int roomLeft(Inventory inventory, ItemStack item, int limit) {
		inventory = cloneInventory(inventory);
		int ret = 0;
		while (limit <= 0 || ret < limit) {
			HashMap<Integer, ItemStack> result = inventory.addItem(item.clone());
			if (result.size() != 0)
				return ret;
			ret++;
		}
		return ret;
	}

	public static void addItemTimes(Inventory inventory, ItemStack item, int times) {
		for (int i = 0; i < times; i++) {
			inventory.addItem(item.clone());
		}
	}

	public static int countSimilar(Inventory inventory, ItemStack itemStack) {
		int ret = 0;
		for (ItemStack item : inventory.getContents()) {
			if (item == null)
				continue;
			if (!item.isSimilar(itemStack))
				continue;
			ret += item.getAmount();
		}
		return ret;
	}
}
