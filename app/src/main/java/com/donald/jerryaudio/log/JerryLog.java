package com.donald.jerryaudio.log;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("LogTagMismatch")
public class JerryLog {

    private static int logLevel = Log.VERBOSE;

    public static void v(String tag, String msg, Object... args) {
        if (msg == null) {
            return;
        }
        if (isLoggable(Log.VERBOSE)) {
            Log.v(tag, String.format(msg, args));
        }
    }

    public static void d(String tag, String msg, Object... args) {
        if (msg == null) {
            return;
        }
        if (isLoggable(Log.DEBUG)) {
            Log.d(tag, String.format(msg, args));
        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (msg == null) {
            return;
        }
        if (isLoggable(Log.INFO)) {
            Log.i(tag, String.format(msg, args));
        }
    }

    public static void w(String tag, String msg, Object... args) {
        if (msg == null) {
            return;
        }
        if (isLoggable(Log.WARN)) {
            Log.w(tag, String.format(msg, args));
        }
    }

    public static void e(String tag, String msg, Object... args) {
        if (msg == null) {
            return;
        }
        if (isLoggable(Log.ERROR)) {
            Log.e(tag, String.format(msg, args));
        }
    }

    public static void setLogLevel(int level) {
        logLevel = level;
    }

    private static boolean isLoggable(int level) {
        return level >= logLevel;
    }
}
