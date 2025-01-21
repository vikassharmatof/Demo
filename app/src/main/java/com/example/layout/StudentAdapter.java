package com.example.layout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.bumptech.glide.Glide;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> studentList;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, rollNumberTextView, idTextView;
        ImageView studentImageView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            rollNumberTextView = itemView.findViewById(R.id.tv_rollnumber);
            idTextView = itemView.findViewById(R.id.tv_id);
            studentImageView = itemView.findViewById(R.id.iv_database_image);
        }
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.database_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.nameTextView.setText(student.getName());
        holder.rollNumberTextView.setText(String.valueOf(student.getRollNumber()));
        holder.idTextView.setText(String.valueOf(student.getId()));

        if (student.getImageUri() != null) {
            Glide.with(holder.studentImageView.getContext())
                    .load(student.getImageUri())
                    .into(holder.studentImageView);
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
