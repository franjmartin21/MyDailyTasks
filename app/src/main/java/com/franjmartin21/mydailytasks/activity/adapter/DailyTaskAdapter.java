package com.franjmartin21.mydailytasks.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.mydailytasks.data.entity.TaskOcurrenceItem;

import java.util.List;

public class DailyTaskAdapter extends RecyclerView.Adapter<DailyTaskAdapter.DailyTaskAdapterViewHolder> {

    private List<TaskOcurrenceItem> taskOcurrenceItemList;

    private ListItemClickListener mOnClickListener;

    public DailyTaskAdapter(List<TaskOcurrenceItem> taskOcurrenceItemList, ListItemClickListener listener){
        this.taskOcurrenceItemList = taskOcurrenceItemList;
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

        if(taskOcurrenceItemList.size()<=position) return;

        TaskOcurrenceItem taskOcurrenceItem = taskOcurrenceItemList.get(position);
        holder.mDailyTaskTitle.setText(taskOcurrenceItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return taskOcurrenceItemList.size();
    }

    public interface ListItemClickListener{
        void onListItemClick(int termId);

        void onListItemClickCheckBox(int termId);
    }


    class DailyTaskAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private int mItemId;

        private TextView mDailyTaskTitle;

        private CheckBox mCompleted;

        public DailyTaskAdapterViewHolder(View itemView) {
            super(itemView);
            mDailyTaskTitle = itemView.findViewById(R.id.tv_tasktitle);
            mCompleted = itemView.findViewById(R.id.cb_completed);
/*

            itemView.setOnClickListener(this);
            mTermDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.onListItemClickDelete(mTermId);
                }
            });

            mTermEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.onListItemClickEdit(mTermId);
                }
            });
            */
        }

        @Override
        public void onClick(View view) {
            //mOnClickListener.onListItemClick(mTermId);
        }
    }
}
