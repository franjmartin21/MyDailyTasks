package com.franjmartin21.mydailytasks.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.mydailytasks.activity.adapter.DailyTaskAdapter;
import com.franjmartin21.mydailytasks.data.dao.TaskOcurrenceDao;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrenceItem;

import java.util.ArrayList;
import java.util.List;

public class DailyTaskListFragment extends Fragment implements DailyTaskAdapter.ListItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //List of components in the page
    private FloatingActionButton mAddTaskToList;
    private RecyclerView mListTasks;
    private DailyTaskAdapter mDailyTaskAdapter;

    private OnFragmentInteractionListener mListener;

    public DailyTaskListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyTaskListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyTaskListFragment newInstance(String param1, String param2) {
        DailyTaskListFragment fragment = new DailyTaskListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily_task_list, container, false);
        mListTasks = v.findViewById(R.id.rv_list_tasks);
        mAddTaskToList = v.findViewById(R.id.btn_add_task_to_list);
        mAddTaskToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DailyTaskListFragment.this.getContext(), "click button", Toast.LENGTH_LONG).show();
            }
        });

        addRecyclerViewSetup(v);
        return v;
    }

    private void addRecyclerViewSetup(View v) {
        mListTasks = v.findViewById(R.id.rv_list_tasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mListTasks.setLayoutManager(layoutManager);
        mDailyTaskAdapter = new DailyTaskAdapter(getDummyData(), this);
        DividerItemDecoration dividerItemDecorationAssessment = new DividerItemDecoration(mListTasks.getContext(),DividerItemDecoration.VERTICAL);
        mListTasks.addItemDecoration(dividerItemDecorationAssessment);
        mListTasks.setAdapter(mDailyTaskAdapter);
        mListTasks.setHasFixedSize(true);
    }

    private List<TaskOcurrenceItem> getDummyData(){
        TaskOcurrenceItem taskOcurrenceItem = new TaskOcurrenceItem();
        taskOcurrenceItem.setTitle("This is my dummy data for the first task");
        TaskOcurrenceItem taskOcurrenceItem2 = new TaskOcurrenceItem();
        taskOcurrenceItem2.setTitle("This is my second dummy data for the first task and I want this one to be longer in size");
        List<TaskOcurrenceItem> taskOcurrenceItemList = new ArrayList<>();
        taskOcurrenceItemList.add(taskOcurrenceItem);
        taskOcurrenceItemList.add(taskOcurrenceItem2);
        return taskOcurrenceItemList;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /**
         *
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
         */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(int termId) {

    }

    @Override
    public void onListItemClickCheckBox(int termId) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
