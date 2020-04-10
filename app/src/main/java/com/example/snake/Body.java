package com.example.snake;

import android.graphics.Rect;

public class Body{
    private enum direction{
        UP, DOWN, LEFT, RIGHT
    }
    Rect rect;
    int x, y, width, velocity = 1;
    direction currentDir;

    public Body() {
        x = 20;
        y = 20;
        width = 150;

        currentDir = direction.RIGHT;
        rect = new Rect(x, y,x+width,y+width);
    }

    public void Move(){
        switch (currentDir){
            case UP:
                y -= velocity;
                break;
            case DOWN:
                y += velocity;
                break;
            case LEFT:
                x -= velocity;
                break;
            case RIGHT:
                x += velocity;
                break;
        }
        rect.set(x, y,x+width,y+width);
    }

    public Rect getRect() {
        return rect;
    }
}
