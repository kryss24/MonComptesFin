package com.example.moncomptes;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//content://media/external/images/media/41755   data.getdata()
//data/user/0/com.example.moncomptes/files   getFileDir()
public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri lien;
    ImageView mylog;
    File imgfile;
    String spaceForId = "      ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView ide = findViewById(R.id.id);
        TextView notif = findViewById(R.id.text_notif);
        TextView textCentreActivite = findViewById(R.id.text_centre_activite);
        TextView textEvaluation = findViewById(R.id.text_evaluation);
        TextView textCentreAide = findViewById(R.id.text_centre_aide);
        TextView textShare = findViewById(R.id.text_share);
        TextView textReglage = findViewById(R.id.text_reglage);
        TextView textSecurite = findViewById(R.id.text_securite);

        Button BOUT = findViewById(R.id.submit);


        //Importation de la photo de profil
        mylog = findViewById(R.id.icon);
        imgfile = new File(getFilesDir(),"mon_images.jpg");
        if(imgfile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
            mylog.setImageBitmap(bitmap);
        }
        else{
            mylog.setImageResource(R.drawable.icon);
        }

        mylog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        //Fin


        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ide.setText(spaceForId + "ID:129698");
            }
        });
        textCentreAide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ide.setText(spaceForId + "ID:129699");
            }
        });
        textCentreActivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ide.setText(spaceForId + "ID:129700");
            }
        });
        textEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ide.setText(spaceForId + "ID:129701");
            }
        });
        textShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ide.setText(spaceForId + "ID:129702");
            }
        });
        textReglage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ide.setText(spaceForId + "ID:129703");
            }
        });
        textSecurite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ide.setText(spaceForId + "ID:129704");
            }
        });

    }
    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    private boolean saveImage(Uri selectedImageUri, String fileName){
        boolean saved = false;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(selectedImageUri);
            File file = new File(getFilesDir(), fileName +".jpg");
            outputStream = new FileOutputStream(file);
            int read;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer))!=-1){
                //assert false;
                outputStream.write(buffer,0,read);
            }
            saved = true;
            mylog.setImageBitmap(BitmapFactory.decodeStream(inputStream));
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return saved;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST && data !=null){
            Uri selectedImageUri = data.getData();
            //notif.setText(selectedImageUri.toString());
            boolean saved = saveImage(selectedImageUri, "mon_images");
            lien = selectedImageUri;
            //img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.mon_image));
            if (saved){
                Toast.makeText(this, "Image importee et enregistree dans les ressources", Toast.LENGTH_SHORT).show();
                if(imgfile.exists()){
                    Bitmap bitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
                    mylog.setImageBitmap(bitmap);
                }
                else{
                    mylog.setImageResource(R.drawable.mon_image);
                }
            }else {
                Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
            }
        }
    }
}