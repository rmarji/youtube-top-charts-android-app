package com.yahia.libs.InternetConnections;

import java.net.URL;

import android.os.AsyncTask;

public class ConnectionTask extends AsyncTask<URL, Integer, Long> {
    @Override
	protected Long doInBackground(URL... urls) {
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
            //totalSize += Downloader.downloadFile(urls[i]);
            publishProgress((int) ((i / (float) count) * 100));
        }
        return totalSize;
    }

    @Override
	protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    @Override
	protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
}
