package pingbits.com.emojikeyboard;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by aagam on 11/2/15.
 */
public class StickerManager {

    public SharedPreferences pref;
    public Context mContext;
    public List<String> stickerURL;
    public StickerManager(Context ctx){
        mContext = ctx;
        pref=ctx.getSharedPreferences("emoji",Context.MODE_PRIVATE);
    }

    public List<String> getStickerURLs(){
        stickerURL = new ArrayList<>();
        String str = pref.getString("stickers", "");
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        while (tokenizer.hasMoreTokens()) {
            try {
                String s = tokenizer.nextToken();
                stickerURL.add(s);
            }
            catch (NumberFormatException e) {
                // ignored
            }
        }

        return stickerURL;
    }

}
