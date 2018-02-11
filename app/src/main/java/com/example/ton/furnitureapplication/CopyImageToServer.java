package com.example.ton.furnitureapplication;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class CopyImageToServer {

    private OkHttpHelper okHttpHelper = new OkHttpHelper();

    public static String uploadFiletoServer(String strSDPath, String strUrlServer) {
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int resCode = 0;
        String resMessage = "";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            File file = new File(strSDPath);
            if (!file.exists()) {
                return "{\"StatusID\":\"0\",\"Error\":\"Please check path on SD Card\"}";
            }
            FileInputStream fileInputStream = new FileInputStream(new File(strSDPath));
            URL url = new URL(strUrlServer);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            DataOutputStream outputStream = new DataOutputStream(conn
                    .getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream
                    .writeBytes("Content-Disposition: form-data; name=\"filUpload\";filename=\""
                            + strSDPath + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = is.read()) != -1) {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();
                resMessage = new String(result);
            }
            Log.e("resCode=", Integer.toString(resCode));
            Log.e("resMessage=", resMessage.toString());
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            return resMessage.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    public void sendFileToServer(String strSDPath, String strUrlServer, String actionMode) {

        File sdCard = Environment.getExternalStorageDirectory();
        String imageStorageFolder = File.separator + "DCIM" + File.separator + "Camera" + File.separator;
        Bitmap bitmap = resource.BitmapManager.decode(sdCard+imageStorageFolder+strSDPath, 1000, 1500);
        String imgString = BitmapManager.getEncoded64ImageStringFromBitmap(bitmap);
        bitmap.recycle();
        System.gc();

        FormBody.Builder params = new FormBody.Builder()
                .add("image", imgString)
                .add("MODE",actionMode)
                .add("filename", sdCard+imageStorageFolder+strSDPath);
        RequestBody formBody = params.build();

        Log.e("IMG NAME",sdCard+imageStorageFolder+strSDPath);
        Log.e("IMG STR",imgString);

        okHttpHelper.serverRequest(strUrlServer,formBody);
    }

}
