package com.example.trainapp.helpers

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class TimeFormatter {
    companion object {
        fun dateTimeToTime(dateTimeString: String): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[xxx][xx][X]")
            return OffsetDateTime.parse(dateTimeString, formatter).format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }
}