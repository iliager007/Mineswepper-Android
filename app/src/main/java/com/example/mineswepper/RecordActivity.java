package com.example.mineswepper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Button btnBack = findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ListView listView = findViewById(R.id.listView);
        AppDatabase db = AppDatabase.getDatabase(this);
        RecordDao recordDao = db.recordDao();
        List<Record> records = recordDao.getAll();
        ArrayList<String> strRecords = new ArrayList<>();
        for(Record i: records){
            strRecords.add((((double)i.time) / 1000) + " - " + i.date);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strRecords);
        listView.setAdapter(adapter);
    }
}