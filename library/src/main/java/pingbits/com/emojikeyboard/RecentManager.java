package pingbits.com.emojikeyboard;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import pingbits.com.emojikeyboard.objects.Emoji;

/**
 * Created by aagam on 9/2/15.
 */
public class RecentManager {

    public List<Emoji> emojis;


    public SharedPreferences sp;
    public RecentManager(Context ctx){
        sp = ctx.getSharedPreferences("emoji", Context.MODE_PRIVATE);
    }

    public Emoji[] getRecents(){
        emojis = new ArrayList<>();
            String str = sp.getString("recent", "");
            StringTokenizer tokenizer = new StringTokenizer(str, ",");
            while (tokenizer.hasMoreTokens()) {
                try {
                    String s = tokenizer.nextToken();
                    Emoji emoji = Emoji.fromCodePoint(Integer.parseInt(s));
                    emojis.add(emoji);
                }
                catch (NumberFormatException e) {
                    // ignored
                }
            }

        return toArray(emojis);
    }

    public Emoji[] toArray(List<Emoji> list){
        Emoji[] arr = new Emoji[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = emojis.get(i);
        }
        return arr;
    }


    public Emoji[] addEmoji(Emoji emoji){
        if(emojis.contains(emoji)){
            emojis.remove(emoji);
        }
        List<Emoji> newE = new ArrayList<>();
        newE.add(emoji);

        int minsize = emojis.size()<40?emojis.size():40;
        for (int i = 0; i < minsize; i++) {
            newE.add(emojis.get(i));
        }
        emojis = newE;
        return toArray(emojis);
    }

    public void saveData(){
        StringBuilder str = new StringBuilder();

        int size = emojis.size();
        for (int i = 0; i < size; i++) {
            str.append(emojis.get(i).codepoint);
            if (i < (size - 1)) {
                str.append(',');
            }
        }

        sp.edit().putString("recent", str.toString()).apply();
    }
}
