package api;

import com.alibaba.fastjson.JSONObject;
import constant.Constant;
import entity.ByteDanceBaseEntity;
import entity.RemoveUserStorage;
import entity.SetUserStorage;
import lombok.extern.slf4j.Slf4j;
import util.BeanConversionUtil;
import util.HttpsUtil;
import util.SignatureUtil;

import java.util.*;

/**
 * <p>
 * 请求执行器
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/8 14:56
 */
@Slf4j
public class ServiceActuator extends BaseActuator {
    private boolean dev = true;

    private String url = "";

    private ServiceActuator() {
    }

    public ServiceActuator(boolean dev, String url) {
        this.dev = dev;
        if (url != null && "".equals(url)) {
            this.url = url;
        }
    }

    @Override
    public JSONObject getAccessToken(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.GET_ACCESS_TOKEN;
        return requestGet(url, obj, null, readTimeout, connectionTimeout);
    }

    @Override
    public JSONObject code2Session(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.CODE_2SESSION;
        return requestGet(url, obj, null, readTimeout, connectionTimeout);
    }

    @Override
    public JSONObject setUserStorage(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.SET_USER_STORAGE;
        SetUserStorage setUserStorage = (SetUserStorage) obj;
        setUserStorage.setSig_method("hmac_sha256");
        List<SetUserStorage.KvItem> kv_list = setUserStorage.getKv_list();
        Map<String, Object> body = new HashMap<>();
        body.put("kv_list", kv_list);
        String sig = SignatureUtil.userSig(HttpsUtil.mapToJsonStr(body), setUserStorage.getSession_key());
        setUserStorage.setKv_list(null);
        setUserStorage.setSignature(sig);
        Map<String, Object> param = BeanConversionUtil.beanToMap(setUserStorage);
        url = HttpsUtil.urlFormat(url, param);
        String jsonStr = this.doPost(url, body, null, readTimeout, connectionTimeout);
        JSONObject result = JSONObject.parseObject(jsonStr);
        log.info("result:{}", result);
        return result;
    }

    @Override
    public JSONObject removeUserStorage(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.REMOVE_USER_STORAGE;
        RemoveUserStorage removeUserStorage = (RemoveUserStorage) obj;
        removeUserStorage.setSig_method("hmac_sha256");
        List<String> key = removeUserStorage.getKey();
        Map<String, Object> body = new HashMap<>();
        body.put("key", key);
        String sig = SignatureUtil.userSig(HttpsUtil.mapToJsonStr(body), removeUserStorage.getSession_key());
        removeUserStorage.setKey(null);
        removeUserStorage.setSignature(sig);
        Map<String, Object> param = BeanConversionUtil.beanToMap(removeUserStorage);
        url = HttpsUtil.urlFormat(url, param);
        String jsonStr = this.doPost(url, body, null, readTimeout, connectionTimeout);
        JSONObject result = JSONObject.parseObject(jsonStr);
        log.info("result:{}", result);
        return result;
    }


    @Override
    public String createQRCode(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.CREATE_QR_CODE;
        return requestImagePost(url, obj, null, readTimeout, connectionTimeout);
    }

    @Override
    public JSONObject notify(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.NOTIFY_URL;
        return requestPost(url, obj, null, readTimeout, connectionTimeout);
    }

    @Override
    public JSONObject contentSafe(ByteDanceBaseEntity obj, Map<String, Object> headers, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.CONTENT_SAFE;
        return requestPost(url, obj, headers, readTimeout, connectionTimeout);
    }

    @Override
    public JSONObject photoCheck(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) {
        String url = getUrl();
        url = url + Constant.PHOTO_CHECK;
        return requestPost(url, obj, null, readTimeout, connectionTimeout);
    }

    private String getUrl() {
        if (null != url && !"".equals(url)) {
            return this.url;
        }
        if (this.dev) {
            return Constant.DEV_URL;
        }
        return Constant.PROD_URL;
    }

    private JSONObject requestPost(String url, ByteDanceBaseEntity obj, Map<String, Object> headers, int readTimeout, int connectionTimeout) {
        Map<String, Object> param = BeanConversionUtil.beanToMap(obj);
        String jsonStr = this.doPost(url, param, headers, readTimeout, connectionTimeout);
        JSONObject result = JSONObject.parseObject(jsonStr);
        log.info("result:{}", result);
        return result;
    }

    private String requestImagePost(String url, ByteDanceBaseEntity obj, Map<String, Object> headers, int readTimeout, int connectionTimeout) {
        Map<String, Object> param = BeanConversionUtil.beanToMap(obj);
        String result = this.doImagePost(url, param, headers, readTimeout, connectionTimeout);
        log.info("result:{}", result);
        return result;
    }

    private JSONObject requestGet(String url, ByteDanceBaseEntity obj, Map<String, Object> headers, int readTimeout, int connectionTimeout) {
        Map<String, Object> param = BeanConversionUtil.beanToMap(obj);
        String jsonStr = this.doGet(url, param, headers, readTimeout, connectionTimeout);
        JSONObject result = JSONObject.parseObject(jsonStr);
        log.info("result:{}", result);
        return result;
    }
}
