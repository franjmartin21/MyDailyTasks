package com.franjmartin21.mydailytasks.activity.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.mydailytasks.data.entity.TaskOccurrenceItem;

import java.util.List;

public class DailyTaskAdapter extends RecyclerView.Adapter<DailyTaskAdapter.DailyTaskAdapterViewHolder> {

    private List<TaskOccurrenceItem> taskOccurrenceItemList;

    private ListItemClickListener mOnClickListener;

    public DailyTaskAdapter(List<TaskOccurrenceItem> taskOccurrenceItemList, ListItemClickListener listener){
        this.taskOccurrenceItemList = taskOccurrenceItemList;
        this.mOnClickListener = listener;
    }

    @Override
    public DailyTaskAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListTerm = R.layout.daily_task_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachParentImmediately = false;
        View view = inflater.inflate(layoutIdForListTerm, parent, shouldAttachParentImmediately);
        DailyTaskAdapterViewHolder termAdapterViewHolder = new DailyTaskAdapterViewHolder(view);

        return termAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(final DailyTaskAdapterViewHolder holder, int position) {

        if(taskOccurrenceItemList != null && taskOccurrenceItemList.size()<=position) return;

        holder.bind(taskOccurrenceItemList.get(position), mOnClickListener);
    }

    @Override
    public void onViewRecycled(@NonNull DailyTaskAdapterViewHolder holder) {
        super.onViewRecycled(holder);
        holder.mCompleted.setOnCheckedChangeListener(null);
    }

    @Override
    public int getItemCount() {
        return taskOccurrenceItemList != null ? taskOccurrenceItemList.size():0;
    }

    public interface ListItemClickListener{
        void onListItemClick(int termId);

        void onListItemClickCheckBox(int taskOccurrenceId, boolean isChecked);
    }

    public List<TaskOccurrenceItem> getTaskOccurrenceItemList() {
        return taskOccurrenceItemList;
    }

    public void setTaskOccurrenceItemList(List<TaskOccurrenceItem> taskOccurrenceItemList) {
        this.taskOccurrenceItemList = taskOccurrenceItemList;
    }

    class DailyTaskAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private int MAX_LENGHT_TITLE = 100;

        private int mItemId;

        private TextView mDailyTaskTitle;

        private CheckBox mCompleted;

        public DailyTaskAdapterViewHolder(View itemView) {
            super(itemView);

            mDailyTaskTitle = itemView.findViewById(R.id.tv_tasktitle);
            mCompleted = itemView.findViewById(R.id.cb_completed);
        }

        public void bind(final TaskOccurrenceItem taskOccurrenceItem, final ListItemClickListener listener) {
            mItemId = taskOccurrenceItem.getOccurrenceId();
            mDailyTaskTitle.setText(taskOccurrenceItem.getTitle().length() > MAX_LENGHT_TITLE ? taskOccurrenceItem.getTitle().substring(0, MAX_LENGHT_TITLE) + "...": taskOccurrenceItem.getTitle());
            setItemChecked(taskOccurrenceItem.getCompletedDate() != null);
            mCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    listener.onListItemClickCheckBox(mItemId, isChecked);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onListItemClick(mItemId);
                }
            });
        }

        private void setItemChecked(boolean isChecked){
            if(isChecked){
                mCompleted.setChecked(true);
                mDailyTaskTitle.setPaintFlags(mDailyTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else{
                mCompleted.setChecked(false);
                mDailyTaskTitle.setPaintFlags(0);
            }
        }

            @Override
        public void onClick(View view) {
            mOnClickListener.onListItemClick(mItemId);
        }
    }
}
