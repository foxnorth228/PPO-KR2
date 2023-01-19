package com.example.kr2;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RankList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.rating_table);
        ListView list = findViewById(R.id.rankList);
        ArrayList<Player> map = Database.sort();
        list.setAdapter(new NameRankAdapter(this, map));
    }
}
