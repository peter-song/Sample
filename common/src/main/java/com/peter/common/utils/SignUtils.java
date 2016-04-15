package com.peter.common.utils;

import android.text.TextUtils;

import com.peter.common.utils.log.ILog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtils {

	private static final String TAG = "SignUtils";
	private static ILog logger = LogUtils.getLogger(TAG);

	public static String signByMd5(Map<String, String> params, String secret) {

		List<String> list = new ArrayList<String>(params.keySet());
		// 对key键值按字典升序排序
		Collections.sort(list);
		// 加密串
		StringBuffer sb = new StringBuffer("");

		for (String key : list) {
			String value = params.get(key);
			if (TextUtils.isEmpty(value)) {
				continue;
			} else {
				sb.append(key).append("=").append(value).append("&");
			}
		}
		sb.append("secret=").append(secret);
		logger.debug("signStr:   " + sb.toString());

		return MD5Util.GetMD5Code(sb.toString());

	}
}
