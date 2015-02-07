package pingbits.com.emojikeyboard;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pingbits.com.emojikeyboard.objects.Emoji;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.openPop);
        TextView tv = (TextView)findViewById(R.id.textemojis);
        final EmojiEditText et = (EmojiEditText)findViewById(R.id.emojiedit);
        final EmojiPopup popup = new EmojiPopup(this,findViewById(R.id.root));
        popup.setOnEmojiClickedListener(new EmojiGridView.OnEmojiClickedListener() {
            @Override
            public void onEmojiconClicked(Emoji emoji) {
                et.append(emoji.emoji);
                et.append(" ");
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.showAtBottom();
            }
        });
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
