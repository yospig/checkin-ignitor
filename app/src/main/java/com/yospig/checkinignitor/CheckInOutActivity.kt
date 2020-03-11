package com.yospig.checkinignitor

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_check_inout.*
import java.util.*

class CheckInOutActivity: FragmentActivity(),DatePickerDialog.OnDateSetListener {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_inout)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val yyyymmddStr = String.format(Locale.US, "%d/%d/%d", year,month+1,dayOfMonth)
        dateView.text = yyyymmddStr
    }

    fun showDatePickerDialog(v: View){
        val newFragment = DatePick()
        newFragment.show(supportFragmentManager,"datePicker")
    }
}
