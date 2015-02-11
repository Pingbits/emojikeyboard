package pingbits.com.emojikeyboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pingbits.com.emojikeyboard.EmojiManager;
import pingbits.com.emojikeyboard.R;
import pingbits.com.emojikeyboard.objects.Emoji;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojiAdapter extends BaseAdapter {

    public LayoutInflater inflater;
    public Context ctx;
    public Emoji[] emojis;
    public EmojiAdapter(Context context, Emoji[] mData) {
        ctx = context;
        inflater = LayoutInflater.from(ctx);
        emojis = mData;
    }

    @Override
    public int getCount() {
        return emojis.length;
    }

    @Override
    public Object getItem(int position) {
        return emojis[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_emoji_grid,null,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }
//        holder.emojiIcon.setImageResource(EmojiManager.sEmojisMap.get(emojis[position].codepoint));
        Picasso.with(ctx).load(EmojiManager.sEmojisMap.get(emojis[position].codepoint)).into(holder.emojiIcon);
        return convertView;
    }

    public static class ViewHolder{
        public ImageView emojiIcon;
        public ViewHolder(View view){
            emojiIcon = (ImageView)view.findViewById(R.id.emoji_icon);
        }
    }
}
