package br.com.dv.account.service.admin;

import br.com.dv.account.dto.admin.*;

import java.util.List;

public interface AdminService {

    List<AdminUserResponse> getUsers();

    UserDeletionResponse deleteUser(String userEmail);

    AdminUserResponse updateUserRoles(RoleUpdateRequest roleUpdateRequest);

    AccessUpdateResponse updateUserAccess(AccessUpdateRequest accessUpdateRequest);

}
