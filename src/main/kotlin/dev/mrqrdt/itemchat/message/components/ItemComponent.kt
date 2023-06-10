/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.message.components

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.ItemTag
import net.md_5.bungee.api.chat.hover.content.Item
import org.bukkit.event.player.AsyncPlayerChatEvent

abstract class ItemComponent(event: AsyncPlayerChatEvent) {
    protected val componentBuilder = ComponentBuilder()
    protected val item = event.player.inventory.itemInMainHand

    fun build(): Array<out BaseComponent> {
        return this.addHoverEvent()
            .addPrefix()
            .addName()
            .addQuantity()
            .addSuffix()
            .toBaseComponents()
    }

    private fun addHoverEvent(): ItemComponent {
        val nbt = when (item.hasItemMeta()) {
            true -> item.itemMeta!!.asString
            false -> "{}"
        }

        return apply {
            componentBuilder.event(
                HoverEvent(
                    HoverEvent.Action.SHOW_ITEM,
                    Item(item.type.key.toString(), item.amount, ItemTag.ofNbt(nbt)),
                ),
            )
        }
    }

    private fun addPrefix(): ItemComponent {
        return apply {
            componentBuilder.append("[").color(ChatColor.WHITE)
        }
    }

    protected abstract fun addName(): ItemComponent

    // Only show a quantity, if there's more than just one single item
    private fun addQuantity(): ItemComponent {
        val quantity = when (item.amount) {
            1 -> ""
            else -> " x${item.amount}"
        }
        return apply {
            componentBuilder.append(quantity).color(ChatColor.AQUA)
        }
    }

    private fun addSuffix(): ItemComponent {
        return apply {
            componentBuilder.append("]").color(ChatColor.WHITE)
        }
    }

    private fun toBaseComponents() = componentBuilder.create()
}
