package com.duong.springdemoresful.service;

import com.duong.springdemoresful.helper.exception.DuplicateResourceException;
import com.duong.springdemoresful.helper.exception.ResourceNotFoundException;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public List<Role> getAll(){
        return repository.findAll();
    }

    public Role create(Role role){
        if(repository.existsByName(role.getName())){
            throw new DuplicateResourceException("Invalid role name");
        }
        return repository.save(role);
    }
    public Role getById(Long id){
        return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Role not found"));
    }

    @Transactional
    public Role updateById(Long id, Role updateRole){
        Role currentRole = getById(id);
        currentRole.setName(updateRole.getName());
        currentRole.setDescription(updateRole.getDescription());
        return currentRole;
    }

    public void  deleteById(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Role not found");
        }
        repository.deleteById(id);
    }

}
