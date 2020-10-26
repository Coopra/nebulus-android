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
    private val waveformPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val outerOval = RectF()
    private var waveformData = mutableListOf<Int>()
    private var artworkBitmap: Bitmap? = null
    private val artworkPath = Path()

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
        var index = 0
        for (dataPoint in waveformData) {
            if (index == 0) {
                this.waveformData.add(dataPoint)
            }

            index++
            if (index == 25) {
                index = 0
            }
        }

        invalidate()
    }

    fun setArtworkDrawable(artwork: Drawable) {
        if (width > 0 && height > 0) {
            val diameter: Int = if (waveformData.isNotEmpty()) {
                (min(width,
                        height) - (waveformData.maxOrNull()!! * 2) - (ringStrokeWidth * 2f)).toInt()
            } else {
                (min(width, height) - (ringStrokeWidth * 2f)).toInt()
            }

            artworkBitmap = artwork.toBitmap(diameter, diameter)
            invalidate()
        }
    }

    private fun init() {
        ringPaint.color = ContextCompat.getColor(context, android.R.color.holo_red_light)
        ringPaint.strokeWidth = ringStrokeWidth
        ringPaint.style = Paint.Style.STROKE

        waveformPaint.color = ContextCompat.getColor(context, android.R.color.darker_gray)
        waveformPaint.strokeWidth = ringStrokeWidth
        waveformPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val diameter: Int
        if (waveformData.isNotEmpty()) {
            diameter = min(width, height) - (waveformData.maxOrNull()!! * 2)

            canvas?.save()
            for (waveformBar in waveformData) {
                canvas?.drawLine(
                        width / 2f,
                        (height / 2f) - (diameter / 2f),
                        width / 2f,
                        ((height / 2f) - (diameter / 2f)) - waveformBar,
                        waveformPaint
                )

                canvas?.rotate(25f, width / 2f, height / 2f)
            }

            canvas?.restore()
        } else {
            diameter = min(width, height)
        }

        val padding = ringStrokeWidth / 2
        outerOval.set(((width / 2) - (diameter / 2) + padding),
                ((height / 2) - (diameter / 2) + padding),
                ((width / 2) + (diameter / 2) - padding),
                ((height / 2) + (diameter / 2) - padding))
        canvas?.drawArc(outerOval, 0f, 360f, false, ringPaint)

        if (artworkBitmap != null) {
            canvas?.save()
            artworkPath.reset()
            artworkPath.addCircle(width / 2f,
                    height / 2f,
                    (diameter / 2f) - (padding * 2f),
                    Path.Direction.CW)

            canvas?.clipPath(artworkPath)
            canvas?.drawBitmap(artworkBitmap!!,
                    outerOval.left + padding,
                    outerOval.top + padding,
                    artworkPaint)
            canvas?.restore()
        }
    }
}