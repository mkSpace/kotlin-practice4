package com.mashup.kotlin_practice4

/** Practice4
 * 1. sealed class SampleAdapterItem을 만들고 Text, Image 클래스를 생성
 * 2. SampleAdapterItem의 hashCode가 unique한 값을 갖게 설계하자
 **/
sealed class SampleAdapterItem(open val id: Long) {
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = id.hashCode()

    class Text(override val id: Long, val text: String) : SampleAdapterItem(id)
    class Image(override val id: Long, val imageUrl: String) : SampleAdapterItem(id)
}