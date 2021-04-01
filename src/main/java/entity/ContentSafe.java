package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 文本安全检测实体类
 * </p>
 *
 * @auther hehw
 * @data 2021/3/9 9:58
 */
@Data
public class ContentSafe extends ByteDanceBaseEntity {
    private List<Content> tasks = new ArrayList<>();

    public class Content {
        private String content;

        public Content(String content) {
            this.content = content;
        }

        public Content() {
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "content='" + content + '\'' +
                    '}';
        }
    }
}
