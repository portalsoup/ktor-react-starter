package com.portalsoup.ktorexposed.core

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class SecurePassword(
    private val rawPassword: String,
    val userSalt: ByteArray = generateSalt(32),
    private val appSalt: ByteArray = "iAmAbAdApPsAlT".toByteArray()
) {
    companion object {
        fun generateSalt(length: Int): ByteArray {
            val salt = ByteArray(length)
            SecureRandom().nextBytes(salt)
            return salt
        }
    }

    fun hashPassword(): ByteArray {
        try {
            val keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
            val spec = PBEKeySpec(rawPassword.toCharArray(), userSalt, 10, 32)
            val key = keyFactory.generateSecret(spec) // Unexpected crash happened here. Keep an eye out.
            return key.encoded
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        }

    }
}

