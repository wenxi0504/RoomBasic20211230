package com.example.roombasic20211230;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton let a class only can create one instance
@Database(entities ={Word.class},version=1,exportSchema = false )
public abstract class WordDatabase extends RoomDatabase {
    private  static WordDatabase INSTANCE;
    //singleton
    static synchronized WordDatabase getDatabase(Context context){
        if(INSTANCE == null)
        {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word_database")
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordDao getWordDao();
}
