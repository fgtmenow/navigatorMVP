package com.noxx.navigator.generator.model

import android.os.Parcelable

data class Navigation(val fragments: List<FragmentItem> = emptyList())

data class FragmentItem(
    val id: String,
    val name: String,
    val arguments: List<ArgumentItem> = emptyList()
) {
    fun getClassName(): String {
        return name.substring(name.lastIndexOf(".") + 1)
    }

    fun getNavId(): String {
        return id.replace("@+id/", "").replace("@id/", "")
    }
}

data class ArgumentItem(
    val name: String,
    val defaultValue: String?,
    val nullable: Boolean,
    val argType: String
) {

    fun getConstName(): String {
        return "ARG_${name.uppercase()}"
    }

    fun getNavType(): String {
        return when {
            typeMapper.containsKey(argType) -> typeMapper[argType]!!

            argType.contains("[]") -> {
                "NavType.ParcelableArrayType(Parcelable::class.java)"
            }

            else -> "NavType.ParcelableType(Parcelable::class.java)"
        }
    }

    fun getParamType(): Class<out Any> {
        val isParcelable = typeMapper.containsKey(argType).not()
        val isArray = argType.contains("[]")
        val primType = argType.replace("[]", "")

        return if (isArray) {
            if (isParcelable) {
                return Array<Parcelable>::class.java
            } else {
                when (primType) {
                    "integer" -> IntArray::class.java
                    "float" -> FloatArray::class.java
                    "long" -> LongArray::class.java
                    "boolean" -> BooleanArray::class.java
                    "string" -> Array<String>::class.java
                    else -> throw RuntimeException("unknown type $primType")
                }
            }
        } else {
            if (isParcelable) {
                return Parcelable::class.java
            } else {
                when (primType) {
                    "integer" -> Int::class.java
                    "float" -> Float::class.java
                    "long" -> Long::class.java
                    "boolean" -> Boolean::class.java
                    "string" -> String::class.java
                    else -> throw RuntimeException("unknown type $primType")
                }
            }
        }
    }

    fun getNullableValue(): String {
        return nullable.toString()
    }
}

private val typeMapper = hashMapOf(
    "integer" to "NavType.IntType",
    "float" to "NavType.FloatType",
    "long" to "NavType.LongType",
    "boolean" to "NavType.BoolType",
    "string" to "NavType.StringType",
    //
    "string[]" to "NavType.StringArrayType",
    "float[]" to "NavType.FloatArrayType",
    "boolean[]" to "NavType.BoolArrayType",
    "long[]" to "NavType.LongArrayType",
    "integer[]" to "NavType.IntArrayType"
)

private val paramMapper = hashMapOf(
    "integer" to Int::class,
    "float" to Float::class,
    "long" to Long::class,
    "boolean" to Boolean::class,
    "string" to String::class
)
