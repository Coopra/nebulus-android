package com.coopra.nebulus.controls

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

class WaveformPlayer : View {
    private val ringStrokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
    private val ringPaint = Paint()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
            attrs,
            defStyleAttr) {
        init()
    }

    private fun init() {
        ringPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_light)
        ringPaint.strokeWidth = ringStrokeWidth
        ringPaint.isAntiAlias = true
        ringPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val diameter = min(width, height)
        val padding = ringStrokeWidth / 2
        val outerOval = RectF(padding, padding, diameter - padding, diameter - padding)
        canvas?.drawArc(outerOval, 0f, 360f, false, ringPaint)
    }
}