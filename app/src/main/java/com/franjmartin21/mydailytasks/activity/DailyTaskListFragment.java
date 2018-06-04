package com.franjmartin21.mydailytasks.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.franjmartin21.mydailytasks.MyDailyTasksApplication;
import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.mydailytasks.activity.adapter.DailyTaskAdapter;
import com.franjmartin21.mydailytasks.activity.util.UIUtil;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;
import com.franjmartin21.mydailytasks.data.viewmodel.TaskOccurrenceListViewModel;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

public class DailyTaskListFragment extends Fragment implements DailyTaskAdapter.ListItemClickListener {

    private static final String ARG_DAILYTASK_LONGDATE = "param_dailytask_longdate";

    private Date mCurrentDate;
    //List of components in the page
    private View mLayout;
    private TextView mDate;
    private ImageView mPrevious;
    private ImageView mNext;
    private EditText mNewTaskText;
    private FloatingActionButton mAddTaskToList;
    private RecyclerView mListTasks;
    private DailyTaskAdapter mDailyTaskAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TaskOccurrenceListViewModel mTaskOccurrenceListViewModel;

    private OnItemClickedListener mListener;

    private UIUtil uiUtil;

    public DailyTaskListFragment() {
        // Required empty public constructor
    }

    public static DailyTaskListFragment newInstance(Long longDate) {
        DailyTaskListFragment fragment = new DailyTaskListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DAILYTASK_LONGDATE, longDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(DailyTaskListFragment.class.getClass().getSimpleName(), "onCreate");
        if (getArguments() != null) {
            mCurrentDate = new Date(getArguments().getLong(ARG_DAILYTASK_LONGDATE));
        }
        uiUtil = UIUtil.getInstance(getActivity());
        ((MyDailyTasksApplication)getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTaskOccurrenceListViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskOccurrenceListViewModel.class);
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(DailyTaskListFragment.class.getClass().getSimpleName(), "onCreateView");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily_task_list, container, false);
        mLayout = v.findViewById(R.id.layout_daily_fragment);
        mDate = v.findViewById(R.id.tv_date);
        mListTasks = v.findViewById(R.id.rv_list_tasks);
        mNewTaskText = v.findViewById(R.id.et_new_task);
        mNewTaskText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTaskOccurrenceListViewModel.setInsertType(s.length() > 0? TaskOccurrenceListViewModel.InsertType.QUICK: TaskOccurrenceListViewModel.InsertType.REGULAR);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mAddTaskToList = v.findViewById(R.id.btn_add_task_to_list);
        mAddTaskToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTaskOccurrenceListViewModel.getInsertType().getValue() == TaskOccurrenceListViewModel.InsertType.QUICK)
                    insertNewTask();
                else
                    mListener.onAddTaskClicked();
            }
        });

        mPrevious = v.findViewById(R.id.btn_previous);
        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPreviousBtnClicked();
                //setDate(-1);
            }
        });
        mNext = v.findViewById(R.id.btn_next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNextBtnClicked();
                //setDate(1);
            }
        });
        informDateText();
        addRecyclerViewSetup(v);
        return v;
    }

    private void changeSendButtonAction(TaskOccurrenceListViewModel.InsertType insertType){
        if(insertType == TaskOccurrenceListViewModel.InsertType.QUICK){
            mAddTaskToList.setImageResource(R.drawable.ic_send_white);
        } else{
            mAddTaskToList.setImageResource(R.drawable.ic_playlist_add);
        }
    }

    private void informDateText(){
        Log.d(this.getClass().getSimpleName(), "informDateText");
        mDate.setText(uiUtil.getStrFromDate(mCurrentDate));
        if(mDate.getText().toString().equals(uiUtil.getStrFromDate(new Date()))) {
            mDate.setText(getString(R.string.daily_task_today_label) + " - " + uiUtil.getStrFromDate(mCurrentDate));
            mDate.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void loadData(){
        mTaskOccurrenceListViewModel.getTaskOccurrenceItemList(mCurrentDate).observe(this, new Observer<List<TaskOccurrenceItem>>() {
            //mTaskOccurrenceViewModel.getAllTaskOccurrences().observe(this, new Observer<List<TaskOccurrenceItem>>() {
            @Override
            public void onChanged(@Nullable List<TaskOccurrenceItem> taskOccurrenceItems) {
                Log.d(DailyTaskListFragment.class.getClass().getSimpleName(), "onChanged");
                mDailyTaskAdapter.setTaskOccurrenceItemList(taskOccurrenceItems);
                mDailyTaskAdapter.notifyDataSetChanged();
            }
        });
        mTaskOccurrenceListViewModel.getInsertType().observe(this, new Observer<TaskOccurrenceListViewModel.InsertType>() {
            @Override
            public void onChanged(@Nullable TaskOccurrenceListViewModel.InsertType insertType) {
                changeSendButtonAction(insertType);
            }
        });
    }

    private void insertNewTask(){
        if(!mNewTaskText.getText().toString().isEmpty()) {
            TaskOccurrenceItem taskOccurrenceItem = new TaskOccurrenceItem();
            taskOccurrenceItem.setTitle(mNewTaskText.getText().toString());
            taskOccurrenceItem.setGoalDate(mCurrentDate);
            mTaskOccurrenceListViewModel.insertTaskOccurrence(taskOccurrenceItem);
        }else
            Snackbar.make(mLayout, getString(R.string.validation_empty_new_task_text), Snackbar.LENGTH_SHORT).show();

        mNewTaskText.setText("");
    }

    private void addRecyclerViewSetup(View v) {
        mListTasks = v.findViewById(R.id.rv_list_tasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mListTasks.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecorationAssessment = new DividerItemDecoration(mListTasks.getContext(),DividerItemDecoration.VERTICAL);
        mListTasks.addItemDecoration(dividerItemDecorationAssessment);
        mListTasks.setAdapter(mDailyTaskAdapter);
        mListTasks.setHasFixedSize(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDailyTaskAdapter = new DailyTaskAdapter(null,this);
        if (context instanceof OnItemClickedListener) {
            mListener = (OnItemClickedListener) context;
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
    public void onListItemClick(int termId) {
        mListener.onItemClicked(termId);
    }

    @Override
    public void onListItemClickCheckBox(int taskOcurrenceId, boolean isChecked) {
        TaskOccurrenceItem taskOccurrenceItem = mTaskOccurrenceListViewModel.getTaskOccurrence(taskOcurrenceId);
        taskOccurrenceItem.setCompletedDate(new Date());
        mTaskOccurrenceListViewModel.updateTaskOccurrence(taskOccurrenceItem);
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
    public interface OnItemClickedListener {
        // TODO: Update argument type and name
        void onItemClicked(int itemId);

        void onPreviousBtnClicked();

        void onNextBtnClicked();

        void onAddTaskClicked();
    }
}
