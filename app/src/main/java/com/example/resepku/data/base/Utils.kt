package com.example.resepku.data.base

import android.view.View


inline fun <T : View> T.showIf(condition: () -> Boolean) {
    if (condition()) {
        show()
    } else {
        hide()
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}