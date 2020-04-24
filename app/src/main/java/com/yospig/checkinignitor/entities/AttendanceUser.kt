package com.yospig.checkinignitor.entities

import java.sql.Timestamp

data class AttendanceUser(
    val user: String,
    val date: List<AttendanceOfADay>)

data class AttendanceOfADay(
    val date: String,
    val inTime: List<AttendanceInTime>,
    val outTime: List<AttendanceOutTime>
)

data class AttendanceInTime(
    val inHour: Int,
    val inMin: Int,
    val inTimeStr: String
)

data class AttendanceOutTime(
    val outHour: Int,
    val outMin: Int,
    val outTimeStr: String
)

data class AttendanceUserCheckInTime(
    val in_hour: String,
    val in_min: String,
    val in_time_str: String,
    val timestamp: Timestamp
)

data class AttendanceUserCheckOutTime(
    val out_hour: String,
    val out_min: String,
    val out_time_str: String,
    val timestamp: Timestamp
)
