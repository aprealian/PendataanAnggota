package id.mil.tni.android.pendataananggota.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.helper.PALog;

/**
 * Created by Aprilian Nur on 11/12/2017.
 */

public class DatePicker extends AbstractPicker {

    private IDatePickerListener mDatePickerListener;

    public interface IDatePickerListener {
        void onClear();
        void onOK(int year, int month, int day);
    }

    protected void setContentView(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = layoutInflater.inflate(R.layout.dialog_date_picker, null);
    }

    @Override
    protected void onClearViewClick() {
        if (mDatePickerListener != null) {
            mDatePickerListener.onClear();
        }
    }

    @Override
    protected void onOkViewClick() {

        if (mDatePickerListener != null) {
            if (mIsDayPickerAvailable) {
                mDatePickerListener.onOK(
                        mYears.get(mYearPicker.getValue()),
                        mMonths.get(mMonthPicker.getValue()),
                        mDays.get(mDayPicker.getValue())
                );
                //PALog.e("tes ini 1 : "+mYears+mMonths+mDays);
            } else {
                mDatePickerListener.onOK(
                        mYears.get(mYearPicker.getValue()),
                        mMonths.get(mMonthPicker.getValue()),
                        1
                );
                //PALog.e("tes ini 2 : "+mYears+mMonths+mDays);
            }
        }

        //PALog.e("tes ini 3 : "+mYears+mMonths+mDays);
    }

    public DatePicker(Context context) {
        this(context, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    public DatePicker(Context context, int year, int month) {
        this(context, year, month, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public DatePicker(Context context, int year, int month, int day) {
        initialize(context, year, month, day, getDefaultYears(), getDefaultMonths());
    }

    public DatePicker(Context context, ArrayList<Integer> years, ArrayList<Integer> months) {
        this(context, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, years, months);
    }

    public DatePicker(Context context, int year, int month, ArrayList<Integer> years, ArrayList<Integer> months) {
        this(context, year, month, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), years, months);
    }

    public DatePicker(Context context, int year, int month, int day, ArrayList<Integer> years, ArrayList<Integer> months) {
        initialize(context, year, month, day, years, months);
    }

    public void setDatePickerListener(IDatePickerListener datePickerListener) {
        mDatePickerListener = datePickerListener;
    }

}