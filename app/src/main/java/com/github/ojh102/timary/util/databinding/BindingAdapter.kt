package com.github.ojh102.timary.util.databinding

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.text.method.MovementMethod
import android.view.View
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.github.ojh102.timary.util.extension.afterMeasured
import com.github.ojh102.timary.util.extension.hasResource

class BindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("android:textColor")
        fun setTextColor(textView: TextView, colorOrResId: Int) {
            if (textView.context.hasResource(colorOrResId)) {
                val resId = if (colorOrResId == 0) {
                    null
                } else {
                    AppCompatResources.getColorStateList(textView.context, colorOrResId)
                }

                textView.setTextColor(resId)
            } else {
                textView.setTextColor(colorOrResId)
            }
        }

        @JvmStatic
        @BindingAdapter("textGradientStart", "textGradientEnd")
        fun setGradientText(textView: TextView, startColorRes: Int, endColorRes: Int) {
            textView.afterMeasured {
                paint.shader = LinearGradient(
                        0f,
                        0f,
                        width.toFloat(),
                        height.toFloat(),
                        ContextCompat.getColor(context, startColorRes),
                        ContextCompat.getColor(context, endColorRes),
                        Shader.TileMode.CLAMP
                )
            }
        }

        @JvmStatic
        @BindingAdapter("android:textAppearance")
        fun setTextAppearance(textView: TextView, @StyleRes resId: Int) {
            if (resId == 0) {
                return
            }

            TextViewCompat.setTextAppearance(textView, resId)
        }

        @JvmStatic
        @BindingAdapter("movementMethod")
        fun setMovementMethod(textView: TextView, movementMethod: MovementMethod) {
            textView.movementMethod = movementMethod
        }

        @JvmStatic
        @BindingAdapter("android:background")
        fun setBackground(view: View, colorOrResId: Int) {
            if (view.context.hasResource(colorOrResId)) {
                ViewCompat.setBackground(view, AppCompatResources.getDrawable(view.context, colorOrResId))
            } else {
                ViewCompat.setBackground(view, ColorDrawable(colorOrResId))
            }
        }
    }
}
