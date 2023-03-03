package com.gildedrose.domain.items

import arrow.core.*

@Suppress("FunctionName")
fun N(s: String): Name? = Name.of(s)

@JvmInline
value class Name private constructor(val value: String) {
    companion object {
        fun of(value: String): Name? =
            validatedFrom(value).orNull()

        fun validatedFrom(value: String): Validated<Nel<ValidationError>, Name> =
            when {
                value.isBlank() -> BlankName.invalidNel()
                value.length > 255 -> TooLong.invalidNel()
                else -> Name(value).valid()
            }
    }

    override fun toString(): String = value

    object BlankName : ValidationError {
        override val description = "Name can't be blank"

        override fun toString(): String = "BlankName"
    }

    object TooLong : ValidationError {
        override val description = "Name is too long (> 255)"

        override fun toString(): String = "TooLong"
    }
}