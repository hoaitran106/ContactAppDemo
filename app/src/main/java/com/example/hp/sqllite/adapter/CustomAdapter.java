package com.example.hp.sqllite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.sqllite.R;
import com.example.hp.sqllite.model.Student;

import java.util.List;


public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<Student> listStudent;

    public CustomAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listStudent = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_student, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvId = convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvNumber = convertView.findViewById(R.id.tv_phone_number);
            viewHolder.tvEmail = convertView.findViewById(R.id.tv_email);
            viewHolder.tvAddress = convertView.findViewById(R.id.tv_address);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            Student student = listStudent.get(position);
            viewHolder.tvId.setText(String.valueOf(student.getID()));
            viewHolder.tvName.setText(student.getName());
            viewHolder.tvNumber.setText((student.getNumber()));
            viewHolder.tvEmail.setText(student.getEmail());
            viewHolder.tvAddress.setText(student.getAddress());
        }

        return convertView;
    }

    public class ViewHolder{

        private TextView tvId;
        private TextView tvName;
        private TextView tvNumber;
        private TextView tvEmail;
        private TextView tvAddress;
    }
}