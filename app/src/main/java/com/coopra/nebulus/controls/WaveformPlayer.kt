package com.coopra.nebulus.controls

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.min

class WaveformPlayer : View {
    private val ringStrokeWidth =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
    private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val artworkPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val outerOval = RectF()
    private var waveformData: IntArray? = null
    private val artworkRect = RectF()

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

    fun setWaveformData(waveformData: IntArray) {
        this.waveformData = waveformData
    }

    fun setArtworkDrawable(artwork: Drawable) {
        if (width > 0 && height > 0) {
            val diameter = min(width, height)
            artworkPaint.shader =
                    BitmapShader(artwork.toBitmap(diameter,
                            diameter),
                            Shader.TileMode.MIRROR,
                            Shader.TileMode.MIRROR)
            invalidate()
        }
    }

    private fun init() {
        ringPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_light)
        ringPaint.strokeWidth = ringStrokeWidth
        ringPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val diameter = min(width, height)
        val padding = ringStrokeWidth / 2
        outerOval.set(((width / 2) - (diameter / 2) + padding),
                ((height / 2) - (diameter / 2) + padding),
                ((width / 2) + (diameter / 2) - padding),
                ((height / 2) + (diameter / 2) - padding))
        canvas?.drawArc(outerOval, 0f, 360f, false, ringPaint)

        if (artworkPaint.shader != null) {
            artworkRect.set(outerOval.left + padding,
                    outerOval.top + padding,
                    outerOval.right - padding,
                    outerOval.bottom - padding)
            canvas?.drawRoundRect(artworkRect,
                    artworkRect.width() / 2,
                    artworkRect.height() / 2,
                    artworkPaint)
        }
    }
}