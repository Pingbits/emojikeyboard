package com.pingbits.emojitest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import pingbits.com.emojikeyboard.EmojiEditText;
import pingbits.com.emojikeyboard.EmojiGridView;
import pingbits.com.emojikeyboard.EmojiPopup;
import pingbits.com.emojikeyboard.EmojiTextView;
import pingbits.com.emojikeyboard.objects.Emoji;


public class MainActivity extends ActionBarActivity {


    public boolean isKeyboardOpenedOnce= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.openPop);
        Button up = (Button)findViewById(R.id.update);
        final EmojiTextView tv = (EmojiTextView)findViewById(R.id.emojitext);
        final EmojiEditText et = (EmojiEditText)findViewById(R.id.emojiedit);


        final EmojiPopup popup = new EmojiPopup(this,findViewById(R.id.root),getScreenSize());
        final ImageView preview = (ImageView)findViewById(R.id.stickerPreview);


        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popup.isShowing())
                    popup.dismiss();
            }
        });

        popup.setSizeForSoftKeyboard();
        popup.setOnEmojiClickedListener(new EmojiGridView.OnEmojiClickedListener() {
            @Override
            public void onEmojiconClicked(Emoji emoji) {

                if(et.getSelectionStart()==et.getSelectionEnd())
                    et.getText().insert(et.getSelectionStart()==-1?0:et.getSelectionStart(),emoji.emoji);
                else{
                    et.getText().replace(et.getSelectionStart(),et.getSelectionEnd(),emoji.emoji);
                }
            }
        });

        popup.setOnStickerClickedListener(new EmojiGridView.OnStickerClickedListener() {
            @Override
            public void onStickerClicked(String path) {
                File imgFile = new  File(path);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                preview.setImageBitmap(myBitmap);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isKeyboardOpenedOnce)
                    popup.showAtBottom();
                else{
                    popup.openPending();
                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et,0);
                }
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(et.getText().toString());
                popup.dismiss();
            }
        });

        popup.setOnSoftKeyboardOpenCloseListener(new EmojiPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {
                isKeyboardOpenedOnce = true;
            }

            @Override
            public void onKeyboardClose() {
                if(popup.isShowing())
                    popup.dismiss();
            }
        });
    }

    private int[] getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        int[] arr = new int[2];
        if (android.os.Build.VERSION.SDK_INT >= 13){
            display.getSize(size);
            arr[0] = size.x;
            arr[1] = size.y;
            return arr;
        }
        else{
            arr[0] = display.getWidth();
            arr[1] = display.getHeight();
            return arr;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
