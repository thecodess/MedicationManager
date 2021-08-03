package com.apps.thecodess.medicationmanger.medication;

import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.thecodess.medicationmanger.R;
import com.apps.thecodess.medicationmanger.adapter.TodaysMedicationListAdapter;
import com.apps.thecodess.medicationmanger.model.Medication;
import com.apps.thecodess.medicationmanger.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TodaysMedicationActivity extends BaseActivity implements TodaysMedicationContract.View{

    private RecyclerView recyclerView;
    private List<Medication> medicationList;
    private TodaysMedicationListAdapter mAdapter;
    private TextView noMedsYetTextView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_medication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.medicationRecyclerView);
        medicationList = new ArrayList<>();
        mAdapter = new TodaysMedicationListAdapter(medicationList);
        noMedsYetTextView = (TextView) findViewById(R.id.textview_no_meds_yet);


        //Initialise recycler view and its properties
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TodaysMedicationActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(TodaysMedicationActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        TodaysMedicationPresenter medicationListPresenter = new TodaysMedicationPresenter(this, TodaysMedicationActivity.this);
        medicationListPresenter.prepareMedications();

        serveViews();

    }


    /**
     * Displays the appropriate views based on data availability
     */
    public void serveViews(){

        if(medicationList.size() > 0){
            //display recycler view if there are medications
            recyclerView.setVisibility(View.VISIBLE);
            noMedsYetTextView.setVisibility(View.GONE);
        }
        else{
            //display text saying there are no medications ifthere are medications
            recyclerView.setVisibility(View.GONE);
            noMedsYetTextView.setVisibility(View.VISIBLE);
        }

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

    @Override
    public void clearMedicationsList() {
        medicationList.clear();
    }

    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addToMedicationsList(Medication medication) {
        medicationList.add(medication);
    }



}
