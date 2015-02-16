package pingbits.com.emojikeyboard;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pingbits.com.emojikeyboard.adapter.EmojiAdapter;
import pingbits.com.emojikeyboard.adapter.StickerAdapter;
import pingbits.com.emojikeyboard.objects.Emoji;
import pingbits.com.emojikeyboard.objects.EmojisData;

/**
 * Created by aagam on 6/2/15.
 */
public class EmojiGridView {

    private final List<String> stickerURL;
    public List<String> stickersPath;
    private  EmojiPopup mEmojiconPopup;
    public  View rootView;
    private  Emoji[] mData;
    public Context context;
    public EmojiPopup emojiconPopup;
    public RecentManager recentManager;
    public EmojiAdapter mAdapter;
    public GridView gridView;
    public Emoji lastEmoji=null;
    public int position;
    public String md5;
    public StickerAdapter stickerAdapter;
    public DonutProgress donutProgress;
    public ImageView dowload;


    public EmojiGridView(Context context, EmojiPopup emojiconPopup, int position, List<String> stickerURL) {

        this.context = context;
        this.emojiconPopup = emojiconPopup;
        this.stickerURL = stickerURL;
        this.position = position;
        switch (position){
            case 0:
                setRecents();
                break;
            case 1:
                setEmoji();
                break;
            default:
                setStickers();
                break;
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

    private void setStickers() {
        position = position-2;
        String s = stickerURL.get(position);
        final File f = new File(Environment.getExternalStorageDirectory()
                + "/stickers-pingbits/"+Utils.md5(s));

        Log.e("file ", f.isDirectory()+"  exits: "+f.exists());
        Log.e("path",f.getPath());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmojiconPopup = emojiconPopup;
        rootView = inflater.inflate(R.layout.sticker_grid, null);
        donutProgress = (DonutProgress)rootView.findViewById(R.id.donut_progress);

        gridView = (GridView) rootView.findViewById(R.id.emoji_gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mEmojiconPopup.onStickerClickedListener != null) {
                    mEmojiconPopup.onStickerClickedListener.onStickerClicked(stickersPath.get(position));
                }
//                Log.e("Sticker clicked",""+stickersPath.get(position));
            }
        });

        if(!f.isDirectory()){
            dowload= (ImageView)rootView.findViewById(R.id.download);
            dowload.setVisibility(View.VISIBLE);
            dowload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //downloadSticker(f);
                    f.mkdirs();
                    dowload.setVisibility(View.GONE);
                    new DownloadFileAsync().execute(stickerURL.get(position),
                            f.getPath()+"/"+position+"2.zip");
                }
            });
        } else{
            setStickerInGrid(f);
        }
    }

    private void setStickerInGrid(File f) {
        stickersPath = Utils.listFilesForFolder(f);
        stickerAdapter = new StickerAdapter(context,stickersPath);
        gridView.setAdapter(stickerAdapter);

    }

    private void downloadSticker(File f) {

        File newFile = new File(f.getPath()+"/"+position+"2.zip");
        try {
            if(!newFile.exists())
                newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        Ion.with(context)
                .load(stickerURL.get(position))
                .progressBar(donutProgress)
                .progressDialog(mProgressDialog)
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        System.out.println("" + downloaded + " / " + total);
                       *//* int prog = (int) ((downloaded * 100) / (total));
                        updateProg(prog);*//*

                    }
                })
                .write(newFile)
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        Log.e("Completed",""+file.getPath());
                    }
                });*/
    }

    public void save() {
        recentManager.saveData();
    }

    public interface OnEmojiClickedListener {
        void onEmojiconClicked(Emoji emoji);
    }

    public interface OnStickerClickedListener{
        void onStickerClicked(String path);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        public boolean isError=false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            donutProgress.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            File newFile = new File(aurl[1]);
            try {

            //Never ever use https requests else in all devices >4.0 it won't connect
            //^ This bug caused me 3 hours to solve

                URL url = new URL(aurl[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int lenghtOfFile = urlConnection.getContentLength();

                newFile = new File(aurl[1]);
                try {
                    if(!newFile.exists())
                        newFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream input = urlConnection.getInputStream();
                OutputStream output = new FileOutputStream(newFile);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                Utils.unzip(newFile.getPath(),newFile.getParentFile().getPath());
            } catch (Exception e) {
                isError = true;
                e.printStackTrace();
            }

            return isError?"":newFile.getParentFile().getPath();

        }
        protected void onProgressUpdate(String... progress) {
            donutProgress.setProgress(Integer.parseInt(progress[0])>3?Integer.parseInt(progress[0])-3:0);
        }

        @Override
        protected void onPostExecute(String unused) {

            donutProgress.setProgress(100);
            if(unused.equals("")){
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            } else{
                donutProgress.setVisibility(View.GONE);
                dowload.setVisibility(View.GONE);
                setStickerInGrid(new File(unused));
            }
        }
    }

}
