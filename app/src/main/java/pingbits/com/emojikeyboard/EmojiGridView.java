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

    private  EmojiPopup mEmojiconPopup;
    public  View rootView;
    private  Emoji[] mData;
    public Context context;
    public EmojiPopup emojiconPopup;
    public RecentManager recentManager;
    public EmojiAdapter mAdapter;
    public GridView gridView;
    public Emoji lastEmoji=null;

    public EmojiGridView(Context context, EmojiPopup emojiconPopup,int position) {

        this.context = context;
        this.emojiconPopup = emojiconPopup;

        switch (position){
            case 0:
                setRecents();
                break;
            default:
                setEmoji();
        }
    }

    private void setRecents() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmojiconPopup = emojiconPopup;
        rootView = inflater.inflate(R.layout.emoji_grid, null);

        gridView = (GridView) rootView.findViewById(R.id.emoji_gridView);
        recentManager = new RecentManager(context);
        mData = recentManager.getRecents();
        mAdapter = new EmojiAdapter(context, recentManager.getRecents());
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

    public void addToRecents(Emoji emoji){
        if(lastEmoji!=emoji){
            lastEmoji = emoji;
            mData = recentManager.addEmoji(emoji);
            mAdapter = new EmojiAdapter(context, mData);
            gridView.setAdapter(mAdapter);
        }

    }

    private void setEmoji() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmojiconPopup = emojiconPopup;
        rootView = inflater.inflate(R.layout.emoji_grid, null);
        gridView = (GridView) rootView.findViewById(R.id.emoji_gridView);
        mData = EmojisData.DATA;

        EmojiAdapter mAdapter = new EmojiAdapter(context, mData);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mEmojiconPopup.onEmojiClickedListener != null) {
                    mEmojiconPopup.onEmojiClickedListener.onEmojiconClicked(mData[position]);
                    mEmojiconPopup.recents.addToRecents(mData[position]);
                }
            }
        });
        gridView.setAdapter(mAdapter);
    }

    public void save() {
        recentManager.saveData();
    }

    public interface OnEmojiClickedListener {
        void onEmojiconClicked(Emoji emoji);
    }
}
