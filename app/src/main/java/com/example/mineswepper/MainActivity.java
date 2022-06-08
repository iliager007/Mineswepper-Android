package com.example.mineswepper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    int height = 11;
    int width = 7;
    int countMines = 10;
    boolean started = false;
    ArrayList<ArrayList<myButton>> btns = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        for(int i = 0; i < height; ++i){
            LinearLayout horizontal_layout = new LinearLayout(this);
            horizontal_layout.setOrientation(LinearLayout.HORIZONTAL);
            horizontal_layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            ArrayList<myButton> btnArray = new ArrayList<>();
            for(int j = 0; j < width; ++j){
                myButton btn = new myButton(this, i, j, btns);
                btn.setMinimumWidth(150);
                btn.setMinimumHeight(150);

                btn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(started){
                            btn.setFlagged(!btn.isFlagged());
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(started){
                            if(btn.isMine()){
                                if(!btn.isFlagged()) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Поражение :(", Toast.LENGTH_SHORT);
                                    toast.show();
                                    for(int i = 0; i < height; ++i){
                                        for(int j = 0; j < width; ++j){
                                            btns.get(i).get(j).setActivated(false);
                                            btns.get(i).get(j).setClickable(false);
                                            btns.get(i).get(j).setEnabled(false);
                                        }
                                    }
                                }
                            }
                            else{
                                btn.open();
                                if(myButton.openedBtns.size() == (height * width - countMines)){
                                    Toast toast = Toast.makeText(getApplicationContext(), "Победа :)", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        }
                        else {
                            for(int dop = 0; dop < countMines;){
                                int y = (int) (Math.random() * width);
                                int x = (int) (Math.random() * height);
                                if(!(Math.abs(x - btn.i) <= 1 && Math.abs(y - btn.j) <= 1) && !btns.get(x).get(y).isMine()){
                                    btns.get(x).get(y).setMine(true);
                                    dop++;
                                }
                            }
                            for(int x = 0; x < height; ++x){
                                for(int y = 0; y < width; ++y){
                                    for(int t1 = -1; t1 <= 1; ++t1){
                                        for(int t2 = -1; t2 <=1; ++t2){
                                            if((x + t1) >= 0 && (x + t1) < height && (y + t2) >= 0 && (y + t2) < width && btns.get(x + t1).get(y + t2).isMine()) btns.get(x).get(y).countMines++;
                                        }
                                    }
                                }
                            }
                            started = true;
                            for(int t1 = -1; t1 <= 1; ++t1){
                                for(int t2 = -1; t2 <=1; ++t2){
                                    if((btn.i + t1) >= 0 && (btn.i + t1) < height && (btn.j + t2) >= 0 && (btn.j + t2) < width && !btns.get(btn.i + t1).get(btn.j + t2).isOpened()) btns.get(btn.i + t1).get(btn.j + t2).open();
                                }
                            }
                        }
                    }
                });
                horizontal_layout.addView(btn);
                btnArray.add(btn);
            }
            btns.add(btnArray);
            mainLayout.addView(horizontal_layout);
        }
        Button restartBtn = new Button(this);
        restartBtn.setText("Новая игра");
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                started = false;
                for(int i = 0; i < height; ++i){
                    for(int j = 0; j < width; ++j){
                        btns.get(i).get(j).setActivated(true);
                        btns.get(i).get(j).setClickable(true);
                        btns.get(i).get(j).setEnabled(true);
                        btns.get(i).get(j).setMine(false);
                        btns.get(i).get(j).setOpened(false);
                        btns.get(i).get(j).setFlagged(false);
                        btns.get(i).get(j).setImageDrawable(null);
                        btns.get(i).get(j).countMines = 0;
                        myButton.openedBtns = new HashSet<>();
                    }
                }
            }
        });
        mainLayout.addView(restartBtn);
    }

}