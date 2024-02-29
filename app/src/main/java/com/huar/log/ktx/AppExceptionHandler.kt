package com.huar.log.ktx

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


/**
 * 使用 Room 数据库保存日志
 */
object AppExceptionHandler : Thread.UncaughtExceptionHandler {

    private lateinit var logDao: LogEntryDao

    fun init(application: Application) {
        val db = Room.databaseBuilder(application, AppDatabase::class.java, "app_database")
            .build()
        logDao = db.logEntryDao()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    //捕获异常并保存
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        saveLogToDatabase("UncaughtExceptionHandler", throwable.message ?: "Unknown error",  LogLevel.ERROR)
    }

    fun logException(tag: String, message: String, level: LogLevel) {
        saveLogToDatabase(tag, message, level)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveLogToDatabase(tag: String, message: String, level: LogLevel) {
        val timestamp = System.currentTimeMillis()
        val formattedDate = formatDate(timestamp)
        GlobalScope.launch(Dispatchers.IO) {
            logDao.insert(LogEntry(timestamp = formattedDate, level = level.name, tag = tag, message = message))
        }
    }

    private fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
    fun getAllLogs(): List<LogEntry> {
        return logDao.getAllLogs()
    }
}

// 枚举类，表示日志级别
enum class LogLevel {
    ERROR,
    INFO,
    WARN
}