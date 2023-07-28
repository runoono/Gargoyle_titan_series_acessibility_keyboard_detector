package com.runo.acessibilitykeyboarddetector

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityWindowInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class KeyboardService: AccessibilityService() {

    var keyboardState = false
//    var triggerCount = 0
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
//        Log.d("keyboardService", "onAccessibilityEvent: triggered $triggerCount")
//        triggerCount++
        try {
            var keyboardOpened = false
            for (windowInfo in windows) {
                if (windowInfo.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                    keyboardOpened = true
                }
            }

            if(keyboardOpened && !keyboardState){
                keyboardState = true
                Log.d("keyboardService", "keyboard is opened!")
                startOrStop(false, "uinput_titan")
            }else if(!keyboardOpened && keyboardState){
                keyboardState = false
                Log.d("keyboardService", "keyboard is closed!")
                startOrStop(true, "uinput_titan")
            }
        }catch (e: Exception){
            Log.e("keyboardService", "onAccessibilityEvent: ", e)
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        startOrStop(true, "uinput_titan")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        startOrStop(true, "uinput_titan")
        return super.onUnbind(intent)
    }
    override fun onInterrupt() {
    }

    fun startOrStop(start: Boolean, service: String) {
        val com = if (start) "start" else "stop"
        val command = "$com $service"
        Log.d("keyboardService",command)
        RootProvider.RunAsRoot(command)
    }
}