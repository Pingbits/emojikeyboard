package pingbits.com.emojikeyboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import pingbits.com.emojikeyboard.R;

/**
 * Created by aagam on 7/2/15.
 */
public class EmojiTitleAdapter extends BaseAdapter {
    public Context ctx;
    public List<Integer> icons;
    public LayoutInflater inflater;

    public EmojiTitleAdapter(Context context, List<Integer> icons){
        ctx = context;
        this.icons = icons;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_emoji_title,null,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.title_tab.setImageResource(R.drawable.ic_backspace_white_24dp);

        return convertView;
    }

    public static class ViewHolder{
        public ImageView title_tab;
        public ViewHolder(View v){
            title_tab = (ImageView)v.findViewById(R.id.emoji_title);
        }

    }
}
