/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.listener

import dev.mrqrdt.itemchat.message.components.createInventoryComponent
import dev.mrqrdt.itemchat.message.components.createItemComponent
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import org.bukkit.Material
import org.bukkit.configuration.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class AsyncPlayerChatEvent(config: Configuration) : Listener {
    private val itemDelimiters = config.getStringList("item.keywords")
    private val inventoryDelimiters = config.getStringList("inventory.keywords")
    private val delimiters = itemDelimiters + inventoryDelimiters

    private val emptyHandWarning = config.getString("item.empty-hand-warning")

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        if (delimiters.none { event.message.contains(it) }) {
            return
        }

        event.isCancelled = true

        if (itemDelimiters.any { event.message.contains(it) } && event.player.inventory.itemInMainHand.type == Material.AIR) {
            return event.player.sendMessage(emptyHandWarning)
        }

        val itemComponent = createItemComponent(event).build()
        val inventoryComponent = createInventoryComponent(event).build()

        val message = extractMessage(event)
        val messageBuilder = ComponentBuilder()

        message.forEach {
            when (it) {
                in itemDelimiters -> messageBuilder.append(itemComponent)
                in inventoryDelimiters -> messageBuilder.append(inventoryComponent)
                // The Event has to be manually cancelled for every non-interactive elements.
                else -> messageBuilder.append(it)
                    .event(null as HoverEvent?)
                    .event(null as ClickEvent?)
            }
        }

        event.recipients.forEach {
            it.spigot().sendMessage(ChatMessageType.CHAT, *messageBuilder.create())
        }
    }

    private fun extractMessage(event: AsyncPlayerChatEvent): List<String> {
        // Restore the original message including the formatting, before splitting it up
        val message = String.format(event.format, event.player.displayName, event.message)

        val output = mutableListOf<String>()
        var index = 0

        while (index < message.length) {
            val found = message.findAnyOf(delimiters, index)

            // Add the remainder of the message as the final element of the output list
            if (found == null) {
                output.add(message.substring(index))
                break
            }

            // Add the substring found, which ends in front of the delimiter.
            // Add the delimiter afterward, then set the index to behind it.
            output.add(message.substring(index, found.first))
            output.add(found.second)
            index = found.first + found.second.length
        }

        return output
    }
}
