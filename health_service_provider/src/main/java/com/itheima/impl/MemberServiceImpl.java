package com.itheima.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName: com.itheima.impl
 * @ClassName: MemberServiceImpl
 * @Author: QingFeng
 * @Date: 2019-12-11 9:37
 * @Description: //TODO
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Integer> findcountBydata(List<String> list) {
        List<Integer> list1 = new ArrayList<>();
        for (String s : list) {
            //得到每一个月份数
            s = s+".31";
           int count =  memberMapper.findcountBydata(s);
           list1.add(count);
        }
        return list1;
    }
}
