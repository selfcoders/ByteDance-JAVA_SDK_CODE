package util;

import com.alibaba.fastjson.JSONObject;
import exception.HttpsUrlForMatException;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * <p>
 * 请求工具类
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/5 17:46
 */
@Slf4j
public class HttpsUtil {

    /**
     * https:GET请求
     *
     * @param url            请求地址
     * @param params         入参
     * @param readTimeOut    读取超时时间
     * @param connectTimeOut 连接超时时间
     * @return
     */
    public static String doGet(String url, Map<String, Object> params, Map<String, Object> headers, int readTimeOut, int connectTimeOut) {
        url = urlFormat(url, params);
        log.info("请求地址:{},入参:{}", url, params);
        StringBuilder buffer = new StringBuilder();
        SSLContext sslContext;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            TrustManager[] tm = {new MyX509TrustManager()};
            sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
            connection.setRequestMethod("GET");
            if (readTimeOut != 0) {
                connection.setReadTimeout(readTimeOut);
            }
            if (connectTimeOut != 0) {
                connection.setConnectTimeout(connectTimeOut);
            }
            if (null != headers && !headers.isEmpty()) {
                Set<String> headersKey = headers.keySet();
                for (String header : headersKey) {
                    connection.setRequestProperty(header, ((String) headers.get(header)));
                }
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            log.info("接口响应数据:{}", buffer.toString());
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bufferedReader, inputStreamReader, inputStream);
            if (null != connection) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * <p>
     * GET 请求下入参转换成url
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param url
     * @param params
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 14:17
     * @return: {@link String}
     */
    public static String urlFormat(String url, Map<String, Object> params) {
        url = url.trim();
        if (!url.startsWith("https")) {
            throw new HttpsUrlForMatException("请求地址应为https前缀");
        }
        if (url.endsWith("&") || url.endsWith("?")) {
            return doGetParamFormat(url, params);
        } else {
            url = url + "?";
            return doGetParamFormat(url, params);
        }
    }

    /**
     * <p>
     * 参数格式化
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param url
     * @param params
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 14:18
     * @return: {@link String}
     */
    private static String doGetParamFormat(String url, Map<String, Object> params) {
        String paramStr = "";
        boolean empty = true;
        for (String str : params.keySet()) {
            paramStr = paramStr + str + "=" + params.get(str) + "&";
            empty = false;
        }
        if (empty) {
            url = url.substring(0, url.length() - 1);
            return url;
        } else {
            paramStr = paramStr.substring(0, paramStr.length() - 1);
            url = url + paramStr;
            return url;
        }
    }

    /**
     * <p>
     * https POST请求
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param url            请求地址
     * @param param          入参
     * @param readTimeOut    读取超时时间
     * @param connectTimeOut 连接超时时间
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 14:18
     * @return: {@link java.lang.String}
     */
    public static String doPost(String url, Map<String, Object> param, Map<String, Object> headers, int readTimeOut, int connectTimeOut) {
        StringBuilder buffer = new StringBuilder();
        SSLContext sslContext;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String jsonStr = mapToJsonStr(param);
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            TrustManager[] tm = {new MyX509TrustManager()};
            sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
            connection.setRequestMethod("POST");
            if (readTimeOut != 0) {
                connection.setReadTimeout(readTimeOut);
            }
            if (connectTimeOut != 0) {
                connection.setConnectTimeout(connectTimeOut);
            }
            connection.setRequestProperty("Content-Type", "application/json");
            if (null != headers && !headers.isEmpty()) {
                Set<String> headersKey = headers.keySet();
                for (String header : headersKey) {
                    connection.setRequestProperty(header, ((String) headers.get(header)));
                }
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            outputStream = connection.getOutputStream();
            if (null != outputStream) {
                outputStream.write(jsonStr.getBytes("UTF-8"));
            }
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bufferedReader, inputStreamReader, inputStream, outputStream);
            if (null != connection) {
                connection.disconnect();
            }
        }
        return null;

    }

    /**
     * <p>
     * https POST请求
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param url            请求地址
     * @param param          入参
     * @param readTimeOut    读取超时时间
     * @param connectTimeOut 连接超时时间
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 14:18
     * @return: {@link java.lang.String}
     */
    public static String doPostImage(String url, Map<String, Object> param, Map<String, Object> headers, int readTimeOut, int connectTimeOut) {
        SSLContext sslContext;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String jsonStr = mapToJsonStr(param);
        ByteArrayOutputStream imageOutputStream = null;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
            TrustManager[] tm = {new MyX509TrustManager()};
            sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
            connection.setRequestMethod("POST");
            if (readTimeOut != 0) {
                connection.setReadTimeout(readTimeOut);
            }
            if (connectTimeOut != 0) {
                connection.setConnectTimeout(connectTimeOut);
            }
            connection.setRequestProperty("Content-Type", "application/json");
            if (null != headers && !headers.isEmpty()) {
                Set<String> headersKey = headers.keySet();
                for (String header : headersKey) {
                    connection.setRequestProperty(header, ((String) headers.get(header)));
                }
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            outputStream = connection.getOutputStream();
            if (null != outputStream) {
                outputStream.write(jsonStr.getBytes("UTF-8"));
            }
            inputStream = connection.getInputStream();
            imageOutputStream = new ByteArrayOutputStream();
            byte[] cacheBytes = new byte[inputStream.available()];
            int n = 0;
            while (-1 != (n = inputStream.read(cacheBytes))) {
                imageOutputStream.write(cacheBytes, 0, n);
            }
            byte[] result = imageOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(outputStream, inputStream, imageOutputStream);
            if (null != connection) {
                connection.disconnect();
            }
        }
        return null;

    }

    /**
     * <p>
     * map 转换成JSONSTR
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param param
     * @throws
     * @author: chenbailin
     * @date: 2021/3/8 14:19
     * @return: {@link String}
     */
    public static String mapToJsonStr(Map<String, Object> param) {
        if (null != param && !param.isEmpty()) {
            JSONObject result = new JSONObject(true);
            Set<String> strings = param.keySet();
            for (String string : strings) {
                Object data = param.get(string);
                result.put(string, data);
            }
            return result.toJSONString();
        }
        return "";
    }

    /**
     * <p>
     * HTTPS请求中的自定义信任管理器
     * </p>
     * <version>
     * 1.0
     * </version>
     *
     * @param
     * @author: chenbailin
     * @date: 2021/3/8 14:19
     * @throws
     * @return: {@link null}
     */
    private static class MyX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static void close(Closeable... arg) {
        if (arg == null || arg.length == 0) {

        } else {
            for (Closeable closeable : arg) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
