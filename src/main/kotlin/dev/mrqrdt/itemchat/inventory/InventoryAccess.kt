/*
 * Copyright (c) 2023
 * Lukas Erich Rodrigues Marquardt, l.marquardt@mrqrdt.dev
 *
 * SPDX-License-Identifier: MIT OR Apache-2.0
 */

package dev.mrqrdt.itemchat.inventory

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.bukkit.configuration.Configuration
import java.security.SecureRandom
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

class InventoryAccess(config: Configuration) {
    private var secretKey = ByteArray(48)
    private val expiryMinutes = config.getLong("inventory.cache-duration-minutes")

    init {
        val rng = SecureRandom()
        rng.nextBytes(secretKey)
    }

    fun generateToken(uuid: UUID): String {
        val expiryTimestamp = Instant.now()
            .plusSeconds(TimeUnit.MINUTES.toSeconds(expiryMinutes))
        val algorithm = Algorithm.HMAC384(secretKey)

        return JWT.create()
            .withSubject(uuid.toString())
            .withExpiresAt(expiryTimestamp)
            .sign(algorithm)
    }

    fun validateToken(token: String): UUID? {
        return try {
            val algorithm = Algorithm.HMAC384(secretKey)
            val jwt = JWT.require(algorithm).build().verify(token)
            UUID.fromString(jwt.subject)
        } catch (exception: Exception) {
            null
        }
    }
}
