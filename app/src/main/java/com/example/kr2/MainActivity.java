package com.example.kr2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static SQLDatabase sqlbase;
    private boolean isReg = false;
    public final static String ACCESS = "ACCESS";
    private String selectedName = null;
    private int count = 0;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent = result.getData();
                    assert intent != null;
                    int count = intent.getIntExtra(ACCESS, -1);
                    if(count != -1) {
                        Database.update(selectedName, count);
                        this.count = count;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sqlbase = new SQLDatabase(getApplicationContext());
        Database.deleteSqlTable();
        sqlbase = new SQLDatabase(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button but = findViewById(R.id.buttonPlay);
        Button reg = findViewById(R.id.buttonReg);
        Button rank = findViewById(R.id.buttonRank);
        but.setOnClickListener((View view) -> {
            if(selectedName == null || selectedName.equals("")) {
                return;
            }
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra(ACCESS, count);
            mStartForResult.launch(intent);
        });
        reg.setOnClickListener((View view) -> {
            EditText edit = findViewById(R.id.registerName);
            TextView name = findViewById(R.id.registeredName);
            if(String.valueOf(edit.getText()).equals("")) {
                return;
            }
            String newName = String.valueOf(edit.getText());
            if(!isReg) {
                reg.setText(R.string.buttonUnreg);
                if(Database.put(newName) == -1) {
                    count = Database.get(newName);
                } else {
                    count = 0;
                }
                name.setText(newName);
                edit.setVisibility(View.INVISIBLE);
                name.setVisibility(View.VISIBLE);
                selectedName = newName;
                isReg = true;
            } else {
                reg.setText(R.string.buttonReg);
                count = 0;
                selectedName = null;
                edit.setVisibility(View.VISIBLE);
                name.setVisibility(View.INVISIBLE);
                isReg = false;
            }
        });
        rank.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, RankList.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqlbase.close();
    }
}