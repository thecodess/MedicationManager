package com.apps.thecodess.medicationmanger;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.thecodess.medicationmanger.adapter.MonthlyCategoryListAdapter;
import com.apps.thecodess.medicationmanger.monthlycategory.MonthlyCategoryContract;
import com.apps.thecodess.medicationmanger.monthlycategory.MonthlyCategoryPresenter;

import java.util.ArrayList;
import java.util.List;


public class MonthlyCategoryFragment extends Fragment implements MonthlyCategoryContract.View {


    private MonthlyCategoryPresenter mPresenter;
    private MonthlyCategoryFragment.OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private List<String[]> monthList;
    private MonthlyCategoryListAdapter mAdapter;
    private TextView noCategoriesYetTextView;

    public MonthlyCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_category, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);
        noCategoriesYetTextView = (TextView) view.findViewById(R.id.textview_no_categories_yet);
        mPresenter = new MonthlyCategoryPresenter(this, getContext());

        initRecyclerView();

        mPresenter.prepareMedication();

        return view;

    }

    private void initRecyclerView(){

        monthList = new ArrayList<>();
        mAdapter = new MonthlyCategoryListAdapter(getContext(), monthList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

    }


    /**
     * Displays the appropriate views based on data availability
     */
    @Override
    public void serveViews(){

        if(monthList.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            noCategoriesYetTextView.setVisibility(View.GONE);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            noCategoriesYetTextView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_add_medication).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void clearList() {
        monthList.clear();
    }

    @Override
    public void addToList(String month, String year) {
        monthList.add(new String[]{month, year});
    }

    @Override
    public void notifyAdapter() {
        mAdapter.notifyDataSetChanged();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
