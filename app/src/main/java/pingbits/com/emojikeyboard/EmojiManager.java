package pingbits.com.emojikeyboard;

import android.content.Context;
import android.text.Spannable;
import android.util.SparseIntArray;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojiManager {

    public static final SparseIntArray sEmojisMap = new SparseIntArray();

    static {
        sEmojisMap.put(0x1f60a, R.drawable.p1f60a);
        sEmojisMap.put(0x1f60b, R.drawable.p1f60b);
        sEmojisMap.put(0x1f60c, R.drawable.p1f60c);
        sEmojisMap.put(0x1f60d, R.drawable.p1f60d);
        sEmojisMap.put(0x1f60e, R.drawable.p1f60e);
        sEmojisMap.put(0x1f60f, R.drawable.p1f60f);
        sEmojisMap.put(0x1f61a, R.drawable.p1f61a);
        sEmojisMap.put(0x1f61b, R.drawable.p1f61b);
        sEmojisMap.put(0x1f61c, R.drawable.p1f61c);
        sEmojisMap.put(0x1f61d, R.drawable.p1f61d);
        sEmojisMap.put(0x1f61e, R.drawable.p1f61e);
        sEmojisMap.put(0x1f61f, R.drawable.p1f61f);
        sEmojisMap.put(0x1f62a, R.drawable.p1f62a);
        sEmojisMap.put(0x1f62b, R.drawable.p1f62b);
        sEmojisMap.put(0x1f62c, R.drawable.p1f62c);
        sEmojisMap.put(0x1f62d, R.drawable.p1f62d);
        sEmojisMap.put(0x1f62e, R.drawable.p1f62e);
        sEmojisMap.put(0x1f62f, R.drawable.p1f62f);
        sEmojisMap.put(0x1f63a, R.drawable.p1f63a);
        sEmojisMap.put(0x1f63b, R.drawable.p1f63b);
        sEmojisMap.put(0x1f63c, R.drawable.p1f63c);
        sEmojisMap.put(0x1f63d, R.drawable.p1f63d);
        sEmojisMap.put(0x1f63e, R.drawable.p1f63e);
    }

    private static int getEmojiResource(int codePoint) {
        return sEmojisMap.get(codePoint);
    }

    /**
     * Convert emoji characters of the given Spannable accordingly
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param index
     * @param length
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int index, int length) {
        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length+index);

        int skip;
        for (int i = index; i < textLengthToProcess; i += skip) {
            int icon = 0;
            int unicode = Character.codePointAt(text, i);
            skip = Character.charCount(unicode);
                icon = getEmojiResource(unicode);

            if (icon > 0) {
                text.setSpan(new EmojiSpan(context, icon, emojiSize), i, i + skip, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
