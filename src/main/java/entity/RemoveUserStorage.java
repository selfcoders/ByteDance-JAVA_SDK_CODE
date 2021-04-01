package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 删除数据缓存实体类
 * </p>
 *
 * @auther hehw
 * @data 2021/3/9 9:43
 */

@Data
public class RemoveUserStorage extends ByteDanceBaseEntity {
    private String access_token;
    private String openid;
    private String signature;
    private String sig_method;
    private String session_key;
    private List<String> key = new ArrayList<>();
}
