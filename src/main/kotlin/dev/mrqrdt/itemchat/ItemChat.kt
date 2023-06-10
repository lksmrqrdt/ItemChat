/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat

import dev.mrqrdt.itemchat.commands.ShowInventoryCommand
import dev.mrqrdt.itemchat.inventory.InventoryAccess
import dev.mrqrdt.itemchat.listener.AsyncPlayerChatEvent
import dev.mrqrdt.itemchat.listener.InventoryClickEvent
import dev.mrqrdt.itemchat.listener.InventoryDragEvent
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ItemChat : JavaPlugin() {
    companion object {
        lateinit var inventoryAccess: InventoryAccess
    }

    override fun onEnable() {
        config.options().copyDefaults()
        saveDefaultConfig()

        inventoryAccess = InventoryAccess(config)

        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(AsyncPlayerChatEvent(config), this)
        pluginManager.registerEvents(InventoryClickEvent(), this)
        pluginManager.registerEvents(InventoryDragEvent(), this)

        getCommand("itemchat")?.setExecutor(ShowInventoryCommand(config))
    }
}
