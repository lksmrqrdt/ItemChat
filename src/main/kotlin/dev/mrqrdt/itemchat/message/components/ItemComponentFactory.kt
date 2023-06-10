/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.message.components

import org.bukkit.event.player.AsyncPlayerChatEvent

fun createItemComponent(event: AsyncPlayerChatEvent): ItemComponent {
    val item = event.player.inventory.itemInMainHand

    return if (item.hasItemMeta() && item.itemMeta!!.hasDisplayName()) {
        CustomItemComponent(event)
    } else {
        DefaultItemComponent(event)
    }
}
