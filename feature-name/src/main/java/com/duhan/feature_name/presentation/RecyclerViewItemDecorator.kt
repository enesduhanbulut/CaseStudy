package com.duhan.feature_name.presentation

import android.graphics.Rect
import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecorator
    (@param:IntRange(from = 0) private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //set  margin to all
        outRect.right = margin
        outRect.left = margin
        outRect.top = margin
        outRect.bottom = margin
    }
}