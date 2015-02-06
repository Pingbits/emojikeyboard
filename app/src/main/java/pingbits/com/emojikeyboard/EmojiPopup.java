package pingbits.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojiPopup extends PopupWindow {
    public Context mContext;

    public EmojiPopup(Context context) {
        super(context);
        mContext = context;
        setContentView(emojiView());
    }

    public View emojiView(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.emoji, null, false);
        return null;
    }


}
