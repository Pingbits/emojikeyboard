package pingbits.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;
import pingbits.com.emojikeyboard.adapter.EmojiTitleAdapter;

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
    public EmojiTitleAdapter emojiTitleAdapter;
    public EmojisPagerAdapter pagerAdapter;
    public View rootView;

    public EmojiPopup(Context context, View root) {
        super(context);
        rootView = root;
        mContext = context;
        setContentView(emojiView());
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //default height
        setSize(WindowManager.LayoutParams.MATCH_PARENT
                ,(int) mContext.getResources().getDimension(R.dimen.keyboard_height));
    }

    public View emojiView(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.emoji, null, false);
        titleList = (HListView)view.findViewById(R.id.titleTabs);
        emojiTitleAdapter = new EmojiTitleAdapter(mContext,null);
        titleList.setAdapter(emojiTitleAdapter);
        titleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pager.setCurrentItem(i,true);
            }
        });
        backspace = (ImageView)view.findViewById(R.id.backspace);


        //Get Sticker counts + recent + emoji
        List<EmojiGridView> emojiGridViewList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            emojiGridViewList.add(new EmojiGridView(mContext,this));
        }

        pager = (ViewPager)view.findViewById(R.id.pager);
        pagerAdapter = new EmojisPagerAdapter(emojiGridViewList);
        pager.setAdapter(pagerAdapter);

        return view;
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

    public void showAtBottom(){
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
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

    private static class EmojisPagerAdapter extends PagerAdapter {
        private List<EmojiGridView> views;

        public EmojisPagerAdapter(List<EmojiGridView> views) {
            super();
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position).rootView;
            ((ViewPager)container).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            ((ViewPager)container).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View view, Object key) {
            return key == view;
        }
    }
}
