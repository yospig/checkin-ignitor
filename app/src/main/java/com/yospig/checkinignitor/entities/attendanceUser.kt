package com.yospig.checkinignitor.entities

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
