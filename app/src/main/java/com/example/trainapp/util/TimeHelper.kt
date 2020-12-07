package com.example.trainapp.util

import java.time.Duration
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class TimeHelper {
    companion object {
        fun dateTimeToTime(dateTimeString: String?): String? {
            if (dateTimeString == null) return null
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[xxx][xx][X]")
            return OffsetDateTime.parse(dateTimeString, formatter).format(DateTimeFormatter.ofPattern("HH:mm"))
        }

        fun calculateDelay(plannedTime: String?, actualTime: String?): Long? {
            if (plannedTime == null || actualTime == null) return null
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val planned = LocalTime.parse(plannedTime, formatter)
            val actual = LocalTime.parse(actualTime, formatter)
            return Duration.between(planned, actual).toMinutes()
        }
    }
}