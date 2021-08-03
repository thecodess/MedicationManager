package com.apps.thecodess.medicationmanger.medication;


import android.content.Context;
import android.database.Cursor;

import com.apps.thecodess.medicationmanger.model.sqlite.MedicationDBContract;
import com.apps.thecodess.medicationmanger.model.sqlite.MedicationDBHelper;
import com.apps.thecodess.medicationmanger.utils.Utility;

import java.util.List;


/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class SingleMedicationPresenter implements SingleMedicationContract.Presenter{

    private SingleMedicationContract.View mView;
    private Context mContext;


    public SingleMedicationPresenter(SingleMedicationContract.View view, Context context){
        mView = view;
        mContext = context;

    }

    @Override
    public void getMedicationData(int medicationID) {

        final MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

        Cursor cursor = medicationDBHelper.readSingleMedication(medicationID);

        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.NAME));
        String description = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.DESCRIPTION));
        int interval = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.INTERVAL));
        String startDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_DATE));
        String startTime = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_TIME));
        String endDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.END_DATE));

        cursor.close();

        //close the connection
        medicationDBHelper.close();

        mView.setToolbarTitle(name);

        List<List<String>> medicationTimes = Utility.getMedicationTimes(startDate, startTime, endDate, interval);


        mView.setMedicationDescription(description);
        mView.setMedicationInterval(interval, startDate, endDate);
        mView.setMedicationStartDate(startDate);
        mView.setMedicationStartTime(startTime);
        mView.setMedicationEndDate(endDate);

        mView.clearMedicationsList();
        mView.addToMedicationsList(medicationTimes);
        mView.notifyAdapter();
    }
}
