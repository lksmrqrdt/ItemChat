/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.commands

import dev.mrqrdt.itemchat.ItemChat
import dev.mrqrdt.itemchat.inventory.InventoryDisplay
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.Configuration
import org.bukkit.entity.Player

class ShowInventoryCommand(config: Configuration) : CommandExecutor {
    private val expiredOrInvalidWarning = config.getString("inventory.expired-warning")

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size == 1 && sender is Player) {
            val player: Player = sender

            val uuid = ItemChat.inventoryAccess.validateToken(args[0])
            if (uuid == null || Bukkit.getPlayer(uuid) == null) {
                player.spigot().sendMessage(*ComponentBuilder().append(expiredOrInvalidWarning).create())
                return true
            }

            val inventory = InventoryDisplay(uuid).build()
            player.openInventory(inventory)
        }

        return true
    }
}
