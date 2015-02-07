package pingbits.com.emojikeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by aagam on 7/2/15.
 */
public class EmojiSpan extends ImageSpan {

    private final Context mContext;
    private final int mResourceId;
    private final int mSize;
    private Drawable mDrawable;

    public EmojiSpan(Context context, int resourceId, int size) {
        super(context,resourceId);
        mContext = context;
        mResourceId = resourceId;
        mSize = size;
    }

    @Override
    public Drawable getDrawable() {
        if (mDrawable == null) {
            try {
                mDrawable = super.getDrawable();
//                mDrawable = mContext.getResources().getDrawable(mResourceId);
                int size = mSize;
                mDrawable.setBounds(0, 0, size, size);

            } catch (Exception e) {
                // swallow
            }
        }
        return mDrawable;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        // adding the padding to the original image size
        int size = super.getSize(paint, text, start, end, fm);
        size += (2 * 2);
        return size;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        // adding the padding to the transformation so it will be padding on both sides
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
    }


}
