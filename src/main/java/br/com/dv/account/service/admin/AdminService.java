package br.com.dv.account.service.admin;

import br.com.dv.account.dto.admin.AdminUserResponse;
import br.com.dv.account.dto.admin.RoleUpdateRequest;
import br.com.dv.account.dto.admin.UserDeletionResponse;

import java.util.List;

public interface AdminService {

    List<AdminUserResponse> getUsers();

    UserDeletionResponse deleteUser(String userEmail);

    AdminUserResponse updateUserRoles(RoleUpdateRequest roleUpdateRequest);

}
