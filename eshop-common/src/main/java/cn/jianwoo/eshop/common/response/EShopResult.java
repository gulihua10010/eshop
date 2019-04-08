package cn.jianwoo.eshop.common.response;

import cn.jianwoo.eshop.common.enums.StatusCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.ObjectHelper;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义响应结构
 */
public class EShopResult  implements Serializable{

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static EShopResult build(Integer status, String msg, Object data) {
        return new EShopResult(status, msg, data);
    }

    public static EShopResult ok(Object data) {
        return new EShopResult(data);
    }
    public static EShopResult ok(String msg,Object data) {
        return new EShopResult(msg,data);
    }
    public static EShopResult ok() {
        return new EShopResult(StatusCode.Success);
    }
    public static EShopResult error(String msg) {
        return new EShopResult(-1,msg,null);
    }
    public static EShopResult error() {
        return new EShopResult(StatusCode.Fail);
    }
    public static EShopResult invalidParams() {
        return new EShopResult(StatusCode.InvalidParams);
    }
    public EShopResult() {

    }

    public static EShopResult build(Integer status, String msg) {
        return new EShopResult(status, msg, null);
    }
    public EShopResult(StatusCode statusCode) {
        this.status = statusCode.getStatus();
        this.msg = statusCode.getMsg();
    }
    public EShopResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    public EShopResult( Object data) {
        this.status = 200;
        this.msg = "ok";
        this.data = data;
    }
    public EShopResult(String msg, Object data) {
        this.status = 200;
        this.msg = msg;
        this.data = data;
    }
    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为 Result对象
     * 
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static EShopResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, EShopResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static EShopResult format(String json) {
        try {
            return MAPPER.readValue(json, EShopResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static EShopResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "EShopResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
