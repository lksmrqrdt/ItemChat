/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClickEvent : Listener {
    @EventHandler
    fun onInventoryInteract(event: InventoryClickEvent) {
        if (event.view.title.endsWith("\'s inventory")) {
            event.isCancelled = true
        }
    }
}
