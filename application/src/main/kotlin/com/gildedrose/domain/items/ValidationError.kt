package com.gildedrose.domain.items

sealed interface ValidationError {

    val message: String

    object BlankName : ValidationError {
        override val message: String
            get() = "Name can't be blank"

        override fun toString(): String = "BlankName"
    }

    object NegativeQuality : ValidationError {
        override val message: String
            get() = "Quality can't be negative"

        override fun toString(): String = "InvalidQuality"
    }

    object InvalidLifecycle : ValidationError {
        override val message: String
            get() = "Lifecycle is not valid"

        override fun toString(): String = "InvalidLifecycle"
    }

}