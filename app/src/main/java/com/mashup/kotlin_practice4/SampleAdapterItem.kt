package com.mashup.kotlin_practice4

sealed class SampleAdapterItem(open val id: Long) {
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = id.hashCode()

    data class Text(override val id: Long, val text: String) : SampleAdapterItem(id)
    data class Image(override val id: Long, val imageUrl: String) : SampleAdapterItem(id)
}
