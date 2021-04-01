package enums;

/**
 * <p>
 * APP枚举类
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/10 17:46
 */
public enum ByteDanceAppEnum {
    toutiao("今日头条", "toutiao"),
    douyin("抖音", "douyin"),
    pipixia("皮皮虾", "pipixia"),
    huoshan("火山小视频", "huoshan");
    private String appName;
    private String code;

    ByteDanceAppEnum(String appName, String code) {
        this.appName = appName;
        this.code = code;
    }

    public static String getCode(String appName) {
        ByteDanceAppEnum[] values = ByteDanceAppEnum.values();
        String code = "";
        for (ByteDanceAppEnum value : values) {
            if (value.appName.equals(appName)) {
                code = value.code;
            }
        }
        return code;
    }

    public static String getAppName(String code) {
        ByteDanceAppEnum[] values = ByteDanceAppEnum.values();
        String name = "";
        for (ByteDanceAppEnum value : values) {
            if (value.code.equals(code)) {
                name = value.appName;
            }
        }
        return name;
    }
}
