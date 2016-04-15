package com.peter.demo.activity.multidown;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.peter.demo.R;
import com.peter.demo.base.BaseFragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 多线程断点续传下载
 * Created by songzhongkun on 16/3/28 18:01.
 */
public class MultiDownloadFragment extends BaseFragment {

    private int total = 0;
    private boolean downloading = false;
    private URL url;
    private File file;
    private int length;
    private Button btn_down;
    private ProgressBar progressBar;
    private EditText et_fileUrl;
    private List<HashMap<String, Integer>> threadList;

    /**
     * handler更新进度条
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                progressBar.setProgress(msg.arg1);
                if (msg.arg1 >= length) {
                    alert("下载完成");
                    btn_down.setText("下载");
                    progressBar.setVisibility(View.GONE);
                }
            }
            return false;
        }
    });

    @Override
    public int layoutId() {
        return R.layout.fragment_mulit_download;
    }

    @Override
    public void initUI() {
        progressBar = (ProgressBar) findViewById(R.id.progress);
        et_fileUrl = (EditText) findViewById(R.id.path);
        btn_down = (Button) findViewById(R.id.btn_download);
        threadList = new ArrayList<>();
    }

    @Override
    public void initData() {
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloading) {
                    downloading = false;
                    btn_down.setText("下载");
                    return;
                }
                downloading = true;
                btn_down.setText("暂停");

                progressBar.setVisibility(View.VISIBLE);

                if (threadList.size() == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                url = new URL(et_fileUrl.getText().toString());
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setConnectTimeout(5000);
                                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NF 5.1; Trident/4.0; .NET CLR 2.0.50)");
                                length = conn.getContentLength();

                                Log.d("Download total length:", "" + length);

                                progressBar.setMax(length);
                                progressBar.setProgress(0);

                                if (length < 0) {
                                    alert("文件不存在");
                                    return;
                                }

                                file = new File(Environment.getExternalStorageDirectory(), getFileName(et_fileUrl.getText().toString()));
                                RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
                                randomFile.setLength(length);


                                int blockSize = length / 3;
                                for (int i = 0; i < 3; i++) {
                                    int begin = i * blockSize;
                                    int end = (i + 1) * blockSize;
                                    if (i == 2) {
                                        end = length;
                                    }

                                    HashMap<String, Integer> map = new HashMap<>();
                                    map.put("begin", begin);
                                    map.put("end", end);
                                    map.put("finished", 0);
                                    threadList.add(map);

                                    //创建新的线程，下载文件
                                    new Thread(new DownloadRunnable(begin, end, file, url, i)).start();
                                }

                            } catch (MalformedURLException e) {
                                alert("URl不正确");
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    //恢复下载
                    for (int i = 0; i < threadList.size(); i++) {
                        HashMap<String, Integer> map = threadList.get(i);
                        int begin = map.get("begin");
                        int end = map.get("end");
                        int finished = map.get("finished");
                        new Thread(new DownloadRunnable(begin + finished, end, file, url, i)).start();
                    }
                }
            }
        });
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("多线程断点续传下载");
    }

    /**
     * 获取文件名
     *
     * @param url 全路径
     * @return 文件名
     */
    private String getFileName(String url) {
        int index = url.lastIndexOf("/") + 1;
        return url.substring(index);
    }

    /**
     * 现在线程
     */
    class DownloadRunnable implements Runnable {
        private int begin;
        private int end;
        private File file;
        private URL url;
        private int id;

        public DownloadRunnable(int begin, int end, File file, URL url, int id) {
            this.begin = begin;
            this.end = end;
            this.file = file;
            this.url = url;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                if (begin > end) {
                    return;
                }
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NF 5.1; Trident/4.0; .NET CLR 2.0.50)");
                conn.setRequestProperty("Range", "bytes=" + begin + "-" + end);

                InputStream is = conn.getInputStream();
                byte[] buf = new byte[1024 * 1024];
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
                randomFile.seek(begin);
                int len;
                HashMap<String, Integer> map = threadList.get(id);
                while ((len = is.read(buf)) != -1 && downloading) {
                    randomFile.write(buf, 0, len);
                    updateProgress(len);
                    map.put("finished", map.get("finished") + len);
                    Log.d("Download length:", "" + total);
                }

                is.close();
                randomFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新进度条
     */
    synchronized private void updateProgress(int add) {
        total += add;
        handler.obtainMessage(0, total, 0).sendToTarget();
    }
}
