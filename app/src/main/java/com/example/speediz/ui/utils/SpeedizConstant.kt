package com.example.speediz.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

enum class SpeedizPackageType(val type: String) {
    Electronics("Electronics"),
    Clothing("Clothing"),
    Documents("Documents"),
    Furniture("Furniture"),
    Materials("Materials"),
    Gifts("Gifts"),
    Food("Food"),
    Supplies("Supplies"),
    Other("Other")
}

enum class SpeedizPaidStatus(val status: String) {
    Paid("Paid"),
    Unpaid("Unpaid"),
}
class LocationPrefix(val locationPrefix: String): VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return prefixFilter(text, locationPrefix)
    }
}
fun prefixFilter(number: AnnotatedString, prefix: String): TransformedText {
//    val formattedText = formatPhoneNumber(number.text)
    val out = prefix + number
    val prefixOffset = prefix.length

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset + prefixOffset
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset < prefixOffset) return 0
            return offset - prefixOffset
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}