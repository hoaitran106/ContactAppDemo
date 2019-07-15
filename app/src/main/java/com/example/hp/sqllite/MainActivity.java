package com.example.hp.sqllite;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.sqllite.adapter.CustomAdapter;
import com.example.hp.sqllite.database.DBManager;
import com.example.hp.sqllite.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtId, edtName, edtNumber, edtEmail, edtAddress;
    Button btnSave, btnUpdate;
    ListView lvStudent;
    DBManager dbManager;

    CustomAdapter customAdapter;
    List<Student> studentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);
        initControl();
        initDatabase();
        setEvent();
    }

    private void setEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = createStudent();
                if(student != null){
                dbManager.addStudent(student);
                }
                updateListStudent();
                setAdapter();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setID(Integer.parseInt(edtId.getText()+""));
                student.setName(edtName.getText()+"");
                student.setNumber(edtNumber.getText()+"");
                student.setEmail(edtEmail.getText()+"");
                student.setAddress(edtAddress.getText()+"");
                int result = dbManager.updateStudent(student);
                if(result>0){
                    updateListStudent();
                }
                btnSave.setEnabled(true);
                btnUpdate.setEnabled(false);
            }
        });

        lvStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Student student = studentList.get(position);
                edtId.setText(String.valueOf(student.getID()));
                edtName.setText(student.getName());
                edtNumber.setText(student.getNumber());
                edtEmail.setText(student.getEmail());
                edtAddress.setText(student.getAddress());
                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
                return false;
            }
        });

        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showDialogAction(position);
            }
        });

    }

    private void initControl() {
            edtId = (EditText) findViewById(R.id.mId);
            edtName = findViewById(R.id.mName);
            edtNumber = findViewById(R.id.mNumber);
            edtEmail = findViewById(R.id.mEmail);
            edtAddress = findViewById(R.id.mAddress);
            btnSave = findViewById(R.id.btn_save);
            btnUpdate = (Button) findViewById(R.id.btn_update);
            lvStudent = findViewById(R.id.lv_Student);
    }

    public void initDatabase(){
        studentList = dbManager.getAllStudent();
        setAdapter();
    }

    private Student createStudent(){
        String name = edtName.getText().toString();
        String number = edtNumber.getText().toString();
        String email = edtEmail.getText().toString();
        String address = edtAddress.getText().toString();
        Student student = new Student(name, number, email, address);
        return student;
    }
    private void setAdapter(){
        if(customAdapter == null){
            customAdapter = new CustomAdapter(this, R.layout.item_list_student, studentList);
            lvStudent.setAdapter(customAdapter);
        }
        else{
            customAdapter.notifyDataSetChanged();
            lvStudent.setSelection(customAdapter.getCount() - 1);
        }
    }

    public void updateListStudent(){
        studentList.clear();
        studentList.addAll(dbManager.getAllStudent());
        if(customAdapter!=null){
            customAdapter.notifyDataSetChanged();
        }
    }

    public void showDialogAction(final int position){
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Contact option");
        dialog.setContentView(R.layout.custom_dialog);
        Button btnCall = dialog.findViewById(R.id.btn_Call);
        Button btnSendMessage = dialog.findViewById(R.id.btn_SendMessage);
        Button btnDelete = dialog.findViewById(R.id.btn_Delete);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Call", Toast.LENGTH_SHORT).show();
                intentCall(position);
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Message", Toast.LENGTH_SHORT).show();
                intentSendMessage(position);
            }
        });
        dialog.show();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDatabase(position);
            }
        });

    }

    public void removeDatabase(int position){
        Student student = studentList.get(position);
        int result = dbManager.deleteStudent(student.getID());
        if(result>0){
            Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_LONG).show();
            updateListStudent();
        }else{
            Toast.makeText(MainActivity.this, "Delete failed", Toast.LENGTH_LONG).show();
        }
    }

    private void intentCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + studentList.get(position).getNumber()));
        startActivity(intent);
    }

    private void intentSendMessage(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+ studentList.get(position).getNumber()));
        startActivity(intent);
    }
}
