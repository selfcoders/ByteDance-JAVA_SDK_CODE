package exception;

/**
 * <p>
 * 请求地址异常类
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/8 10:30
 */
public class HttpsUrlForMatException extends RuntimeException {
    public HttpsUrlForMatException(String message) {
        super(message);
    }
}
