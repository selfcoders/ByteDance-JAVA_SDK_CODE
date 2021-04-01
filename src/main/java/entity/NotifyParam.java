package entity;

import lombok.Data;


/**
 * <p>
 * 订阅消息实体类
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/8 15:10
 */
@Data
public class NotifyParam extends ByteDanceBaseEntity {
    private String access_token;
    private String app_id;
    private String tpl_id;
    private String open_id;
    private String data;
    private String page;
}
