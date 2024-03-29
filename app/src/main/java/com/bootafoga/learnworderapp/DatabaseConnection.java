package com.bootafoga.learnworderapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseConnection {

        private Context context;
        private SQLiteDatabase dbWritable;
        private SQLiteDatabase dbReadable;
        Cursor cursorAllLearned;
        Cursor cursorAllNotLearned;

        public DatabaseConnection(Context context){
            this.context = context;
            setConnect();
        }

        private void setConnect(){
            SQLiteOpenHelper worderDatabaseHelper = new LearnWorderDBHelper(context);
            try {
                dbWritable = worderDatabaseHelper.getWritableDatabase();
                dbReadable = worderDatabaseHelper.getReadableDatabase();
            } catch(SQLiteException e) {
                Toast toast = Toast.makeText(context, R.string.database_unavailable, Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        public SQLiteDatabase getDbWritable() {
            return dbWritable;
        }

        public SQLiteDatabase getDbReadable() {
            return dbReadable;
        }

        public Cursor getCursorAll() {
            Cursor cursorAll;
            cursorAll = dbReadable.query("DICTIONARY",
                    new String[]{"_id", "WORD", "TRANSLATE", "FLAG"},
                    null, null, null, null, null);
            return cursorAll;
        }

        public Cursor getCursorAllNotLearned() {
            cursorAllNotLearned = dbReadable.query("DICTIONARY",
                    new String[]{"_id", "WORD", "TRANSLATE", "FLAG"},
                    "FLAG = ?", new String[]{Integer.toString(0)}, null, null, "WORD ASC");
            return cursorAllNotLearned;
        }

        public Cursor getCursorAllLearned() {
            cursorAllLearned = dbReadable.query("DICTIONARY",
                    new String[]{"_id", "WORD", "TRANSLATE", "FLAG"},
                    "FLAG = ?", new String[]{Integer.toString(1)}, null, null, "WORD ASC");
            return cursorAllLearned;
        }

        public Cursor getCursorById(int id) {
            Cursor cursorById;
            cursorById = dbReadable.query("DICTIONARY",
                    new String[]{"_id", "WORD", "TRANSLATE", "FLAG"},
                    "_id = ?", new String[] {Integer.toString(id)}, null, null, "WORD ASC");
            return cursorById;
        }

        public int getCountNotLearned(){
            getCursorAllNotLearned();
            return cursorAllNotLearned.getCount();
        }

        public int getCountLearned(){
            getCursorAllLearned();
            return cursorAllLearned.getCount();
        }
}