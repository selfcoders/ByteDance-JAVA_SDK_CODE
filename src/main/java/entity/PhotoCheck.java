package entity;

import lombok.Data;

/**
 * <p>
 * 图片检测实体类
 * </p>
 *
 * @auther hehw
 * @data 2021/3/9 10:01
 */
@Data
public class PhotoCheck extends ByteDanceBaseEntity {
    private String app_id;
    private String access_token;
    private String image;
    private String image_data;
}
