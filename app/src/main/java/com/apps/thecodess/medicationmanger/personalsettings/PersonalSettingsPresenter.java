package com.apps.thecodess.medicationmanger.personalsettings;

import android.content.Context;
import android.content.Intent;

import com.apps.thecodess.medicationmanger.account.AccountActivity;
import com.apps.thecodess.medicationmanger.contact.ContactActivity;
import com.apps.thecodess.medicationmanger.faqs.FAQActivity;
import com.apps.thecodess.medicationmanger.medication.TodaysMedicationActivity;
import com.apps.thecodess.medicationmanger.model.preferences.SharedPrefContract;
import com.apps.thecodess.medicationmanger.model.preferences.SharedPrefHelper;
import com.apps.thecodess.medicationmanger.model.sqlite.MedicationDBHelper;
import com.apps.thecodess.medicationmanger.profile.ProfileActivity;


/**
 * Responsible for handling actions from the view and updating the UI as required
 */
public class PersonalSettingsPresenter implements PersonalSettingsContract.Presenter {

    private Context mContext;
    private PersonalSettingsContract.View mView;
    private SharedPrefHelper mSharedPreferences;

    public PersonalSettingsPresenter(PersonalSettingsContract.View view, Context context){
        mContext = context;
        mView = view;
        mSharedPreferences = new SharedPrefHelper(context);
    }

    @Override
    public void launchContactActivity() {
        //goes to contact me page
        Intent mIntent = new Intent(mContext, ContactActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    public void launchFAQActivity() {
        //goes to ic_question_mark  page
        Intent mIntent = new Intent(mContext, FAQActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    public void launchEditProfileActivity() {
        //goes to edit profile page
        Intent mIntent = new Intent(mContext, ProfileActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    public void launchAccountActivity() {
        //goes to ic_account page
        Intent mIntent = new Intent(mContext, AccountActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    public void launchTodaysMedsActivity() {
        //goes to todays medications page
        Intent mIntent = new Intent(mContext, TodaysMedicationActivity.class);
        mContext.startActivity(mIntent);
    }



    @Override
    public void toggleNotificationsOnOff(boolean notifyMe) {

        //edit shared preferences - notification status
        mSharedPreferences.putBoolean(SharedPrefContract.PREF_NOTIFICATION_TURNED_ON, notifyMe);

        String notificationStatus;

        if(notifyMe){
            notificationStatus = "on";
        }else{
            notificationStatus = "off";
        }

        String userID = mSharedPreferences.getUserID();

        MedicationDBHelper medicationDBHelper = new MedicationDBHelper(mContext);

        //update DB
        medicationDBHelper.updateUserNotification(userID, notificationStatus);

        //close the db connection
        medicationDBHelper.close();

    }

    @Override
    public void checkUncheckNotificationSwitch() {
        //checks the sharedpref to see if user has set notify to true or false, then updates ui
        boolean notificationStatus = mSharedPreferences.isNotificationOn();
        mView.initNotificationSwitch(notificationStatus);
    }

}
