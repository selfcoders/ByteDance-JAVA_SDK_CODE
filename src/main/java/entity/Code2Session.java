package entity;

import lombok.Data;

/**
 * <p>
 * 登录请求实体类
 * </p>
 *
 * @auther hehw
 * @data 2021/3/8 16:16
 */
@Data
public class Code2Session extends ByteDanceBaseEntity {
    private String appid;
    private String secret;
    private String code;
    private String anonymous_code;

}
