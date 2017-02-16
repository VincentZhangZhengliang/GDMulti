package com.szxkbl.gdmulti;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.szxkbl.gd.DaoMaster;
import com.szxkbl.gd.DaoSession;
import com.szxkbl.gd.Father;
import com.szxkbl.gd.FatherDao;
import com.szxkbl.gd.Son;
import com.szxkbl.gd.SonDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private SQLiteDatabase mDb;
    private DaoMaster      mMaster;
    private DaoSession     mSession;
    private SonDao         mSonDao;
    private FatherDao      mFatherDao;
    private EditText       mIdSonNam;
    private EditText       mIdSonAge;
    private EditText       mIdFatAge;
    private EditText       mIdFatNam;
    private Button         mIdAdd;
    private Button         mIdDelete;
    private Button         mIdUpdate;
    private Button         mIdQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDb();
        init();
    }

    private void init() {
        mIdSonNam = (EditText) findViewById(R.id.id_sonNam);
        mIdSonAge = (EditText) findViewById(R.id.id_SonAge);
        mIdFatAge = (EditText) findViewById(R.id.id_FatAge);
        mIdFatNam = (EditText) findViewById(R.id.id_FatNam);


        mIdAdd = (Button) findViewById(R.id.id_add);
        mIdDelete = (Button) findViewById(R.id.id_delete);
        mIdUpdate = (Button) findViewById(R.id.id_update);
        mIdQuery = (Button) findViewById(R.id.id_query);
        mIdAdd.setOnClickListener(this);
        mIdDelete.setOnClickListener(this);
        mIdUpdate.setOnClickListener(this);
        mIdQuery.setOnClickListener(this);
    }

    private void openDb() {
        mDb = new DaoMaster.DevOpenHelper(this, "person.db").getWritableDatabase();
        mMaster = new DaoMaster(mDb);
        mSession = mMaster.newSession();
        mSonDao = mSession.getSonDao();
        mFatherDao = mSession.getFatherDao();
    }

    private void add() {
        Son son = new Son();
        son.setAge(Integer.parseInt(mIdSonAge.getText().toString()));
        son.setName(mIdSonNam.getText().toString());

        Father father = new Father();
        father.setName(mIdFatNam.getText().toString());
        father.setAge(Integer.parseInt(mIdFatAge.getText().toString()));
        long fatherId = mFatherDao.insert(father);
        son.setFatherId(fatherId);
        mSonDao.insert(son);

    }

    private void delete() {
        mSonDao.deleteAll();
        mFatherDao.deleteAll();
    }

    private void update() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_add:
                add();
                break;
            case R.id.id_delete:
                List<Son> sons = mSonDao.queryBuilder().list();
                for (Son son : sons) {
                    Log.e(TAG, "onClick: " + son);
                }
                break;
            case R.id.id_update:
                break;
            case R.id.id_query:
                List<Son> sons1 = mSonDao.queryBuilder().list();
                for (Son son : sons1) {
                    Log.e(TAG, "onClick: " + son);
                    QueryBuilder<Father> fatherQueryBuilder = mFatherDao.queryBuilder();
                    List<Father> list = fatherQueryBuilder.where(FatherDao.Properties.Id.eq(son.getFatherId())).list();
                    Log.e(TAG, "onClick: " + list);
                }
                break;
        }
    }
}
