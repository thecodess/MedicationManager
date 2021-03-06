package com.apps.thecodess.medicationmanger.splash;


/**
 * Defines the contract between the View {@link MainActivity} and the presenter {@link MainPresenter}
 */
public interface MainContract {

    interface View{
        void splashComplete();
    }

    interface Presenter{
        void splashDisplay();
    }

}
