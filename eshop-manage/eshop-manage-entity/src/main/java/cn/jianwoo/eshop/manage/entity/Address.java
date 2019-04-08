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
public class Address implements Serializable {
    private  Long id;
    private String receiverName;
    private  Long uid;
    private  Integer isDefault;
    private String receiverPhone;

    private String receiverMobile;
// receiverProvinces+receiverCity+receiverDistrict
    private String receiverProvinces;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private Timestamp created;

    private Timestamp updated;

}
