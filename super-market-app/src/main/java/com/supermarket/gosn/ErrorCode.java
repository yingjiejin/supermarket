package com.supermarket.gosn;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public enum ErrorCode implements GsonEnum<ErrorCode> {
    SUCCESS(0, "成功"),
    /**
     * json格式不对
     */
    ERROR_INPUT_JSON_FORMAT(1, "入参json格式不对"),
    /**
     * 无效请求
     */
    INVALID_REQUEST_PACKAGE(2, "无效的包名"),
    /**
     *
     */
    INVALID_REQUEST_METHOD(3, "无效的方法名"),
    /**
     *
     */
    INVALID_REQUEST_PARAMETER_COUNT(4, "无效的参数数量"),
    /**
     *
     */
    EXCEPTION_OCCURED(5, "发生了异常"),
    /**
     *
     */
    BIZ_ERROR(6, "业务错误");
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.addProperty("code", code);
        obj.addProperty("msg", msg);
        obj.addProperty("name", name());
        return obj;
    }

    @Override
    public ErrorCode deserialize(String jsonEnum) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(jsonEnum, Map.class);
        String name = map.get("name") == null ? null : map.get("name").toString();
        for (ErrorCode code : values()) {
            if (StringUtils.endsWithIgnoreCase(code.name(), name)) {
                return code;
            }
        }
        return SUCCESS;
    }
}