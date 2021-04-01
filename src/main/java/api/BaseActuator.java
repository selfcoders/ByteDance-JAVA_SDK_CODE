package api;

import util.HttpsUtil;

import java.util.Map;

/**
 * <p>
 * Base执行器
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/8 14:50
 */
public abstract class BaseActuator implements ServiceApi {

    protected String doGet(String url, Map<String, Object> param, Map<String, Object> headers, int readTimeOut, int connectionTimeOut) {
        return HttpsUtil.doGet(url, param, headers, readTimeOut, connectionTimeOut);
    }

    protected String doPost(String url, Map<String, Object> param, Map<String, Object> headers, int readTimeOut, int connectionTimeOut) {
        return HttpsUtil.doPost(url, param, headers, readTimeOut, connectionTimeOut);
    }

    protected String doImagePost(String url, Map<String, Object> param, Map<String, Object> headers, int readTimeOut, int connectionTimeOut) {
        return HttpsUtil.doPostImage(url, param, headers, readTimeOut, connectionTimeOut);
    }
}
