package com.yospig.checkinignitor

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_check_inout.*
import java.util.*

class CheckInOutActivity: FragmentActivity(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_inout)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val dateStr = String.format(Locale.US, "%d/%d/%d", year,month+1,dayOfMonth)
        dateView.text = dateStr
    }

    fun showDatePickerDialog(v: View){
        val newFragment = DatePick()
        newFragment.show(supportFragmentManager,"datePicker")
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val timeStr = String.format(Locale.US, "%d:%d", hourOfDay,minute)
        timeView.text = timeStr
    }

    fun showTimePickerDialog(v: View) {
        val newFragment = TimePick()
        newFragment.show(supportFragmentManager, "timePicker")
    }
}
