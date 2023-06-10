/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.message.components

import dev.mrqrdt.itemchat.ItemChat
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.event.player.AsyncPlayerChatEvent

class InventoryComponent(event: AsyncPlayerChatEvent) {
    private val componentBuilder = ComponentBuilder()
    private val player = event.player

    fun build(): Array<out BaseComponent> {
        return this.addHoverEvent()
            .addClickEvent()
            .addPrefix()
            .addTitle()
            .addSuffix()
            .toBaseComponents()
    }

    private fun addHoverEvent(): InventoryComponent {
        return apply {
            componentBuilder.event(
                HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    Text("Click to view ${player.displayName}'s inventory"),
                ),
            )
        }
    }

    private fun addClickEvent(): InventoryComponent {
        // To prevent users from opening other players inventories using a command,
        // ItemChat generates a unique, signed JWT containing the UUID as well as the validity time-period.
        // The key automatically lives in-memory and is generated securely on startup.
        val token = ItemChat.inventoryAccess.generateToken(player.uniqueId)

        return apply {
            componentBuilder.event(
                ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/itemchat $token",
                ),
            )
        }
    }

    private fun addPrefix(): InventoryComponent {
        return apply {
            componentBuilder.append("[").color(ChatColor.WHITE)
        }
    }

    private fun addTitle(): InventoryComponent {
        return apply {
            componentBuilder.append("${player.displayName}'s inventory").color(ChatColor.GOLD)
        }
    }

    private fun addSuffix(): InventoryComponent {
        return apply {
            componentBuilder.append("]").color(ChatColor.WHITE)
        }
    }

    private fun toBaseComponents() = componentBuilder.create()
}
