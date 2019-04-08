package cn.jianwoo.eshop.manage.mapper;


import cn.jianwoo.eshop.manage.entity.Address;

import java.util.List;

public interface AddressMapper {

    List<Address> getAddresslist();
    List<Address> getAddresslistByUid(Long uid);
    List<Address> getAddresslistByUidDefault(Long uid);
    Address getaddressbyid(Long id);
    int insert(Address address);
    int update(Address address);
    int delete(Address address);
    int updateDefault(Address address);
}
