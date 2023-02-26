package com.gildedrose.domain.items

import arrow.core.*

@Suppress("FunctionName")
fun N(s: String): Name? = Name.of(s)

@JvmInline
value class Name private constructor(private val value: String) {
    init {
        require(value.isNotBlank()) { BlankName.description }
    }

    companion object {
        fun of(value: String): Name? =
            validatedFrom(value).orNull()

        fun validatedFrom(value: String): Validated<Nel<ValidationError>, Name> =
            try {
                Name(value).valid()
            } catch (_: IllegalArgumentException) {
                BlankName.invalidNel()
            }
    }

    override fun toString(): String = value

    object BlankName : ValidationError {
        override val description = "Name can't be blank"

        override fun toString(): String = "BlankName"
    }
}