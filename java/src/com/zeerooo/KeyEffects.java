package com.zeerooo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;

import com.android.inputmethod.keyboard.Keyboard;

public class KeyEffects {
    private int[] colorArray = {};
    private short animationSpeed;
    private Matrix matrix;
    private LinearGradient linearGradient;
    public static byte keyEffectMode;

    public void initialize(Keyboard keyboard, Paint paint) {
        if (keyEffectMode == 1 || keyEffectMode == 2) {
            if (keyEffectMode == 1 && colorArray.length != 600)
                rainbow();
            else if (keyEffectMode == 2)
                setColorArray(new int[]{Color.DKGRAY, Tinter.colorAccentDark});

            linearGradient = new LinearGradient(0, 0, keyboard.mOccupiedWidth, 0, colorArray, null, Shader.TileMode.MIRROR);
            paint.setShader(linearGradient);
            matrix = new Matrix();
        }
    }

    public Canvas draw(Canvas canvas) {
        if (keyEffectMode == 1 || keyEffectMode == 2) {
            animationSpeed += 2;
            matrix.setTranslate(animationSpeed, 0);
            linearGradient.setLocalMatrix(matrix);
        }
        return canvas;
    }

    public void setColorArray(int[] colorArray) {
        this.colorArray = colorArray;
    }

    public void rainbow() {
        colorArray = new int[600];
        short r = 0;
        for (r = 0; r < 100; r++)
            colorArray[r] = (Color.rgb(((r * 255) / 100), 255, 0));
        for (byte g = 100; g > 0; g--)
            colorArray[r++] = (Color.rgb(255, ((g * 255) / 100), 0));
        for (byte b = 0; b < 100; b++)
            colorArray[r++] = (Color.rgb(255, 0, ((b * 255) / 100)));
        for (byte t = 100; t > 0; t--)
            colorArray[r++] = (Color.rgb(((t * 255) / 100), 0, 255));
        for (byte g = 0; g < 100; g++)
            colorArray[r++] = (Color.rgb(0, ((g * 255) / 100), 255));
        for (byte b = 100; b > 0; b--)
            colorArray[r++] = (Color.rgb(0, 255, ((b * 255) / 100)));
    }
}