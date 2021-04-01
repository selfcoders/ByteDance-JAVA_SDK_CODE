package entity;

import lombok.Data;

import java.util.Map;

/**
 * <p>
 *  获取二维码实体类
 * </p>
 *
 * @auther hehw
 * @data 2021/3/9 9:50
 */
@Data
public class CreateQRCode extends ByteDanceBaseEntity {
    private String access_token;
    private String appname;
    private String path;
    private Integer width;
    private Map<String, Object> line_color;
    private Map<String, Object> background;
    private boolean set_icon;
}
