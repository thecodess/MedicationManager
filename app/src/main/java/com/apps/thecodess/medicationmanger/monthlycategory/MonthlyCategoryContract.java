package com.apps.thecodess.medicationmanger.monthlycategory;




public interface MonthlyCategoryContract {

    interface View{
        void serveViews();
        void clearList();
        void addToList(String month, String year);
        void notifyAdapter();
    }

    interface Presenter{
        void prepareMedication();
    }
}
