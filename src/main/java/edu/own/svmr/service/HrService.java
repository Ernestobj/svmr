package edu.own.svmr.service;

import edu.own.svmr.mapper.HrMapper;
import edu.own.svmr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author:江斌
 * @Description:
 * @Date: Created in 2021/8/19 19:35
 * @ModifiedBy:
 */
@Service
public class HrService implements UserDetailsService {

    @Autowired
    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr==null){
            throw new UsernameNotFoundException("账户不存在！");
        }
        return hr;
    }
}
