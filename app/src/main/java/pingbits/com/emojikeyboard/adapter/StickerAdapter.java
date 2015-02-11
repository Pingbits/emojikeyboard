package pingbits.com.emojikeyboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import pingbits.com.emojikeyboard.R;

/**
 * Created by aagam on 11/2/15.
 */
public class StickerAdapter extends BaseAdapter {

    public LayoutInflater inflater;
    public Context ctx;
    public List<String> stickerList;

    public StickerAdapter(Context context, List<String> stickerList) {
        ctx = context;
        inflater = LayoutInflater.from(ctx);
        this.stickerList = stickerList;
    }

    @Override
    public int getCount() {
        return stickerList.size();
    }

    @Override
    public Object getItem(int position) {
        return stickerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_sticker_grid,null,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        File imgFile = new  File(stickerList.get(position));
        /*Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        holder.emojiIcon.setImageBitmap(myBitmap);*/

        Picasso.with(ctx).load(imgFile).into(holder.emojiIcon);
        return convertView;
    }

    public static class ViewHolder{
        public ImageView emojiIcon;
        public ViewHolder(View view){
            emojiIcon = (ImageView)view.findViewById(R.id.emoji_icon);
        }
    }
}
