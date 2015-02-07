package pingbits.com.emojikeyboard;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by aagam on 7/2/15.
 */
public class EmojiTextView extends TextView {
    public EmojiTextView(Context context) {
        super(context);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        EmojiManager.addEmojis(getContext(), builder, (int)getTextSize(), 0, -1);
        super.setText(builder, type);
    }
}
