package com.noxx.navigator.ext

import android.os.Bundle
import android.os.Parcel
import android.util.Base64
import timber.log.Timber

fun Bundle.toBase64(): String {
    val parcel = Parcel.obtain()
    var serialized = try {
        this.writeToParcel(parcel, 0)
        Base64.encodeToString(parcel.marshall(), 0)
    } catch (e: Exception) {
        Timber.e(e)
        ""
    } finally {
        parcel.recycle()
    }
    return serialized
}

fun String.fromBase64(): Bundle {
    val parcel = Parcel.obtain()
    return try {
        val data = Base64.decode(this, 0)
        parcel.unmarshall(data, 0, data.size)
        parcel.setDataPosition(0)
        parcel.readBundle(this.javaClass.classLoader) ?: Bundle.EMPTY
    } catch (e: Exception) {
        Timber.e(e)
        Bundle.EMPTY
    } finally {
        parcel.recycle()
    }
}

fun Bundle.getStringValue(key: String): String {
    return when (get(key)) {
        is String -> getString(key).toString()
        is Long -> getLong(key).toString()
        is Int -> getInt(key).toString()
        is Boolean -> getBoolean(key).toString()
        is Float -> getFloat(key).toString()
        else -> throw RuntimeException("unknown param type")
    }
}
