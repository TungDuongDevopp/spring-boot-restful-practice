package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService service;

    @PostMapping("/roles")
    public ResponseEntity<ApiResponse<Role>> createRole(@Valid @RequestBody Role inputRole){
        service.create(inputRole);
        return  ApiResponse.success(inputRole);
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles(){
        List<Role> roles = service.getAll();
        return  ApiResponse.success(roles);
    }
    @GetMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable Long id){
        Role role = service.getById(id);
        return  ApiResponse.success(role);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Role>> updateRole(@PathVariable Long id,@Valid @RequestBody Role updateRole){
        Role currentRole = service.updateById(id,updateRole);
        return  ApiResponse.success(currentRole);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id){
        service.deleteById(id);
        return  ApiResponse.success(null,"Deleted succsessful!");
    }







}
