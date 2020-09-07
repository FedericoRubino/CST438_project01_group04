package com.example.cst438_project01_group4.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cst438_project01_group4.ClassObjects.Course;
import com.example.cst438_project01_group4.R;

import java.util.List;

public class GradeAppAdapter extends RecyclerView.Adapter<GradeAppAdapter.ViewHolder> {
    private List<Course> mCoursesList;
    private ItemClickListener clickListener;
    @Override
    public GradeAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.my_recycler_text_view, parent, false);
        // Return a new holder instance
        GradeAppAdapter.ViewHolder viewHolder = new GradeAppAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GradeAppAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Course resItem = mCoursesList.get(position);
        // Set item views based on your views and data model
        TextView textView = viewHolder.logTextView;
        textView.setText(resItem.toString());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mCoursesList.size();
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

    // Pass in the contact array into the constructor
    public GradeAppAdapter(List<Course> coursList) {
        mCoursesList = coursList;
    }
}