package com.peter.common.net;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.peter.common.R;
import com.peter.common.utils.LogUtils;
import com.peter.common.utils.ProgressUtil;
import com.peter.common.utils.log.ILog;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.HttpHostConnectException;

/**
 * Created by songzhongkun on 15/9/24.
 */
public abstract class MyJsonResponseHandler<JSON_TYPE> extends BaseJsonHttpResponseHandler<JSON_TYPE> {
    private static final String TAG = "MyJsonResponseHandler<JSON_TYPE>";
    private static ILog logger = LogUtils.getLogger(TAG);
    private static final Gson mGson = new Gson();
    private Dialog mDialog;
    private Activity mContext;
    private boolean isShowProgress = true;
    private DialogInterface.OnCancelListener mOnDialogCancelListener_outer;

    protected Gson getGson() {
        return mGson;
    }

    public MyJsonResponseHandler(Activity context) {
        this(context, true);
    }

    public MyJsonResponseHandler(Activity context, boolean isShowProgress) {
        this(DEFAULT_CHARSET, context, isShowProgress);
    }

    public MyJsonResponseHandler(String encoding, Activity context, boolean isShowProgress) {
        super(encoding);
        if (context == null)
            throw new NullPointerException();
        mContext = context;
        this.isShowProgress = isShowProgress;

        mDialog = new ProgressUtil(mContext);

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {

                if (null != arg2 && arg2.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    arg0.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCancel() {
        dismissDialog();
        super.onCancel();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mContext instanceof Activity) {
            Activity activity = mContext;
            if (activity == null || activity.isFinishing()) {
                return;
            }
            try {
                if (isShowProgress) {
                    if (mDialog == null) {
                        mDialog = new ProgressUtil(mContext);
                    } else {
                        mDialog.show();
                    }
                }
            } catch (Exception e) {
                logger.error("onStart" + e);
            }
        }
    }

    public void setOnDialogCancelListener(DialogInterface.OnCancelListener onDialogCancelListener) {
        mOnDialogCancelListener_outer = onDialogCancelListener;
    }

    @Override
    public void onFinish() {
        super.onFinish();
        dismissDialog();
    }

    private void dismissDialog() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {
            logger.error("" + e);
            try {
                if (mDialog != null) {
                    mDialog.dismiss();
                    mDialog = null;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                logger.error("" + e);
            }
        }
    }

    @Override
    public void onFailure(int arg0, Header[] arg1, Throwable ex1, String responseString,
                          JSON_TYPE arg4) {
        dismissDialog();
        handleException(mContext, arg0, ex1, responseString);
    }

    private void handleException(Activity activity, int httpCode, Throwable ex1,
                                 String responseString) {
        String ex1_className = (ex1 == null ? "null" : ex1.getClass().getName());
        String ex1_message = (ex1 == null ? "null" : ex1.getMessage());
        logger.error(String.format(
                "【http异常：】httpCode=%s, exceptionClass=%s,exceptionMessage=%s, responseString=%s",
                httpCode, ex1_className, ex1_message, responseString));

        if (ex1 instanceof JsonSyntaxException) {
            alert("解析服务端返回的数据时，发生了序列化错误");
            return;
        }
        if (httpCode == 404) {
            alert(R.string.http_404_code);
            return;
        }

        if (ex1 instanceof ConnectTimeoutException || ex1 instanceof HttpHostConnectException) {
            alert("网络请求超时，请检查网络是否通畅");
            return;
        } else if (ex1 instanceof IOException) {
            alert("网络异常");
            return;
        }
        if (httpCode >= 400 && httpCode < 500) {
            alert(R.string.http_400_code);
            return;
        } else if (httpCode >= 500 && httpCode <= 600) {
            alert(R.string.http_500_code);
            return;
        }
        if (httpCode == 200) {// httpCode=0时，说明未收到来自服务端的响应
            if (TextUtils.isEmpty(responseString)) {
                alert("远程服务返回了空数据");
                return;
            }

            JSONObject object;
            try {
                object = new JSONObject(responseString);
                // int code = object.getInt("code");
                String msg = object.getString("msg");
                alert(msg);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
        alert("意外的错误：" + (ex1 == null ? "" : ex1.getMessage()));
    }

    /**
     * toast提示
     *
     * @param msg 提示文字
     */
    public void alert(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * toast提示
     *
     * @param resourceId 提示资源
     */
    public void alert(int resourceId) {
        Toast.makeText(mContext, resourceId, Toast.LENGTH_LONG).show();
    }

}
