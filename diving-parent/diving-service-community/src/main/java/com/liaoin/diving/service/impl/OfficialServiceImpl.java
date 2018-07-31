package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.OfficialRepository;
import com.liaoin.diving.entity.Official;
import com.liaoin.diving.mapper.OfficialMapper;
import com.liaoin.diving.service.OfficialService;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OfficialServiceImpl implements OfficialService{
    @Resource
    private OfficialRepository officialRepository;
    @Resource
    private OfficialMapper officialMapper;

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<Official> findAll( ) {
        return officialMapper.findAll();
    }

    @Override
    public Integer insert(Official official) {
        official.setCreateTime(new Date());
        official.setLikeCount(0);
        official.setDisCount(0);
        official.setReadCount(0);
        return officialMapper.insert(official);
//      officialRepository.save(official);
    }

    @Override
    public Integer insertImgRelation(Map<String, Object> map) {
        officialMapper.insertRelation(map);
        return null;
    }

    @Override
    public Integer insertLabelRelation(Map<String, Object> map) {
        return null;
    }

    @Override
    public Integer del(Integer id) {
        return officialMapper.del(id);
    }
}
