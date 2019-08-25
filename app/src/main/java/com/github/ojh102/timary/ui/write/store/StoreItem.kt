package com.github.ojh102.timary.ui.write.store

import com.github.ojh102.timary.data.SelectableItem

data class StoreItem(
    val text: String,
    var date: Long = 0L
) : SelectableItem()
