package com.example.roombasic20211230;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   // WordDatabase wordDatabase;
   // WordDao wordDao;
    TextView textView;
    Button buttonInsert,buttonUpdate,buttonDelete,buttonClear;
  // LiveData<List<Word>>allWordsLive;
   WordViewModel wordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel= new ViewModelProvider(this).get(WordViewModel.class);

       // wordDatabase= Room.databaseBuilder(this,WordDatabase.class,"word_database").build();
       //wordDao=wordDatabase.getWordDao();

       // allWordsLive=wordDao.getAllWordsLive();
        textView=findViewById(R.id.textView);
        // no need updateView() bc livedata is observed
       // updateView();
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
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
                wordViewModel.insertWords(word1,word2,word3);
               // updateView();
                //new WordViewModel.InsertAsyncTask(wordDao).execute(word1,word2,word3);

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
               // updateView();
                //new WordViewModel.DeleteAllAsyncTask(wordDao).execute();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi,", "nihaoa");
                word.setId(1);
                wordViewModel.updateWords(word);
                //updateView();
               // new WordViewModel.UpdateAsyncTask(wordDao).execute(word);


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi,", "nihaoa");
                word.setId(1);
                wordViewModel.deleteWords(word);
               // updateView();
                //new WordViewModel.DeleteAsyncTask(wordDao).execute(word);
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
