package pingbits.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import it.sephiroth.android.library.widget.HListView;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojiPopup extends PopupWindow {
    public Context mContext;
    private ViewPager pager;
    private HListView titleList;
    private ImageView backspace;
    private OnBackspaceClickedListener onBackspaceClickedListener;
    public EmojiGridView.OnEmojiClickedListener onEmojiClickedListener;


    public EmojiPopup(Context context) {
        super(context);
        mContext = context;
        setContentView(emojiView());
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //default height
        setSize((int) mContext.getResources().getDimension(R.dimen.keyboard_height), LayoutParams.MATCH_PARENT);
    }

    public View emojiView(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.emoji, null, false);
        titleList = (HListView)view.findViewById(R.id.titleTabs);
        backspace = (ImageView)view.findViewById(R.id.backspace);
        pager = (ViewPager)view.findViewById(R.id.pager);
        return null;
    }

    /**
     * Manually set the popup window size
     * @param width Width of the popup
     * @param height Height of the popup
     */
    public void setSize(int width, int height){
        setWidth(width);
        setHeight(height);
    }

    /**
     * Set the listener for the event when backspace on is clicked
     */
    public void setOnBackspaceClickedListener(OnBackspaceClickedListener listener){
        this.onBackspaceClickedListener = listener;
    }

    public interface OnBackspaceClickedListener {
        void onBackspaceClicked(View v);
    }

    /**
     * Set the listener for the event when any of the emojicon is clicked
     */
    public void setOnEmojiClickedListener(EmojiGridView.OnEmojiClickedListener listener){
        this.onEmojiClickedListener = listener;
    }
}
