package pingbits.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import pingbits.com.emojikeyboard.adapter.EmojiAdapter;
import pingbits.com.emojikeyboard.objects.Emoji;
import pingbits.com.emojikeyboard.objects.EmojisData;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojiGridView {

    private final EmojiPopup mEmojiconPopup;
    public final View rootView;
    private final Emoji[] mData;

    public EmojiGridView(Context context, EmojiPopup emojiconPopup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmojiconPopup = emojiconPopup;
        rootView = inflater.inflate(R.layout.emoji_grid, null);
        GridView gridView = (GridView) rootView.findViewById(R.id.emoji_gridView);
        mData = EmojisData.DATA;

        EmojiAdapter mAdapter = new EmojiAdapter(context, mData);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mEmojiconPopup.onEmojiClickedListener != null) {
                    mEmojiconPopup.onEmojiClickedListener.onEmojiconClicked(mData[position]);
                }
            }
        });
        gridView.setAdapter(mAdapter);
    }

    public interface OnEmojiClickedListener {
        void onEmojiconClicked(Emoji emoji);
    }
}
