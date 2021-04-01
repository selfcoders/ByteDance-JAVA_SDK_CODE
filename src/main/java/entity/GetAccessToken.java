package entity;

import lombok.Data;

/**
 * <p>
 * 接口调用TOKEN获取实体类
 * </p>
 *
 * @auther hehw
 * @data 2021/3/8 16:13
 */
@Data
public class GetAccessToken extends ByteDanceBaseEntity {
    private String appid;
    private String secret;
    private String grant_type;

}
