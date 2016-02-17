package com.jason9075.realmplaygroundandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private EditText titleEditText, subtitleEditText;
    private ArticleAdapter articleAdapter;
    private List<ArticleData> selectedArticleDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = (EditText) findViewById(R.id.title_edittext);
        subtitleEditText = (EditText) findViewById(R.id.subtitle_edittext);
        Button addButton = (Button) findViewById(R.id.add_button);
        Button deleteButton = (Button) findViewById(R.id.delete_button);
        ListView listView = (ListView) findViewById(R.id.list_view);

        realm = Realm.getInstance(this);
        RealmResults<ArticleData> articleDatas = realm.where(ArticleData.class).findAll();
        articleAdapter = new ArticleAdapter(this, articleDatas);
        listView.setAdapter(articleAdapter);

        //delegate back
        articleAdapter.setCustomEventListener(new ArticleAdapter.OnCustomEventListener() {
            @Override
            public void onCheckboxChanged(ArticleData articleData, boolean isSelected) {
                if(isSelected)
                    selectedArticleDatas.add(articleData);
                else
                    selectedArticleDatas.remove(articleData);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEditText.getText().toString().equals("") || subtitleEditText.getText().toString().equals(""))
                    return;

                realm.beginTransaction();
                ArticleData articleData = realm.createObject(ArticleData.class);
                articleData.setId(System.currentTimeMillis());
                articleData.setTitle(titleEditText.getText().toString());
                articleData.setSubtitle(subtitleEditText.getText().toString());
                realm.commitTransaction();

                articleAdapter.notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();

                for (ArticleData articleData : selectedArticleDatas) {
                    articleData.removeFromRealm();
                }

                realm.commitTransaction();

                selectedArticleDatas.clear();
                articleAdapter.clearSelectedIndex();
                articleAdapter.notifyDataSetChanged();
            }
        });
    }
}
