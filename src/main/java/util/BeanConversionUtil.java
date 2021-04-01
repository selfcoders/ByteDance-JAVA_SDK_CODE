package util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * Bean转换器
 * </p>
 *
 * @auther chenbailin
 * @data 2021/3/8 14:29
 */
@Slf4j
public class BeanConversionUtil {

    public static Map<String, Object> beanToMap(Object obj) {
        // 若是对顺序有要求则更换成treeMap
        HashMap<String, Object> result = new HashMap<String, Object>();
        ArrayList files;
        Class<?> clazz = obj.getClass();
        for (files = new ArrayList(); clazz != null; clazz = clazz.getSuperclass()) {
            files.addAll(new ArrayList(Arrays.asList(clazz.getDeclaredFields())));
        }
        Iterator iterator = files.iterator();
        while (iterator.hasNext()) {
            Field next = (Field) iterator.next();
            next.setAccessible(true);
            try {
                if (next.get(obj) != null) {
                    result.put(next.getName(), next.get(obj));
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("error change Bean2Map:{},field:{}", e.getMessage(), next.getName());
            }
        }
        return result;
    }


}
