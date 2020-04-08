package com.yospig.checkinignitor

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_check_inout.*
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CheckInOutActivity: FragmentActivity(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private val TAG = "CheckinOutActivity"
    var dateStr = ""
    var timeStr = ""
    private lateinit var functions: FirebaseFunctions

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_inout)

        val inButton: Button = findViewById(R.id.check_in)
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        dateStr =  currentDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        timeStr =  currentDateTime.format(DateTimeFormatter.ofPattern("hh:mm"))
        val date:TextView = findViewById(R.id.dateView)
        date.text = dateStr
        val time:TextView = findViewById(R.id.timeView)
        time.text = timeStr

        inButton.setOnClickListener {
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        dateStr = String.format(Locale.US, "%d/%02d/%02d", year,month+1,dayOfMonth)
        dateView.text = dateStr
    }

    fun showDatePickerDialog(v: View){
        val newFragment = DatePick()
        newFragment.show(supportFragmentManager,"datePicker")
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        timeStr = String.format(Locale.US, "%02d:%02d", hourOfDay,minute)
        timeView.text = timeStr
    }

    fun showTimePickerDialog(v: View) {
        val newFragment = TimePick()
        newFragment.show(supportFragmentManager, "timePicker")
    }


}
