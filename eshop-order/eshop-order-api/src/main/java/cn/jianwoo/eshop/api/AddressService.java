package cn.jianwoo.eshop.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAddresslist();
    List<Address> getAddresslistByUid(Long uid);
    List<Address> getAddresslistByUidDefault(Long uid);
    Address getaddressbyid(Long id);
    EShopResult insert(Address address);
    EShopResult update(Address address);
    EShopResult delete(Address address);
    EShopResult updateDefault(Address address);
    EShopResult setDefault(Address address);
}
