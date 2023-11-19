package br.com.dv.account.controller;

import br.com.dv.account.dto.admin.*;
import br.com.dv.account.service.admin.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/user/")
    public ResponseEntity<List<AdminUserResponse>> getUsers() {
        List<AdminUserResponse> response = adminService.getUsers();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{userEmail}")
    public ResponseEntity<UserDeletionResponse> deleteUser(@PathVariable String userEmail) {
        UserDeletionResponse response = adminService.deleteUser(userEmail);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/role")
    public ResponseEntity<AdminUserResponse> updateUserRole(@Valid @RequestBody RoleUpdateRequest request) {
        AdminUserResponse response = adminService.updateUserRoles(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/access")
    public ResponseEntity<AccessUpdateResponse> updateUserAccess(@Valid @RequestBody AccessUpdateRequest request) {
        AccessUpdateResponse response = adminService.updateUserAccess(request);
        return ResponseEntity.ok(response);
    }

}
