package com.firstapp.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "questionsdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below is our database columns name
    private static final String TABLE_NAME = "myquestions";

    private static final String ID_COL = "id";

    private static final String QUESTION_NAME = "question";

    private static final String OPTION_1 = "option1";

    private static final String OPTION_2 = "option2";

    private static final String OPTION_3 = "option3";

    private static final String CORRECTANS_NO = "correctAnsNo";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUESTION_NAME + " TEXT,"
                + OPTION_1 + " TEXT,"
                + OPTION_2 + " TEXT,"
                + OPTION_3 + " TEXT,"
                + CORRECTANS_NO+" TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewQuestion(String questionname, String option1, String option2, String option3,String correctanswerno) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(QUESTION_NAME, questionname);
        values.put(OPTION_1,option1);
        values.put(OPTION_2,option2);
        values.put(OPTION_3,option3);
        values.put(CORRECTANS_NO,correctanswerno);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<QuestionModel> readQuestion() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<QuestionModel> questionModelArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                String x5=cursorCourses.getString(0);
                String x=cursorCourses.getString(1);
                String x1=cursorCourses.getString(2);
                String x2=cursorCourses.getString(3);
                String x3=cursorCourses.getString(4);
                int x4=cursorCourses.getInt(5);

                System.out.println(x4);

               System.out.println(x);
                // on below line we are adding the data from cursor to our array list.
                questionModelArrayList.add(new QuestionModel(x5,x,x1,x2,x3,x4));

            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return questionModelArrayList;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}