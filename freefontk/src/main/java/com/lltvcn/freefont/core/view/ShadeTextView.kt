package com.lltvcn.freefont.core.view

import kotlin.jvm.JvmOverloads
import com.lltvcn.freefont.core.layer.LayerSpan
import com.lltvcn.freefont.core.layer.SingleWarpSpan
import com.lltvcn.freefont.core.linedrawer.BackgroundDrawer
import com.lltvcn.freefont.core.linedrawer.ForegroundDrawer
import com.lltvcn.freefont.core.linedrawer.LineDrawer
import android.graphics.Paint.FontMetricsInt
import android.content.Context
import android.graphics.*
import android.text.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by zhaolei on 2017/9/18.
 */
open class ShadeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatTextView(context, attrs) {
    private var sb: SpannableStringBuilder? = null
    override fun setText(text: CharSequence, type: BufferType) {
        if (text is SpannableStringBuilder) {
//            sb = (SpannableStringBuilder) text;
//            for (int i = 0; i < sb.length(); i++) {
//                sb.setSpan(new RelativeSizeSpan(1.0f),i,i+1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
            sb = text
            var spans = sb!!.getSpans(0, sb!!.length, LayerSpan::class.java)
            if (spans != null) {
                for (i in sb!!.indices) {
                    spans = sb!!.getSpans(i, i + 1, LayerSpan::class.java)
                    if (spans != null && spans.isNotEmpty()) {
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
        //        if(sb!=null){
//            remove(sb,LeadingMarginSpan.class);
//            remove(sb, LeadingMarginSpan.LeadingMarginSpan2.class);
//        }
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
            val fontMetricsInt = FontMetricsInt()
            paint.getFontMetricsInt(fontMetricsInt)
            drawLineRect(fontMetricsInt, canvas, layout, BackgroundDrawer::class.java)
            super.onDraw(canvas)
            drawLineRect(fontMetricsInt, canvas, layout, ForegroundDrawer::class.java)
        } else {
            super.onDraw(canvas)
        }
    }

    private fun <T : LineDrawer> drawLineRect(
        fontMetricsInt: FontMetricsInt,
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
                    spanStar = lineStart.coerceAtLeast(sb!!.getSpanStart(drawer))
                    spanEnd = lineEnd.coerceAtMost(sb!!.getSpanEnd(drawer))
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
                        //                        drawer.draw(canvas,getPaint(),left,layout.getLineBaseline(line)+fontMetricsInt.top+getPaddingTop(), right ,layout.getLineBaseline(line)+fontMetricsInt.bottom+getPaddingTop());
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