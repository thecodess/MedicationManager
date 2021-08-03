package com.apps.thecodess.medicationmanger.medication;


import com.apps.thecodess.medicationmanger.model.Medication;

public interface TodaysMedicationContract {

    interface View{

        void clearMedicationsList();
        void notifyAdapter();
        void addToMedicationsList(Medication medication);

    }

    interface Presenter{

        void prepareMedications();
    }

}
