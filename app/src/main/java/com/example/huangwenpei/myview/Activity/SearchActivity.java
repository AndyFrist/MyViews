package com.example.huangwenpei.myview.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.adapter.SearchAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    EditText searchEdit;
    RecyclerView searchRecyclerview;
    private SearchAdapter searchAdapter;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();

    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEdit = findViewById(R.id.search_edit);
        searchRecyclerview = findViewById(R.id.search_recycler);

        init();
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int k, int i1, int i2) {
                list.clear();
                int size = data.size();
                for (int i = 0; i < size; i++) {
                    if (data.get(i).contains(charSequence)) {
                        list.add(data.get(i));
                    }
                }
                key = charSequence.toString();
                searchAdapter.setKey(key);
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void init() {
        for (int i = 0; i < 100; i++) {
            data.add(i + "号种子");
        }
        list.addAll(data);
        searchAdapter = new SearchAdapter(this, list);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerview.setAdapter(searchAdapter);
    }
}
