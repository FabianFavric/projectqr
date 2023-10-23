package com.favric.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    PopupWindow popupWindow;

    Bitmap bitmapImage = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        QrClass qrClass = new QrClass("+56965928948" , "Hola , como estas ") ;

        Button button = findViewById(R.id.button2);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               showPopup();


            }
        });

    }

    private void showPopup() {
        QrClass qrClass = new QrClass("+56965928948","Hola ,como estas");
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_up_window, null);
        String TextDevice ="Consulta por Equipo.";


// Obtén una referencia al ImageView en la vista inflada (contentView)
        TextView textViewView = contentView.findViewById(R.id.textViewmessage);
                textViewView.setText(TextDevice);
        ImageView imageView = contentView.findViewById(R.id.imageqr);

// Genera la imagen QR usando la clase QrClass
        bitmapImage = qrClass.getQr(this);

// Establece la imagen generada en el ImageView
        imageView.setImageBitmap(bitmapImage);

        popupWindow = new PopupWindow(contentView, 1080, 1900, true);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);


    }
    public void letraButtonClick(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
    @Override
    protected void onDestroy() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        super.onDestroy();
    }
}