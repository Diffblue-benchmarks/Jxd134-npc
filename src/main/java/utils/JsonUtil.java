package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * @author jinxiaodong
 */
public class JsonUtil {

    private static ObjectMapper objectMapper;

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false);
        objectMapper.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        objectMapper.disable(SerializationFeature.CLOSE_CLOSEABLE);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
    }

    public static <T> byte[] serialize(T obj) {
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static <T> T deserialize(byte[] data, Class<T> cls) {
        T obj = null;
        try {
            obj = objectMapper.readValue(data, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> T jsonToObject(String json, Class<?> cls) {
        T obj = null;
        JavaType javaType = objectMapper.getTypeFactory().constructType(cls);
        try {
            obj = objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> T jsonToObjectList(String json, Class<?> collectionClass, Class<?>... elementsClass) {
        T obj = null;
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(
                collectionClass, elementsClass);
        try {
            obj = objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> T jsonToObjectHashMap(String json, Class<?> keyClass, Class<?> valueClass) {
        T obj = null;
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(
                HashMap.class, keyClass, valueClass);
        try {
            obj = objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static String objectToJson(Object object) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
