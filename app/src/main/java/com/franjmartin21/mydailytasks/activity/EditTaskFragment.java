package com.franjmartin21.mydailytasks.activity;

import android.arch.lifecycle.ViewModelProvider;
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

import com.franjmartin21.mydailytasks.MyDailyTasksApplication;
import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.mydailytasks.activity.util.UIUtil;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.data.viewmodel.TaskOccurrenceListViewModel;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

public class EditTaskFragment extends Fragment {

    public enum Mode{
        ADD,
        EDIT
    }

    private static final String ARG_MODE = "param_mode";
    private static final String ARG_TASKOCCURRENCE_ID = "param_task_occurrence";


    //It sets the mode of the current screen
    private Mode mode;
    //Mantains reference to the current taskOccurrence id that we are editing
    private int mTaskOccurrenceId;

    //Visual components of the Fragment
    private EditText mTitle;

    private EditText mGoalDate;

    private EditText mCompletedDate;

    private CheckBox mCompleted;

    private OnFragmentInteractionListener mListener;

    private UIUtil uiUtil;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TaskOccurrenceListViewModel mTaskOccurrenceListViewModel;

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
    public static EditTaskFragment newInstance(String mode, int taskOccurrenceId) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASKOCCURRENCE_ID, taskOccurrenceId);
        args.putString(ARG_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTaskOccurrenceListViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskOccurrenceListViewModel.class);
        loadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = Mode.valueOf(getArguments().getString(ARG_MODE));
            mTaskOccurrenceId = getArguments().getInt(ARG_TASKOCCURRENCE_ID);
        }
        ((MyDailyTasksApplication)getActivity().getApplication()).getApplicationComponent().inject(this);
        uiUtil = UIUtil.getInstance(getActivity());
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
        TaskOccurrenceItem taskOccurrenceItem = mTaskOccurrenceListViewModel.getTaskOccurrence(mTaskOccurrenceId);
        mTitle.setText(taskOccurrenceItem.getTitle());
        mGoalDate.setText(uiUtil.getStrFromDate(taskOccurrenceItem.getGoalDate()));
        if(taskOccurrenceItem.getCompletedDate() != null){
            mCompleted.setChecked(taskOccurrenceItem.getCompletedDate() != null);
            mCompletedDate.setText(uiUtil.getStrFromDate(taskOccurrenceItem.getCompletedDate()));
        }
        changeCompleted(mCompleted.isChecked());
    }

    public void save(){
        TaskOccurrenceItem taskOccurrenceItem = mTaskOccurrenceListViewModel.getTaskOccurrence(mTaskOccurrenceId);
        taskOccurrenceItem.setTitle(mTitle.getText().toString());
        taskOccurrenceItem.setCompletedDate(uiUtil.getDateFromStr(mCompletedDate.getText().toString()));
        taskOccurrenceItem.setGoalDate(uiUtil.getDateFromStr(mGoalDate.getText().toString()));
        mTaskOccurrenceListViewModel.updateTaskOccurrence(taskOccurrenceItem);
    }


    public void delete() {
        mTaskOccurrenceListViewModel.deleteTaskOccurrence(mTaskOccurrenceId);
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
