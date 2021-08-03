package com.apps.thecodess.medicationmanger.onboarding;


public interface OnboardingContract {

    interface View{
        void goToLogin();
        void setFullScreen();
        void addBottomDots(int position);
        void setCurrentViewPagerItem(int current);
        int getItem(int i);
    }

    interface Presenter{
        void checkInstall();
        void onSkipClick();
        void onNextClick(int numberOfScreens);
    }

}
