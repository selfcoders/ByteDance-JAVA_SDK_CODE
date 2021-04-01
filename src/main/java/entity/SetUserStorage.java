package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  设置用户缓存数据实体类
 * </p>
 *
 * @auther hehw
 * @data 2021/3/9 8:54
 */
@Data
public class SetUserStorage extends ByteDanceBaseEntity {
    private String access_token;
    private String openid;
    private String signature;
    private String sig_method;
    private String session_key;
    private List<KvItem> kv_list = new ArrayList<>();

    public class KvItem {
        private String key;
        private String value;

        @Override
        public String toString() {
            return "KvItem{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
