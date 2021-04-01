package api;

import com.alibaba.fastjson.JSONObject;
import entity.ByteDanceBaseEntity;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 * API接口声明
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/8 14:53
 */
public interface ServiceApi {
    /**
     * <p>
     * 获取Access_token
     * </p>
     * <version>
     *
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 15:19
     * @return: {@link JSONObject}
     */
    JSONObject getAccessToken(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout);

    /**
     * <p>
     * 登录
     * </p>
     * <version>
     *
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 15:19
     * @return: {@link JSONObject}
     */
    JSONObject code2Session(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout);

    /**
     * <p>
     * 设置云缓存
     * </p>
     * <version>
     *
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 15:19
     * @return: {@link JSONObject}
     */
    JSONObject setUserStorage(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout);

    /**
     * <p>
     * 删除云缓存
     * </p>
     * <version>
     *
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 15:20
     * @return: {@link JSONObject}
     */
    JSONObject removeUserStorage(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout);

    /**
     * <p>
     * 获取二维码
     * </p>
     * <version>
     *
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 15:20
     * @return: {@link JSONObject}
     */
    String createQRCode(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout) throws UnsupportedEncodingException;

    /**
     * <p>
     * 订阅消息
     * </p>
     * <version>
     *
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 15:20
     * @return: {@link JSONObject}
     */
    JSONObject notify(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout);

    /**
     * <p>
     * 内容安全检测
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: hehw
     * @date:
     * @return: {@link JSONObject}
     */
    JSONObject contentSafe(ByteDanceBaseEntity obj, Map<String, Object> headers, int readTimeout, int connectionTimeout);


    /**
     * <p>
     * 图片检测
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param obj
     * @param readTimeout
     * @param connectionTimeout
     * @throws
     * @author: hehw
     * @date:
     * @return: {@link JSONObject}
     */
    JSONObject photoCheck(ByteDanceBaseEntity obj, int readTimeout, int connectionTimeout);
}
