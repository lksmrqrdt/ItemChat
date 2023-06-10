/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.message.components

import org.bukkit.event.player.AsyncPlayerChatEvent

fun createInventoryComponent(event: AsyncPlayerChatEvent): InventoryComponent {
    return InventoryComponent(event)
}
