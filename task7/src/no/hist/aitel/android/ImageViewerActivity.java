package no.hist.aitel.android;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import no.hist.aitel.android.utils.HttpUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ImageViewerActivity extends Activity {

    private static final String TAG = ImageViewerActivity.class.getSimpleName();
    private static final int PROGRESS_DIALOG = 0;
    private static final String TMP_FILE = "rotated.png";
    private Cursor thumbnailCursor;
    private Cursor imageCursor;
    private ProgressDialog progressDialog;
    private Properties properties;
    private String imagePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        thumbnailCursor = managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Thumbnails._ID}, null, null,
                MediaStore.Images.Thumbnails.IMAGE_ID);

        imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA}, null, null,
                MediaStore.Images.Media._ID);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                imageCursor.moveToPosition(position);
                imagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                rotateAndStartUpload();
            }
        });
        readProperties();
    }

    private void readProperties() {
        try {
            InputStream inputStream = getAssets().open("url.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "Could not read properties file", e);
        }
    }

    private void rotateAndStartUpload() {
        try {
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            Matrix were_in_it = new Matrix();
            were_in_it.postRotate(180);
            Bitmap image = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), were_in_it, true);
            FileOutputStream fos = openFileOutput(TMP_FILE, MODE_WORLD_READABLE);
            image.compress(Bitmap.CompressFormat.PNG, 0, fos);
            fos.close();
            new UploadTask().execute(properties.getProperty("uploadUrl"));
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PROGRESS_DIALOG: {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getResources().getString(
                        R.string.wait));
                progressDialog.setCancelable(false);
                return progressDialog;
            }
            default: {
                return null;
            }
        }
    }

    private Uri getThumbnailUri(final int position) {
        return Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                String.valueOf(getImageId(position)));
    }

    private int getImageId(final int position) {
        thumbnailCursor.moveToPosition(position);
        return thumbnailCursor.getInt(thumbnailCursor.getColumnIndex(MediaStore.Images.Media._ID));
    }

    private class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return thumbnailCursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ImageView imageView;
            if (view == null) {
                imageView = new ImageView(context);
                imageView.setImageURI(getThumbnailUri(position));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(8, 8, 8, 8);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            } else {
                imageView = (ImageView) view;
            }
            return imageView;
        }
    }

    private class UploadTask
            extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            showDialog(PROGRESS_DIALOG);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            return HttpUtil.postFile(urls[0], String.format("%s/%s", getFilesDir(), TMP_FILE));
        }

        @Override
        protected void onPostExecute(String body) {
            progressDialog.dismiss();
            if (body != null) {
                String url = body.trim();
                if (url.length() > 0) {
                    final Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            }
        }
    }


}
