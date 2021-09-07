package com.portalsoup.ktorexposed.core.util

import com.portalsoup.ktorexposed.core.util.Try.Failure
import com.portalsoup.ktorexposed.core.util.Try.Success
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.xml.bind.DatatypeConverter

class SecurePassword(
    private val rawPassword: String,
    val userSalt: String = generateSalt(32),
    private val appSalt: ByteArray = "iamabadappsalt".toByteArray()
) {
    companion object {
        fun generateSalt(length: Int): String {
            val salt = ByteArray(length)
            SecureRandom().nextBytes(salt)
            return DatatypeConverter.printHexBinary(salt)
        }
    }

    fun hashPassword(): String = when (
        val result = Try.catching {
            PBEKeySpec(rawPassword.toCharArray(), userSalt.toByteArray(), 10, 32)
                .let { SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512").generateSecret(it) }
                .let { DatatypeConverter.printHexBinary(it.encoded) }
        }
    ) {
        is Success -> result.data
        is Failure -> throw result.error
    }

    fun hashPassword2(): String {
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

