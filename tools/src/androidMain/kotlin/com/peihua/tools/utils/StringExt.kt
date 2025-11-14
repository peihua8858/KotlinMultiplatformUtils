package com.peihua.tools.utils


import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Patterns
import com.peihua.tools.file.adjustBitmapOrientation
import com.peihua.tools.file.cacheFile
import com.peihua.tools.file.createFileName
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.resume


//const val MOBILE_PHONE =
//    "^((\\+86)?(13\\d|14[5-9]|15[0-35-9]|16[5-6]|17[0-8]|18\\d|19[158-9])\\d{8})$"
//
//val MOBILE_PHONE_PATTERN = MOBILE_PHONE.toRegex()
//fun CharSequence?.isPhoneNumber(): Boolean {
//    return this != null && MOBILE_PHONE_PATTERN.matches(this)
//}

/**
 * 首字母大写
 *
 * @return 成功返回true，失败返回false
 */
fun CharSequence?.firstLetterUpperCase(local: Locale): String {
    if (this.isEmptyOrBlank()) {
        return ""
    }
    if (length <= 1) {
        return this.toString().uppercase(local)
    }
    val firstLetter = substring(0, 1).uppercase(local)
    return firstLetter + substring(1)
}

/**
 * 首字母大写
 *
 * @return 成功返回true，失败返回false
 */
fun CharSequence?.firstLetterUpperCase(): String {
    return firstLetterUpperCase(Locale.ROOT)
}


fun String?.toBase64(): ByteArray? {
    return if (null == this) {
        null
    } else Base64.decode(toByteArray(), Base64.NO_WRAP)
}

/**
 * 验证是否是IP地址
 *
 * @param target 要验证的文本
 * @return 是返回true, 否则返回false
 */
fun CharSequence?.isIpAddress(): Boolean {
    return !this.isNullOrEmpty() && Patterns.IP_ADDRESS.matcher(this).matches()
}

/**
 * 验证是否是邮箱
 *
 * @param target 要验证的文本
 * @return 是返回true, 否则返回false
 */
fun CharSequence?.isEmail(): Boolean {
    return this != null && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

private val sf = SimpleDateFormat("yyyy-MM-dd")

/**
 * 根据时间戳创建文件名
 *
 * @return
 */
fun String.createFolderFileName(): String {
    val millis = System.currentTimeMillis()
    return this + sf.format(millis)
}

fun String.createFolderFile(context: Context): File {
    val fileCache = createFolderFileName()
    val parentPath = context.cacheFile("files")
    return File(parentPath, fileCache)
}

fun String.createFile(context: Context, extension: String): File {
    val fileCache = createFileName(extension)
    val parentPath = context.cacheFile("files")
    return File(parentPath, fileCache)
}


suspend fun String.adjustBitmapOrientationAsync(): Bitmap? {
    return try {
        suspendCancellableCoroutine<Bitmap?> { continuation ->
            continuation.resume(this.adjustBitmapOrientation())
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}

fun String.adjustBitmapOrientation(): Bitmap? {
    return File(this).adjustBitmapOrientation()

}