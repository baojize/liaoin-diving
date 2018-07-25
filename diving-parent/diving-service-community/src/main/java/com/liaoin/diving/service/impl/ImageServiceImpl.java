package com.liaoin.diving.service.impl;

import com.liaoin.diving.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImageServiceImpl  implements ImageService{
}
