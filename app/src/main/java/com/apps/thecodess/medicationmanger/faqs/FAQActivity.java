package com.apps.thecodess.medicationmanger.faqs;

import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.apps.thecodess.medicationmanger.R;
import com.apps.thecodess.medicationmanger.utils.BaseActivity;

/**
 * This view shows possible frequently asked questions about the app
 */
public class FAQActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

}
