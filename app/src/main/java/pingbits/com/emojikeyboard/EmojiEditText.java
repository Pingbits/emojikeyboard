package pingbits.com.emojikeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by aagam on 7/2/15.
 */
public class EmojiEditText extends EditText{

    private int mEmojiconSize;

    public EmojiEditText(Context context) {
        super(context);
        mEmojiconSize = (int)getTextSize();
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mEmojiconSize = (int)getTextSize();
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mEmojiconSize = (int)getTextSize();
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mEmojiconSize = (int)getTextSize();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        EmojiManager.addEmojis(getContext(), getText(), mEmojiconSize);
    }
}
