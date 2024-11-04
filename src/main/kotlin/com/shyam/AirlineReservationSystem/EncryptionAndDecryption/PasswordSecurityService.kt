package com.shyam.AirlineReservationSystem.EncryptionAndDecryption

import com.shyam.AirlineReservationSystem.Constants
import com.shyam.AirlineReservationSystem.DTO.EncryptionResult
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest

@Service
class PasswordSecurityService {

    fun encryptPassword(password: String): EncryptionResult {

        val salt = generateRandomBytes(Constants.SALT_LENGTH) // Generate a random salt
        val secretKey = generateAESKey()  // Generate a secure key
        val iv = generateRandomBytes(Constants.GCM_IV_LENGTH) // Generate IV for GCM mode
        val hashedPassword = hashPassword(password, salt) // Hash the password with salt before encryption
        val encryptedData = aesEncrypt(hashedPassword, secretKey, iv) // Encrypt the hashed password

        return EncryptionResult(
            encryptedPassword = Base64.getEncoder().encodeToString(encryptedData),
            salt = Base64.getEncoder().encodeToString(salt),
            iv = Base64.getEncoder().encodeToString(iv),
            secretKey = Base64.getEncoder().encodeToString(secretKey.encoded)
        )
    }

    fun verifyPassword(
        inputPassword: String,
        encryptedPassword: String,
        storedSalt: String,
        storedIv: String,
        storedKey: String
    ): Boolean {
        try {
            val salt = Base64.getDecoder().decode(storedSalt)
            val iv = Base64.getDecoder().decode(storedIv)
            val secretKey = stringToSecretKey(storedKey)
            val encryptedBytes = Base64.getDecoder().decode(encryptedPassword)

            val hashedInput = hashPassword(inputPassword, salt) // Hash the input password with stored salt
            val decryptedPassword = aesDecrypt(encryptedBytes, secretKey, iv) // Decrypt stored password

            return MessageDigest.isEqual(hashedInput, decryptedPassword) // Compare in constant time to prevent timing attacks
        } catch (e: Exception) {
            return false // Log the error appropriately
        }
    }

    private fun generateRandomBytes(length: Int): ByteArray {
        val bytes = ByteArray(length)
        SecureRandom().nextBytes(bytes)
        return bytes
    }

    private fun hashPassword(password: String, salt: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(salt)
        return digest.digest(password.toByteArray())
    }

    private fun generateAESKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(Constants.ENCRYPTION_KEY_SIZE, SecureRandom())
        return keyGenerator.generateKey()
    }

    private fun stringToSecretKey(keyStr: String): SecretKey {
        val decodedKey = Base64.getDecoder().decode(keyStr)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }

    private fun aesEncrypt(data: ByteArray, secretKey: SecretKey, iv: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val gcmSpec = GCMParameterSpec(Constants.GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec)
        return cipher.doFinal(data)
    }

    private fun aesDecrypt(encryptedData: ByteArray, secretKey: SecretKey, iv: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val gcmSpec = GCMParameterSpec(Constants.GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)
        return cipher.doFinal(encryptedData)
    }
}