package com.liyiruo.base.result;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 自定义响应结构
 * @author liyiruo
 */
@Data
public class LiyiruoResult {
    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public LiyiruoResult() {
    }
    public LiyiruoResult(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public LiyiruoResult(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public LiyiruoResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static LiyiruoResult ok() {
        return new LiyiruoResult(null);
    }
    public static LiyiruoResult ok(String message) {
        return new LiyiruoResult(message, null);
    }
    public static LiyiruoResult ok(Object data) {
        return new LiyiruoResult(data);
    }
    public static LiyiruoResult ok(String message, Object data) {
        return new LiyiruoResult(message, data);
    }

    public static LiyiruoResult build(Integer code, String message) {
        return new LiyiruoResult(code, message, null);
    }

    public static LiyiruoResult build(Integer code, String message, Object data) {
        return new LiyiruoResult(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 LiyiruoResult 对象
     * @param json
     * @return
     */
    public static LiyiruoResult format(String json) {
        try {
            return JSON.parseObject(json, LiyiruoResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
