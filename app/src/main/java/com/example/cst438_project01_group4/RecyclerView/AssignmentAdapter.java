package com.example.cst438_project01_group4.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cst438_project01_group4.ClassObjects.Assignment;
import com.example.cst438_project01_group4.R;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> mAssignmentsList;
    private ItemClickListener clickListener;
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.my_recycler_text_view, parent, false);
        // Return a new holder instance
        AssignmentAdapter.ViewHolder viewHolder = new AssignmentAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    /**
     * updates the objects on the recycler view
     * @param assignments
     */
    public void setData(List<Assignment> assignments){
        mAssignmentsList = assignments;
        notifyDataSetChanged();
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(AssignmentAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Assignment resItem = mAssignmentsList.get(position);
        // Set item views based on your views and data model
        TextView textView = viewHolder.logTextView;
        textView.setText(resItem.toString());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mAssignmentsList.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView logTextView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            logTextView = (TextView) itemView.findViewById(R.id.tv_recycler);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    // Pass in the course array into the constructor
    public AssignmentAdapter(List<Assignment> courseList) {
        mAssignmentsList = courseList;
    }
}
