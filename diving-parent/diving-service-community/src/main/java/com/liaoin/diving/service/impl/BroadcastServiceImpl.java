package com.liaoin.diving.service.impl;

import com.liaoin.diving.dao.BroadcastRepository;
import com.liaoin.diving.entity.Broadcast;
import com.liaoin.diving.service.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName BroadcastServiceImpl
 * @Author huqingxi
 * @Date 2018/7/5 14:07
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class BroadcastServiceImpl implements BroadcastService {
    @Resource
    BroadcastRepository broadcastRepository;




    @Override
    public List<Broadcast> get(Integer size) {
        List<Broadcast> broadcasts = broadcastRepository.getBroadcast(size);
        for(Broadcast broadcast: broadcasts){
            if (!Objects.isNull(broadcast.getActivity())){

                continue;
            }
            if (Objects.isNull(broadcast.getTopic())){

            }
        }
        return broadcasts;
    }
}
