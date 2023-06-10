/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.inventory

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

class InventoryDisplay(uuid: UUID) {
    private val player = Bukkit.getPlayer(uuid)!!
    private val inventory = Bukkit.createInventory(null, 54, "${player.displayName}'s inventory")
    private val items = player.inventory.contents

    fun build(): Inventory {
        return this.createSkull()
            .createExperienceBottle()
            .createOffhand()
            .createArmor()
            .createInventory()
            .createHotbar()
            .createFillers()
            .toInventory()
    }

    private fun createSkull(): InventoryDisplay {
        val skull = ItemStack(Material.PLAYER_HEAD)
        val skullMeta = skull.itemMeta!! as SkullMeta

        skullMeta.setOwningPlayer(player)
        skullMeta.setDisplayName(player.displayName)
        skull.setItemMeta(skullMeta)

        return apply {
            inventory.setItem(0, skull)
        }
    }

    private fun createExperienceBottle(): InventoryDisplay {
        val experienceBottle = ItemStack(Material.EXPERIENCE_BOTTLE)
        val experienceBottleMeta = experienceBottle.itemMeta!!

        experienceBottleMeta.setDisplayName("Level ${player.level}")
        experienceBottle.setItemMeta(experienceBottleMeta)

        return apply {
            inventory.setItem(1, experienceBottle)
        }
    }

    private fun createOffhand(): InventoryDisplay {
        return apply {
            inventory.setItem(3, items[40])
        }
    }

    private fun createArmor(): InventoryDisplay {
        return apply {
            inventory.setItem(5, items[39])
            inventory.setItem(6, items[38])
            inventory.setItem(7, items[37])
            inventory.setItem(8, items[36])
        }
    }

    private fun createHotbar(): InventoryDisplay {
        return apply {
            for (i in 0..8) inventory.setItem(i + 45, items[i])
        }
    }

    private fun createInventory(): InventoryDisplay {
        return apply {
            for (i in 9..35) inventory.setItem(i + 9, items[i])
        }
    }

    private fun createFillers(): InventoryDisplay {
        val filler = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
        val fillerMeta = filler.itemMeta!!
        fillerMeta.setDisplayName("§r")
        filler.setItemMeta(fillerMeta)

        return apply {
            inventory.setItem(2, filler)
            inventory.setItem(4, filler)
            for (i in 9..17) inventory.setItem(i, filler)
        }
    }

    private fun toInventory(): Inventory {
        return this.inventory
    }
}
