/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.message.components

import net.md_5.bungee.api.ChatColor
import org.bukkit.event.player.AsyncPlayerChatEvent

class CustomItemComponent(event: AsyncPlayerChatEvent) : ItemComponent(event) {
    override fun addName(): ItemComponent {
        return apply {
            componentBuilder.append(item.itemMeta!!.displayName).color(ChatColor.GOLD)
        }
    }
}
