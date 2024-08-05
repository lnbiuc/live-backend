package com.lnbiuc.livebackend.utils.stream

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


object StreamUtils {
    private val DIGITS_LOWER: CharArray =
        charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    fun getSafeUrl(key: String, streamName: String, txTime: Long): String {
        val input = StringBuilder();
        input.append(key).append(streamName).append(java.lang.Long.toHexString(txTime).uppercase(Locale.getDefault()))

        var txSecret: String? = null
        try {
            val messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
            txSecret = byteArrayToHexString(
                messageDigest.digest(input.toString().toByteArray(charset("UTF-8")))
            )
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        if (txSecret == null) return ""
        val sb = StringBuilder()
        sb.append("txSecret=")
            .append(txSecret)
            .append("&")
            .append("txTime=")
            .append(java.lang.Long.toHexString(txTime))
        return sb.toString()
    }

    private fun byteArrayToHexString(data: ByteArray): String {
        val out = CharArray(data.size shl 1)

        var i = 0
        var j = 0
        while (i < data.size) {
            out[j++] = DIGITS_LOWER[0xF0 and data[i].toInt() ushr 4]
            out[j++] = DIGITS_LOWER[0x0F and data[i].toInt()]
            i++
        }
        return String(out)
    }
}