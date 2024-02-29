package com.huar.log

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.huar.log.ktx.AppExceptionHandler
import com.huar.log.ktx.LogLevel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //手动添加日志
        AppExceptionHandler.logException(this.javaClass.simpleName,"MainActivity view",LogLevel.INFO)
        //获取所有日志
        GlobalScope.launch {
            AppExceptionHandler.getAllLogs()
        }



    }
}