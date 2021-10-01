package com.example.noteapp.feature_note.domain.util

/**
 * Possible list order.
 */
sealed class Order {
    object AscendingOrder : Order()
    object DescendingOrder : Order()
}

/**
 * Possible sorting approach with given order.
 * Requirements : Sort by date in ascending order.
 * Solution : Sort.Date(Order.AscendingOrder)
 */
sealed class Sort(val order: Order) {
    class Date(order: Order) : Sort(order)
    class Title(order: Order) : Sort(order)
    class Color(order: Order) : Sort(order)

    /**
     * Update the order type of current sort.
     */
    fun updateOrder(order: Order) : Sort {
       return when(this) {
            is Date -> Date(order)
            is Title -> Title(order)
            is Color -> Color(order)
        }
    }
}