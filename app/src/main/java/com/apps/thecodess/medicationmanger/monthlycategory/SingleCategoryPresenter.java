package com.apps.thecodess.medicationmanger.monthlycategory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import androidx.recyclerview.widget.RecyclerView;

import com.apps.thecodess.medicationmanger.AlarmReceiver;
import com.apps.thecodess.medicationmanger.adapter.MedicationListAdapter;
import com.apps.thecodess.medicationmanger.model.Medication;
import com.apps.thecodess.medicationmanger.model.preferences.SharedPrefHelper;
import com.apps.thecodess.medicationmanger.model.sqlite.MedicationDBContract;
import com.apps.thecodess.medicationmanger.model.sqlite.MedicationDBHelper;


/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class SingleCategoryPresenter implements SingleCategoryContract.Presenter{

    private SingleCategoryContract.View mView;
    private Context mContext;
    private String month, year;

    public SingleCategoryPresenter(SingleCategoryContract.View view, Context context, String _month, String _year){
        mView = view;
        mContext = context;
        month = _month;
        year = _year;
    }

    @Override
    public void prepareMedication() {

        mView.clearList();  //clear list

        //get logged in user using shared preferences
        SharedPrefHelper mSharedPrefHelper = new SharedPrefHelper(mContext);
        String userID = mSharedPrefHelper.getUserID();

        //make call to db to fetch medications
        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

        Cursor cursor = medicationDBHelper.readMedicationsForCategory(month,year, userID);

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.NAME));
            String description = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.DESCRIPTION));
            int frequencyOrInterval = cursor.getInt(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.INTERVAL));
            String startDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_DATE));
            String startTime = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.START_TIME));
            String endDate = cursor.getString(cursor.getColumnIndex(MedicationDBContract.MedicationEntry.END_DATE));

            mView.addToList(id, userID, name, description, frequencyOrInterval, startDate,startTime, endDate);

        }

        cursor.close();

        mView.notifyAdapter();  //notify recyclerview adapter of change
    }

    /**
     * Handles the swipe to delete gesture
     * @param viewHolder
     * @param direction
     * @param position
     */
    @Override
    public void handleSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof MedicationListAdapter.MyViewHolder) {

            //get medication to be deleted
            Medication medication = mView.getMedication(viewHolder.getAdapterPosition());

            //make call to db to delete medication
            MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

            medicationDBHelper.deleteMedication(medication.getId());

            //close the db connection
            medicationDBHelper.close();

            // remove the item from recycler view
            mView.removeItem(viewHolder.getAdapterPosition());
            cancelAlarm(medication.getId());    //cancel deleted medication

        }
    }

    /**
     * Helper method to cancel an alarm after deleting the medication it belongs to
     * @param id
     */
    private void cancelAlarm(int id){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("MEDICATION_ID", id);
        intent.setAction(""+System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
