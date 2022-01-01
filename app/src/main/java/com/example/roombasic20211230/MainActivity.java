package com.example.roombasic20211230;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    WordDatabase wordDatabase;
    WordDao wordDao;
    TextView textView;
    Button buttonInsert,buttonUpdate,buttonDelete,buttonClear;
   LiveData<List<Word>>allWordsLive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordDatabase= Room.databaseBuilder(this,WordDatabase.class,"word_database")
                .allowMainThreadQueries()// temporary use , normally crud should use at other treads
                .build();
        wordDao=wordDatabase.getWordDao();
        allWordsLive=wordDao.getAllWordsLive();
        textView=findViewById(R.id.textView);
        // no need updateView() bc livedata is observed
       // updateView();
        allWordsLive.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                String text="";
                for (int i=0;i<words.size();i++)
                {
                    Word word=words.get(i);
                    text+= new StringBuilder().append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n").toString();
                    textView.setText(text);
                }
            }
        });

        buttonInsert=findViewById(R.id.buttonInsert);
        buttonClear=findViewById(R.id.buttonClear);
        buttonUpdate=findViewById(R.id.buttonUpdate);
        buttonDelete=findViewById(R.id.buttonDelete);


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1= new Word("hello1","nihao");
                Word word2= new Word("world1","shijie");
                Word word3= new Word("sophie1","wen");
                wordDao.insertWords(word1,word2,word3);
               // updateView();

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAllWords();
               // updateView();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi,", "nihaoa");
                word.setId(1);
                wordDao.updateWords(word);
                //updateView();


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi,", "nihaoa");
                word.setId(1);
                wordDao.deleteWords(word);
               // updateView();
            }
        });


    }

/*
    void updateView(){
        List<Word> list=wordDao.getAllWords();
        String text="";
        for (int i=0;i<list.size();i++)
        {
            Word word=list.get(i);
            text+=word.getId()+":"+word.getWord()+"="+word.getChineseMeaning()+'\n';
            textView.setText(text);
        }
    }

 */
}
