package com.favric.myapplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrClass {

    private String numberClient;
    private String message ;
    private String concatenatedMessage ;

    public String getNumberClient() {
        return numberClient;
    }

    public void setNumberClient(String numberClient) {
        this.numberClient = numberClient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QrClass(String numberClient, String message) {
        this.numberClient = numberClient;
        this.message = message;
        this.concatenatedMessage = "https://api.whatsapp.com/send?phone=" + numberClient +"&text="+ message ;
    }

    public Bitmap getQr(Context context) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap finalBitmap = null;
        int qrWidth = 1050;
        int qrHeight = 1050;
        int centerX = qrWidth / 2;
        int centerY = qrHeight / 2;
        int blackThreshold = 100; // Ajusta este valor según tus necesidades
        Bitmap bitmap = null;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(concatenatedMessage, BarcodeFormat.QR_CODE, qrWidth, qrHeight);
            int[] pixels = new int[qrWidth * qrHeight];

            for (int y = 0; y < qrHeight; y++) {
                for (int x = 0; x < qrWidth; x++) {
                    int distanceToCenter = (int) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                    if (distanceToCenter > blackThreshold) {
                        pixels[y * qrWidth + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.TRANSPARENT;
                    } else {
                        pixels[y * qrWidth + x] = Color.WHITE;
                    }
                }
            }
            Bitmap qrBitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888);
            qrBitmap.setPixels(pixels, 0, qrWidth, 0, 0, qrWidth, qrHeight);
            Bitmap centralImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.logoentel);
            int desiredWidth = 120; // Cambia este valor al tamaño deseado
            int desiredHeight = 120;
            centralImage = Bitmap.createScaledBitmap(centralImage, desiredWidth, desiredHeight, true);
            finalBitmap = Bitmap.createBitmap(qrBitmap.getWidth(), qrBitmap.getHeight(), qrBitmap.getConfig());
            Canvas canvas = new Canvas(finalBitmap);

            // Dibujar el QR en el Canvas
            canvas.drawBitmap(qrBitmap, 0, 0, null);

            // Dibujar la imagen central (logo) en el medio del QR
            int centerXbit = (qrBitmap.getWidth() - centralImage.getWidth()) / 2;
            int centerYbit = (qrBitmap.getHeight() - centralImage.getHeight()) / 2;
            canvas.drawBitmap(centralImage, centerXbit, centerYbit, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalBitmap;
    }


}
