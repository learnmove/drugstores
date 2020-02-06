package com.jarvislin.drugstores.extension

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.annotation.ColorInt


fun View.transparentEffect(effectAlpha: Float = 0.5f) {
    val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            alpha = effectAlpha
            invalidate()
            return super.onDown(e)
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            callOnClick()
            return super.onSingleTapConfirmed(e)
        }
    }

    val gestureDetector = GestureDetector(context, gestureListener)

    setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                alpha = 1.0f
                invalidate()
                return@setOnTouchListener false
            }
            else -> return@setOnTouchListener gestureDetector.onTouchEvent(event)
        }
    }
}

val ViewGroup.children: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

fun View.show() {
    visibility = VISIBLE
}

fun View.hide(invisible: Boolean = false) {
    visibility = if (invisible) INVISIBLE else GONE
}

fun Drawable.tint(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        mutate().colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
    } else {
        mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}