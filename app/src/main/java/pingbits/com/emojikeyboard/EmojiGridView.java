package pingbits.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import pingbits.com.emojikeyboard.adapter.EmojiAdapter;
import pingbits.com.emojikeyboard.objects.Emoji;
import pingbits.com.emojikeyboard.objects.EmojisData;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojiGridView {

    private final EmojiPopup mEmojiconPopup;
    private final View rootView;
    private final Emoji[] mData;

    public EmojiconGridView(Context context, EmojiconRecents recents, EmojiPopup emojiconPopup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmojiconPopup = emojiconPopup;
        rootView = inflater.inflate(R.layout.emoji_grid, null);
       // setRecents(recents);
        GridView gridView = (GridView) rootView.findViewById(R.id.emoji_gridView);
        mData = EmojisData.DATA;

        EmojiAdapter mAdapter = new EmojiAdapter(rootView.getContext(), mData);
        mAdapter.setEmojiClickListener(new OnEmojiClickedListener() {

            @Override
            public void onEmojiconClicked(Emoji emoji) {
                if (mEmojiconPopup.onEmojiClickedListener != null) {
                    mEmojiconPopup.onEmojiClickedListener.onEmojiconClicked(emoji);
                }
                /*if (mRecents != null) {
                    mRecents.addRecentEmoji(rootView.getContext(), emoji);
                }*/
            }
        });
        gridView.setAdapter(mAdapter);
    }

    public interface OnEmojiClickedListener {
        void onEmojiconClicked(Emoji emoji);
    }
}
