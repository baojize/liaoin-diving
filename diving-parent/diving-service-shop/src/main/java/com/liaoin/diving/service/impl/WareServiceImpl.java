package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.entity.Size;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.WareOrderRepository;
import com.liaoin.diving.dao.WareRepository;
import com.liaoin.diving.entity.Type;
import com.liaoin.diving.entity.Ware;
import com.liaoin.diving.entity.WareOrder;
import com.liaoin.diving.mapper.WareMapper;
import com.liaoin.diving.service.WareService;
import com.liaoin.diving.utils.UpdateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WareServiceImpl implements WareService {
    @Resource
    private WareMapper wareMapper;
    @Resource
    private WareRepository wareRepository;
    @Resource
    private WareOrderRepository wareOrderRepository;


    @Override
    public void add(Ware ware) {
        wareRepository.save(ware);
    }

    @Override
    public void del(Integer id) {
        wareRepository.delete(id);
    }

    @Override
    public void upload(Ware ware) {
        Ware one = wareRepository.findOne(ware.getId());
        UpdateUtils.copyNonNullProperties(ware, one);
        wareRepository.save(one);
    }

    @Override
    public Ware findById(Integer id) {
        Ware ware = wareRepository.findOne(id);
        List<Type> typeList = wareMapper.findType(ware.getId());
        if (!Objects.isNull(typeList) || typeList.size() != 0){
            ware.setTypeList(typeList);
        }
        List<Size> sizeList = wareMapper.findSize(ware.getId());
        if (!Objects.isNull(sizeList) || sizeList.size() != 0){
            ware.setSizeList(sizeList);
        }
        Integer stock = wareMapper.findStock(ware.getId());
        if (Objects.isNull(stock)){
            ware.setStock(stock);
        }
        return ware;
    }

    @Override
    public List<Ware> findAll(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Ware> wareList = wareRepository.findAll();
        for (Ware ware : wareList){
            List<Type> typeList = wareMapper.findType(ware.getId());
            if (!Objects.isNull(typeList) || typeList.size() != 0){
                ware.setTypeList(typeList);
            }
            List<Size> sizeList = wareMapper.findSize(ware.getId());
            if (!Objects.isNull(sizeList) || sizeList.size() != 0){
                ware.setSizeList(sizeList);
            }
            Integer stock = wareMapper.findStock(ware.getId());
            if (Objects.isNull(stock)){
                ware.setStock(stock);
            }
        }
        return wareList;
    }

    @Override
    public List<Type> findType(Integer id) {
        return  wareMapper.findType(id);
    }

    @Override
    public List<Size> findSize(Integer id) {
        return  wareMapper.findSize(id);
    }

    @Override
    public Integer findStock(Integer id) {
        return wareMapper.findStock(id);
    }

    @Override
    public Integer findStock4TypeAndSize(Integer typeId, Integer sizeId) {
        return wareMapper.findStock4TypeAndSize(typeId, sizeId);
    }

    @Override
    public void save(WareOrder wareOrder) {
        wareOrderRepository.save(wareOrder);
    }
}
