package com.peter.demo.net;

/**
 * http回调接口
 * 
 * @ClassName: HttpCallbackListener
 * @author peter
 * @date 2014-12-23 下午6:24:17
 * 
 */
public interface HttpCallbackListener {
	public void onFinish(String response);

	public void onError(Exception e);
}
