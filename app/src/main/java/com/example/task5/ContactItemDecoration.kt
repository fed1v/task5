package com.example.task5

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class ContactItemDecoration(
    private val dividerDrawable: Drawable,
    private val spacing: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
            .let { if (it == RecyclerView.NO_POSITION) return else it }

        outRect.bottom = when (position) {
            RecyclerView.NO_POSITION -> 0
            parent.adapter?.itemCount?.minus(1) -> 0
            else -> spacing
        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children.forEach { view ->
            val position = parent.getChildAdapterPosition(view)
                .let { if (it == RecyclerView.NO_POSITION) return else it }

            if (position != (parent.adapter?.itemCount ?: 0) - 1) {
                val left = parent.paddingLeft
                val top = view.bottom
                val right = view.right - parent.paddingRight
                val bottom = top + dividerDrawable.intrinsicHeight
                dividerDrawable.bounds = Rect(left, top, right, bottom)
                dividerDrawable.draw(c)
            }
        }
    }
}