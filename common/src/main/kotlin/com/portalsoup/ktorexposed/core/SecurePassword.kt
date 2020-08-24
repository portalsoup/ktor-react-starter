package com.portalsoup.ktorexposed.core

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.xml.bind.DatatypeConverter

class SecurePassword(
    private val rawPassword: String,
    val userSalt: String = generateSalt(32),
    private val appSalt: ByteArray = "iAmAbAdApPsAlT".toByteArray()
) {
    companion object {
        fun generateSalt(length: Int): String {
            val salt = ByteArray(length)
            SecureRandom().nextBytes(salt)
            return DatatypeConverter.printHexBinary(salt)
        }
    }

    fun hashPassword(): String {
        try {
            val keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
            val spec = PBEKeySpec(rawPassword.toCharArray(), userSalt.toByteArray(), 10, 32)
            val key = keyFactory.generateSecret(spec)
            return DatatypeConverter.printHexBinary(key.encoded)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        }

    }
}

