package com.apps.thecodess.medicationmanger.medication;



import androidx.recyclerview.widget.RecyclerView;

import com.apps.thecodess.medicationmanger.model.Medication;


/**
 * This interface defines the contract between {@link MedicationListContract} and {@link MedicationListPresenter}
 */
public interface MedicationListContract {

    interface View{

        void clearMedicationsList();
        void notifyAdapter();
        void addToMedicationsList(Medication medication);
        void removeAdapterItem(int position);
        Medication getMedication(int position);
        void serveViews();

    }

    interface Presenter{

        void prepareMedications();
        void handleSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
