package com.codeSomethingDaily.novel_viewer

import android.view.KeyEvent
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

private var blockVolumeKey = false

private var sink: EventChannel.EventSink? = null

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "com.codeSomethingDaily/method")
            .setMethodCallHandler { call, result ->
                when (call.method) {
                    "startBlockingVolumeKey" -> {
                        blockVolumeKey = true
                        result.success(true)
                    }
                    "stopBlockingVolumeKey" -> {
                        blockVolumeKey = false
                        result.success(true)
                    }
                    else -> {
                        result.notImplemented()
                    }
                }
            }
        EventChannel(flutterEngine.dartExecutor.binaryMessenger, "com.codeSomethingDaily/event")
            .setStreamHandler(
                object : EventChannel.StreamHandler {

                    override fun onListen(listener: Any?, events: EventChannel.EventSink?) {
                        // startListening
                        sink = events
                    }

                    override fun onCancel(arguments: Any?) {
                        sink = null
                    }
                }
            )
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                sink?.success("volumeDown")
                if (blockVolumeKey) {
                    return true
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                sink?.success("volumeUp")
                if (blockVolumeKey) {
                    return true
                }
            }
        }
        
        return super.onKeyDown(keyCode, event)
    }
}


// package com.codeSomethingDaily.novel_viewer

// import io.flutter.embedding.android.FlutterActivity

// class MainActivity: FlutterActivity() {
// }


