package com.franjmartin21.mydailytasks.activity.adapter;

import android.content.Context;
import android.graphics.Paint;
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
    public void onBindViewHolder(DailyTaskAdapterViewHolder holder, int position) {

        if(taskOccurrenceItemList != null && taskOccurrenceItemList.size()<=position) return;

        TaskOccurrenceItem taskOccurrenceItem = taskOccurrenceItemList.get(position);
        holder.mItemId = taskOccurrenceItem.getOccurrenceId();
        holder.mDailyTaskTitle.setText(taskOccurrenceItem.getTitle().length() > holder.MAX_LENGHT_TITLE ? taskOccurrenceItem.getTitle().substring(0, holder.MAX_LENGHT_TITLE) + "...": taskOccurrenceItem.getTitle());
        setItemChecked(holder, taskOccurrenceItem.getCompletedDate() != null);

    }

    private void setItemChecked(DailyTaskAdapterViewHolder holder, boolean isChecked){
        if(isChecked){
            holder.mCompleted.setChecked(true);
            holder.mDailyTaskTitle.setPaintFlags(holder.mDailyTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else{
            holder.mCompleted.setChecked(false);
            holder.mDailyTaskTitle.setPaintFlags(0);
        }
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
            mCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    mOnClickListener.onListItemClickCheckBox(mItemId, isChecked);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.onListItemClick(mItemId);
                }
            });
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListItemClick(mItemId);
        }
    }
}
