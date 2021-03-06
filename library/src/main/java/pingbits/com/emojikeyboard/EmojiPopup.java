package pingbits.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

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
    public EmojiGridView.OnStickerClickedListener onStickerClickedListener;
    public EmojiTitleAdapter emojiTitleAdapter;
    public EmojisPagerAdapter pagerAdapter;
    public View rootView;
    public List<EmojiGridView> emojiGridViewList;
    public EmojiGridView recents;
    public StickerManager stickerManager;
    public List<String> stickerURL;
    public EmojiEditText et;

    private int keyBoardHeight = 0;
    private Boolean pendingOpen = false;
    private Boolean isOpened = false;
    private Boolean hasOpenedOnce = false;


    public int screenHeight=0;
    public int screenWidth = 0;

    public OnSoftKeyboardOpenCloseListener onSoftKeyboardOpenCloseListener;

    public EmojiPopup(Context context, View root, EmojiEditText et, int[] screenSize) {
        super(context);
        rootView = root;
        mContext = context;
        this.et = et;
        setContentView(emojiView());
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        this.screenWidth = screenSize[0];
        this.screenHeight = screenSize[1];
        //default height
        setSize(screenWidth, (int) mContext.getResources().getDimension(R.dimen.keyboard_height));
        setBackgroundDrawable(new ColorDrawable(Color.BLACK));
    }

    public View emojiView(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.emoji, null, false);
        titleList = (HListView)view.findViewById(R.id.titleTabs);
        emojiTitleAdapter = new EmojiTitleAdapter(mContext,null);
        titleList.setAdapter(emojiTitleAdapter);
        emojiTitleAdapter.setClickListener(new EmojiTitleAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                pager.setCurrentItem(position, true);
            }
        });
        backspace = (ImageView)view.findViewById(R.id.backspace);



        //Get Sticker counts + recent + emoji
        emojiGridViewList = new ArrayList<>();
        setUpStickers();

        for (int i = 0; i < stickerURL.size()+2; i++) {
            emojiGridViewList.add(new EmojiGridView(mContext,this,i,stickerURL));
        }
        recents = emojiGridViewList.get(0);

        pager = (ViewPager)view.findViewById(R.id.pager);
        pagerAdapter = new EmojisPagerAdapter(emojiGridViewList);
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                emojiTitleAdapter.isActive=position;
                emojiTitleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void setUpStickers() {
        stickerManager = new StickerManager(mContext);
        stickerURL = new ArrayList<>();
        stickerURL = stickerManager.getStickerURLs();
        if(stickerURL.size()==0){
            stickerURL.add("http://dl.stickershop.line.naver.jp/products/0/0/29/252/android/stickers.zip");
        }
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
        if(hasOpenedOnce)
            showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        else
            openPending();
    }

    /**
     * Call this function to resize the emoji popup according to your soft keyboard size
     */
    public void setSizeForSoftKeyboard(){
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                rootView.getRootView().getWindowVisibleDisplayFrame(r);

                if(rootView.getRootView()
                        .getHeight()<screenHeight){
                    screenHeight = rootView.getRootView().getHeight();
                }

                int heightDifference = screenHeight
                        - (r.bottom - r.top);
                int resourceId = mContext.getResources()
                        .getIdentifier("status_bar_height",
                                "dimen", "android");
                if (resourceId > 0) {
                    heightDifference -= mContext.getResources()
                            .getDimensionPixelSize(resourceId);
                }
                if (heightDifference > 100) {
                    keyBoardHeight = heightDifference;
                    hasOpenedOnce=true;
                    setSize(WindowManager.LayoutParams.MATCH_PARENT, keyBoardHeight);
                    if(isOpened == false){
                        if(onSoftKeyboardOpenCloseListener!=null)
                            onSoftKeyboardOpenCloseListener.onKeyboardOpen(keyBoardHeight);
                    }
                    isOpened = true;
                    if(pendingOpen){
                        showAtBottom();
                        pendingOpen = false;
                    }
                }
                else{
                    isOpened = false;
                    if(onSoftKeyboardOpenCloseListener!=null){
                        onSoftKeyboardOpenCloseListener.onKeyboardClose();

                    }
                }
            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();
        recents.save();

    }

    /**
     * Set the listener for the event when backspace on is clicked
     */
    public void setOnBackspaceClickedListener(OnBackspaceClickedListener listener){
        this.onBackspaceClickedListener = listener;
    }

    public void openPending() {
        pendingOpen = true;
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et,0);
    }

    public interface OnBackspaceClickedListener {
        void onBackspaceClicked(View v);
    }

    public void setOnSoftKeyboardOpenCloseListener(OnSoftKeyboardOpenCloseListener listener){
        this.onSoftKeyboardOpenCloseListener = listener;
    }

    /**
     * Set the listener for the event when any of the emojicon is clicked
     */
    public void setOnEmojiClickedListener(EmojiGridView.OnEmojiClickedListener listener){
        this.onEmojiClickedListener = listener;
    }

    /**
     * Set the listener for the event when any of the sticker is clicked
     */
    public void setOnStickerClickedListener(EmojiGridView.OnStickerClickedListener listener){
        this.onStickerClickedListener = listener;
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

    public interface OnSoftKeyboardOpenCloseListener{
        void onKeyboardOpen(int keyBoardHeight);
        void onKeyboardClose();
    }
}
