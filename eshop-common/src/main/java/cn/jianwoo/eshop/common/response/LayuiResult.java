package cn.jianwoo.eshop.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayuiResult implements Serializable {
    private  Integer code;
    private   String msg;
    private Object data;
    private  Long count;
    public static LayuiResult  ok(Long count, Object data){
       return  new LayuiResult(0,"ok",data,count);

    }
    public static LayuiResult  error(){
        return  new LayuiResult(-1,"fail",null,0L);

    }
}
