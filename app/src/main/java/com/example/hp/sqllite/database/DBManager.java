package com.example.hp.sqllite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.hp.sqllite.model.Student;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private final String TAG = "DBManager";
    public static final String TABLE_NAME = "Student_list";
    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";

    private Context context;
    private String sqlQuery = "CREATE TABLE "+TABLE_NAME+" (" +
            ID + " integer primary key, "+
            NAME + " TEXT, "+
            NUMBER + " TEXT, "+
            EMAIL + " TEXT, "+
            ADDRESS + " TEXT)";

    public DBManager(Context context) {
        super(context, TABLE_NAME, null, 1);
        Log.d("DBMNanage", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlQuery);
        //Toast.makeText(context, "Create database successfully", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate is called and create database successfully ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
    }

    public void addStudent(Student student){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, student.getName());
        values.put(NUMBER, student.getNumber());
        values.put(EMAIL, student.getEmail());
        values.put(ADDRESS, student.getAddress());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "Add student to database successfuly!");
    }

    public int updateStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,student.getName());
        contentValues.put(NUMBER,student.getNumber());
        contentValues.put(EMAIL,student.getEmail());
        contentValues.put(ADDRESS,student.getAddress());
        return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(student.getID())});
    }
    public int deleteStudent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[] {String.valueOf(id)});
    }

    public List<Student> getAllStudent(){
        List<Student> listStudent = new ArrayList();
        String selectQuery = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                Student student = new Student();
                student.setID(cursor.getInt(0));
                student.setName(cursor.getString(1));
                student.setNumber(cursor.getString(2));
                student.setEmail(cursor.getString(3));
                student.setAddress(cursor.getString(4));
                listStudent.add(student);
            }while (cursor.moveToNext());
        }
        db.close();
        Log.d(TAG, "Get all Student Successfully");
        return listStudent;
    }
}
