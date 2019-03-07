package br.com.pescadosdobrasil.pescadosdobrasil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

import static java.security.AccessController.getContext;

public class ImageViewActivity extends AppCompatActivity {

    private DialogHelper dialogHelper;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_view);

        dialogHelper = new DialogHelper(ImageViewActivity.this);

        int tipoPescado = getIntent().getExtras().getInt("tipo_pescado");
        String imageName = getIntent().getExtras().getString("img_principal");
        final ImageView imageView = findViewById(R.id.imageView);

        String imgAddr = PDBServer.getImageAddress(imageName, AppUtils.getFisheryType(tipoPescado, true), false);

        Picasso.get().load(imgAddr).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                findViewById(R.id.progressBar).setVisibility(View.GONE);

                imageView.setImageBitmap(bitmap);

                image = bitmap;

                PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);

                attacher.setMinimumScale(1.0f);
                attacher.setMediumScale(1.5f);
                attacher.setMaximumScale(2.5f);

                findViewById(R.id.buttonShare).setVisibility(View.VISIBLE);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                findViewById(R.id.progressBar).setVisibility(View.GONE);
                findViewById(R.id.textFalha).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public void share(View view) {

        dialogHelper.showProgressDelayed(500, new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(Intent.ACTION_SEND);

                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(image));

                startActivity(Intent.createChooser(i, "Compartilhar imagem"));
            }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {

        Uri bmpUri = null;

        try {

            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "sasp-share-" + System.currentTimeMillis() + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        }
        catch (IOException e) { }

        return bmpUri;
    }

    public void finishActivity(View view) {

        finish();
    }
}
