package com.franjmartin21.mydailytasks.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.mydailytasks.activity.util.UIUtil;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.data.viewmodel.TaskOccurrenceViewModel;
import com.franjmartin21.mydailytasks.service.MyDailyTaskService;

import java.util.Date;

public class EditTaskFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TASKOCCURRENCE_ID = "param_task_occurrence";
    //private static final String ARG_PARAM2 = "param2";

    private MyDailyTaskService service;

    private int mTaskOccurrenceId;

    private EditText mTitle;

    private EditText mGoalDate;

    private EditText mCompletedDate;

    private CheckBox mCompleted;

    private OnFragmentInteractionListener mListener;

    private UIUtil uiUtil;

    private TaskOccurrenceViewModel mTaskOccurrenceViewModel;

    public EditTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param taskOccurrenceId
     * @return A new instance of fragment EditTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTaskFragment newInstance(int taskOccurrenceId) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASKOCCURRENCE_ID, taskOccurrenceId);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = MyDailyTaskService.getInstance(getActivity().getApplicationContext());
        if (getArguments() != null) {
            mTaskOccurrenceId = getArguments().getInt(ARG_TASKOCCURRENCE_ID);
        }
        uiUtil = UIUtil.getInstance(getActivity());
        mTaskOccurrenceViewModel = ViewModelProviders.of(this).get(TaskOccurrenceViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_task, container, false);
        mTitle = v.findViewById(R.id.et_title);
        mGoalDate = v.findViewById(R.id.et_goaldate);
        mGoalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiUtil.openDateDialog(getActivity(), view);
            }
        });
        mGoalDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) uiUtil.openDateDialog(getActivity(), view);
            }
        });

        mCompletedDate = v.findViewById(R.id.et_completeddate);
        mCompletedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uiUtil.openDateDialog(getActivity(), view);
            }
        });
        mCompletedDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) uiUtil.openDateDialog(getActivity(), view);
            }
        });
        mCompleted = v.findViewById(R.id.cb_completed);
        mCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                changeCompleted(isChecked);
            }
        });
        loadData();
        return v;
    }

    private void changeCompleted(boolean isChecked) {
        if(isChecked){
            mCompletedDate.setEnabled(true);
            if(mCompletedDate.getText().toString().isEmpty()) mCompletedDate.setText(uiUtil.getStrFromDate(new Date()));
        } else{
            mCompletedDate.setEnabled(false);
            mCompletedDate.setText("");
        }
    }

    private void loadData(){
        TaskOccurrenceItem taskOccurrenceItem = mTaskOccurrenceViewModel.getTaskOcurrence(mTaskOccurrenceId);
        mTitle.setText(taskOccurrenceItem.getTitle());
        mGoalDate.setText(uiUtil.getStrFromDate(taskOccurrenceItem.getGoalDate()));
        if(taskOccurrenceItem.getCompletedDate() != null){
            mCompleted.setChecked(taskOccurrenceItem.getCompletedDate() != null);
            mCompletedDate.setText(uiUtil.getStrFromDate(taskOccurrenceItem.getCompletedDate()));
        }
        changeCompleted(mCompleted.isChecked());
    }

    public void save(){
        TaskOccurrenceItem taskOccurrenceItem = mTaskOccurrenceViewModel.getTaskOcurrenceItem();
        taskOccurrenceItem.setTitle(mTitle.getText().toString());
        taskOccurrenceItem.setCompletedDate(uiUtil.getStrFromDate(mCompletedDate.getText().toString()));
        taskOccurrenceItem.setGoalDate(uiUtil.getStrFromDate(mGoalDate.getText().toString()));
        service.saveTaskOccurrence(taskOccurrenceItem);
    }


    public void delete() {
        service.deleteTaskOccurrence(mTaskOccurrenceId);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mTaskOccurrenceViewModel.getTaskOcurrenceItem().setTitle(savedInstanceState.getCharSequence("title").toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("title", mTitle.getText().toString());
        outState.putCharSequence("goalDate", mGoalDate.getText().toString());
        outState.putCharSequence("completedDate", mGoalDate.getText().toString());
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
