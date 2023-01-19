package com.example.kr2;

public class PlaygroundBorder {
    public final int left;
    public final int right;
    public final int top;
    public final int bottom;

    public PlaygroundBorder(int top, int border, int width, int height) {
        left = Inst.makePx(border);
        right = Inst.makePx(width - border);
        this.top = Inst.makePx(top);
        bottom = Inst.makePx(height - border * 4);
    }
}
