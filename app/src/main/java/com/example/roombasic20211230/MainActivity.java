package com.example.roombasic20211230;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.AsyncTask;
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
                new InsertAsyncTask(wordDao).execute(word1,word2,word3);

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAllWords();
               // updateView();
                new DeleteAllAsyncTask(wordDao).execute();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi,", "nihaoa");
                word.setId(1);
                wordDao.updateWords(word);
                //updateView();
                new UpdateAsyncTask(wordDao).execute(word);


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("hi,", "nihaoa");
                word.setId(1);
                wordDao.deleteWords(word);
               // updateView();
                new DeleteAsyncTask(wordDao).execute(word);
            }
        });


    }

    // void 1 is to report progress, void 2 is to report result
    static class InsertAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
        // postExcute,progressUpdate//preExecute
    }

    static class UpdateAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
        // postExcute,progressUpdate//preExecute
    }

    static class DeleteAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;

        public DeleteAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
        // postExcute,progressUpdate//preExecute
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private WordDao wordDao;

        public DeleteAllAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
        // postExcute,progressUpdate//preExecute
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
