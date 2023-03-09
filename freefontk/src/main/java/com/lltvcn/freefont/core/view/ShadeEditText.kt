package com.lltvcn.freefont.core.view

import com.lltvcn.freefont.core.layer.LayerSpan
import android.widget.EditText
import com.lltvcn.freefont.core.layer.SingleWarpSpan
import com.lltvcn.freefont.core.linedrawer.BackgroundDrawer
import com.lltvcn.freefont.core.linedrawer.ForegroundDrawer
import com.lltvcn.freefont.core.linedrawer.LineDrawer
import android.content.Context
import android.graphics.*
import android.text.*
import android.util.AttributeSet
import android.util.Log

/**
 * Created by zhaolei on 2017/10/12.
 */
class ShadeEditText(context: Context?, attrs: AttributeSet?) : EditText(context, attrs) {
    private var sb: SpannableStringBuilder? = null
    override fun setText(text: CharSequence, type: BufferType) {
        Log.i("jjjjjjjj", "setText$text")
        if (text is SpannableStringBuilder) {
            sb = text
            var spans = sb!!.getSpans(0, sb!!.length, LayerSpan::class.java)
            if (spans != null) {
                for (i in 0 until sb!!.length) {
                    spans = sb!!.getSpans(i, i + 1, LayerSpan::class.java)
                    if (spans != null && spans.size > 0) {
                        sb!!.setSpan(
                            SingleWarpSpan(spans[spans.size - 1]),
                            i,
                            i + 1,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
            remove(sb, LayerSpan::class.java)
        } else {
            sb = null
        }
        super.setText(text, type)
    }

    private fun <T> remove(sb: SpannableStringBuilder?, c: Class<T>) {
        val spans = sb!!.getSpans(0, sb.length, c)
        if (spans != null) {
            for (i in spans.indices) {
                sb.removeSpan(spans[i])
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (layout != null && sb != null) {
            drawLineRect(canvas, layout, BackgroundDrawer::class.java)
            super.onDraw(canvas)
            drawLineRect(canvas, layout, ForegroundDrawer::class.java)
        } else {
            super.onDraw(canvas)
        }
    }

    private fun <T : LineDrawer> drawLineRect(
        canvas: Canvas,
        layout: Layout,
        drawerClass: Class<T>
    ) {
        val drawers: Array<LineDrawer>? = sb!!.getSpans(0, sb!!.length, drawerClass) as Array<LineDrawer>?
        if (drawers != null) {
            var drawer: LineDrawer? = null
            var lineStart: Int
            var lineEnd: Int
            var spanStar: Int
            var spanEnd: Int
            var left: Float
            var right: Float
            val lineCount = lineCount
            for (line in 0 until lineCount) {
                lineStart = layout.getLineStart(line)
                lineEnd = layout.getLineEnd(line)
                for (j in drawers.indices) {
                    drawer = drawers[j]
                    spanStar = Math.max(lineStart, sb!!.getSpanStart(drawer))
                    spanEnd = Math.min(lineEnd, sb!!.getSpanEnd(drawer))
                    if (spanEnd > spanStar) {
                        left = if (lineStart < spanStar) {
                            layout.getLineLeft(line) + Layout.getDesiredWidth(
                                sb,
                                lineStart,
                                spanStar,
                                paint
                            )
                        } else {
                            layout.getLineLeft(line)
                        }
                        right = if (lineEnd > spanEnd) {
                            layout.getLineRight(line) - StaticLayout.getDesiredWidth(
                                sb,
                                spanEnd,
                                lineEnd,
                                paint
                            )
                        } else {
                            layout.getLineRight(line)
                        }
                        left += paddingLeft.toFloat()
                        right += paddingLeft.toFloat()
                        drawer.draw(
                            canvas,
                            paint,
                            left,
                            layout.getLineTop(line) + paddingTop,
                            right,
                            layout.getLineBottom(line) + paddingTop,
                            layout.getLineBaseline(line)
                        )
                    }
                }
            }
        }
    }
}