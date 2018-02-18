package com.azazellj.cropyourlife

/**
 * Created by azazellj on 17.02.18.
 */
enum class FilterType(val value: FloatArray, val filterName: String) {
    NORMAL(floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f), "Normal"),
    BLACK_AND_WHITE(floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f), "B&W"),
    INVERTED(floatArrayOf(
            -1f, +0f, +0f, 0f, 255f,
            +0f, -1f, +0f, 0f, 255f,
            +0f, +0f, -1f, 0f, 255f,
            +0f, +0f, +0f, 1f, 0.0f), "Inverted");

    fun fromName(name: String): FilterType {
        return values().find { it.filterName == name } ?: NORMAL
    }
}