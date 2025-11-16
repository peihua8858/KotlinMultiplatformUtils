package com.peihua.tools

import com.peihua.tools.log.Logcat

class AndroidLogger : Logger() {
    override fun printLog(level: Int, stackTraceIndex: Int, tag: String, message: String) {
        when (level) {
            VERBOSE -> Logcat.printLog(stackTraceIndex, Logcat.V, tag, message)
            DEBUG -> Logcat.printLog(stackTraceIndex, Logcat.D, tag, message)
            INFO -> Logcat.printLog(stackTraceIndex, Logcat.I, tag, message)
            WARN -> Logcat.printLog(stackTraceIndex, Logcat.W, tag, message)
            ERROR -> Logcat.printLog(stackTraceIndex, Logcat.E, tag, message)
        }
    }

    override fun writeLog(tag: String, stackTraceIndex: Int, message: String) {
        val context = ContextInitializer.context
        Logcat.writeLog(context, stackTraceIndex, tag, message)
    }
}

private val logger: Logger = AndroidLogger()
actual fun logcat(): Logger = logger