package cn.jianwooeshop.order.service.impl;

import cn.jianwoo.eshop.api.AddressService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Address;
import cn.jianwoo.eshop.manage.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;
    @Override
    public List<Address> getAddresslist() {
        return addressMapper.getAddresslist();
    }

    @Override
    public List<Address> getAddresslistByUid(Long uid) {
        return addressMapper.getAddresslistByUid(uid);
    }

    @Override
    public List<Address> getAddresslistByUidDefault(Long uid) {
        return addressMapper.getAddresslistByUidDefault(uid);
    }

    @Override
    public Address getaddressbyid(Long id) {
        return addressMapper.getaddressbyid(id);
    }

    @Transactional
    @Override
    public EShopResult insert(Address address) {
        if (address!=null){
            address.setCreated(new Timestamp(new Date().getTime()));
            address.setUpdated(new Timestamp(new Date().getTime()));
            addressMapper.insert(address);
            return  EShopResult.ok(address);
        }
        return EShopResult.error("not null");
    }

    @Override
    public EShopResult update(Address address) {
        if (address!=null&&address.getId()!=null){
            address.setUpdated(new Timestamp(new Date().getTime()));
            addressMapper.update(address);
            return  EShopResult.ok();
        }
        return EShopResult.error("not null");
    }

    @Override
    public EShopResult delete(Address address) {
        addressMapper.delete(address);
        return EShopResult.ok();
    }

    @Override
    public EShopResult updateDefault(Address address) {
        if (address!=null){
            addressMapper.updateDefault(address);
            return  EShopResult.ok();
        }
        return EShopResult.error("not null");
    }

    @Transactional
    @Override
    public EShopResult setDefault(Address address) {
        Address address1=addressMapper.getaddressbyid(address.getId());
        addressMapper.updateDefault(address1);
        address1.setIsDefault(1);
        addressMapper.update(address1);
        return EShopResult.ok();
    }
}
