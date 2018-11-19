package com.cheapest.lansu.cheapestshopping.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import com.cheapest.lansu.cheapestshopping.view.App;

import static android.text.style.DynamicDrawableSpan.ALIGN_BASELINE;

/*
* 文件名：StickerSpan
* 描    述：
* 作    者：lansu
* 时    间：2018/5/14 15:52
* 版    权：lansus
*/
public class StickerSpan extends ImageSpan {
    public StickerSpan(Drawable b, int verticalAlignment) {
        super(b, verticalAlignment);
    }
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();
        int transY = (int) (bottom - b.getBounds().bottom - AppUtils.dip2px(App.app,3.0f));// 减去行间距
        if (mVerticalAlignment == ALIGN_BASELINE) {
            int textLength = text.length();
            for (int i = 0; i < textLength; i++) {
                if (Character.isLetterOrDigit(text.charAt(i))) {
                    transY -= paint.getFontMetricsInt().descent;
                    break;
                }
            }
        }
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}