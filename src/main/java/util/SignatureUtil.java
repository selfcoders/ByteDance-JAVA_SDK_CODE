package util;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p>
 * 数据签名工具类
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/9 10:48
 */
public class SignatureUtil {
    private static final String SIG_TYPE = "HmacSHA256";
    private static final String CHAR_SET = "UTF-8";

    /**
     * <p>
     * 用户登录态签名加密方法
     * </p>
     * <version>
     *  1.0
     * </version>
     *
     * @param jsonStr    请求Body的JsonStr
     * @param sessionKey
     * @throws
     * @author: chenbailin
     * @date: 2021/3/9 10:53
     * @return: {@link String}
     */
    public static String userSig(String jsonStr, String sessionKey) {
        try {
            Mac hmacSHA256 = Mac.getInstance(SIG_TYPE);
            SecretKeySpec hmacSHA2561 = new SecretKeySpec(sessionKey.getBytes(CHAR_SET), SIG_TYPE);
            hmacSHA256.init(hmacSHA2561);
            byte[] bytes = hmacSHA256.doFinal(jsonStr.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte item : bytes) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 支付加签方法
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param param  请求入参
     * @param secret 秘钥
     * @throws
     * @author: chenbailin
     * @date: 2021/3/10 17:42
     * @return: {@link String}
     */
    public static String paySig(TreeMap<String, Object> param, String secret) {
        TreeMap clone  = ((TreeMap) param.clone());
        if (null == clone || clone.isEmpty()) {
            throw new RuntimeException("支付入参不能为空");
        }
        clone.remove("sing");
        clone.remove("risk_info");
        Set<Map.Entry<String, Object>> entries = clone.entrySet();
        String urlParam = "";
        for (Map.Entry<String, Object> entry : entries) {
            if (null != entry.getValue()) {
                urlParam = urlParam + entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        urlParam = urlParam.substring(0, urlParam.length() - 1);
        urlParam = urlParam + secret;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md5.digest(urlParam.getBytes());
        String md5Code = new BigInteger(1, digest).toString(16);
        return md5Code;
    }

}
