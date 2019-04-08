package cn.jianwoo.eshop.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class User implements Serializable {

    private Long id;

    private String username;

    private String password;
    private String rePassword;
    private String code;
    private String avatar;
    private  Integer sex;
    private  Integer status;

    private String phone;

    private String email;

    private Timestamp created;

    private Timestamp updated;

    private String nickName;

    private  String token;

    private  String exField1;

    private  String exField2;

    private  String exField3;

    private  Integer type;


}
