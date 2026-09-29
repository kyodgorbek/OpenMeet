package com.yodgorbek.openmeet.network

import com.yodgorbek.openmeet.BuildConfig
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object StreamTokenUtils {
    fun generateStreamToken(userId: String): String {
        val secret = BuildConfig.STREAM_API_SECRET
        if (secret.isEmpty()) {
            return io.getstream.video.android.core.StreamVideo.devToken(userId)
        }
        return try {
            val now = System.currentTimeMillis() / 1000
            val exp = now + (86400 * 30) // 30 days
            val header = """{"alg":"HS256","typ":"JWT"}"""
            val payload = """{"user_id":"$userId","iat":$now,"exp":$exp}"""

            val encoder = Base64.getUrlEncoder().withoutPadding()
            val base64Header = encoder.encodeToString(header.toByteArray(Charsets.UTF_8))
            val base64Payload = encoder.encodeToString(payload.toByteArray(Charsets.UTF_8))

            val contentToSign = "$base64Header.$base64Payload"
            val mac = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "HmacSHA256")
            mac.init(secretKey)

            val signatureBytes = mac.doFinal(contentToSign.toByteArray(Charsets.UTF_8))
            val base64Signature = encoder.encodeToString(signatureBytes)

            "$contentToSign.$base64Signature"
        } catch (e: Exception) {
            io.getstream.video.android.core.StreamVideo.devToken(userId)
        }
    }
}
