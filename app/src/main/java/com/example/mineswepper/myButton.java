package com.example.mineswepper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

class myButton extends androidx.appcompat.widget.AppCompatImageButton{
    int height = 11;
    int width = 7;
    int i;
    int j;
    boolean mine = false;
    boolean opened = false;
    boolean flagged = false;
    int countMines = 0;
    ArrayList<ArrayList<myButton>> btns;
    static HashSet<Integer> openedBtns = new HashSet<>();
    public myButton(Context context, int i, int j, ArrayList<ArrayList<myButton>> btns) {
        super(context);
        this.i = i;
        this.j = j;
        this.btns = btns;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
    public void open(){
        this.setActivated(false);
        this.setOpened(true);
        this.setClickable(false);
        this.setEnabled(false);
        openedBtns.add(i * width + j);
        if(this.countMines == 1) this.setImageDrawable(AppCompatResources.getDrawable(this.getContext(), R.drawable.one));
        else if(this.countMines == 2) this.setImageDrawable(AppCompatResources.getDrawable(this.getContext(), R.drawable.two));
        else if(this.countMines == 3) this.setImageDrawable(AppCompatResources.getDrawable(this.getContext(), R.drawable.three));
        else if(this.countMines == 4) this.setImageDrawable(AppCompatResources.getDrawable(this.getContext(), R.drawable.four));
        else if(this.countMines == 5) this.setImageDrawable(AppCompatResources.getDrawable(this.getContext(), R.drawable.five));
        else if(this.countMines == 0) {
            this.setImageDrawable(AppCompatResources.getDrawable(this.getContext(), R.drawable.zero));
            for(int t1 = -1; t1 <= 1; ++t1){
                for(int t2 = -1; t2 <=1; ++t2){
                    if((i + t1) >= 0 && (i + t1) < height && (j + t2) >= 0 && (j + t2) < width && !btns.get(i + t1).get(j + t2).isOpened()) btns.get(i + t1).get(j + t2).open();
                }
            }
        }
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
        if(flagged){
            this.setImageDrawable(AppCompatResources.getDrawable(this.getContext(), R.drawable.flag));
        }
        else {
            this.setImageDrawable(null);
        }
    }
}
