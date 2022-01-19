package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Role;
import com.cv.spring_workcv.repository.RoleRepository;
import com.cv.spring_workcv.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role getRoleById(int id) {
        try {
            return roleRepository.findRoleById(id);
        }catch (Exception e){
            log.error("Error at [getRoleById]", e);
        }
        return null;
    }
}
