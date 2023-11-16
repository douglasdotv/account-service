package br.com.dv.account.validation;

import br.com.dv.account.dto.admin.RoleUpdateRequest;
import br.com.dv.account.entity.Role;
import br.com.dv.account.entity.User;
import br.com.dv.account.exception.custom.AdminRestrictionException;
import br.com.dv.account.exception.custom.LastRoleException;
import br.com.dv.account.exception.custom.RoleConflictException;
import br.com.dv.account.exception.custom.RoleNotAssignedException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleValidationService {

    private static final String REMOVE_OPERATION = "REMOVE";
    private static final String GRANT_OPERATION = "GRANT";
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";

    public void validateUserDeletion(User user) {
        if (isAdmin(user)) {
            throw new AdminRestrictionException();
        }
    }

    public void validateRoleUpdate(RoleUpdateRequest roleUpdate, User user) {
        if (roleUpdate.operation().equals(REMOVE_OPERATION)) {
            checkAdminRoleNotRemovable(user);
            checkRoleAssignedBeforeRemoval(roleUpdate, user);
            checkLastRoleBeforeRemoval(roleUpdate, user);
        } else if (roleUpdate.operation().equals(GRANT_OPERATION)) {
            checkRoleGrantConflict(roleUpdate, user);
        }
    }

    private void checkRoleAssignedBeforeRemoval(RoleUpdateRequest roleUpdate, User user) {
        String roleToBeRemoved = roleUpdate.roleName();
        Set<Role> userRoles = user.getRoles();
        if (userRoles.stream().noneMatch(role -> role.getName().equals(ROLE_PREFIX + roleToBeRemoved))) {
            throw new RoleNotAssignedException(roleToBeRemoved);
        }
    }

    private void checkLastRoleBeforeRemoval(RoleUpdateRequest roleUpdate, User user) {
        String roleToBeRemoved = roleUpdate.roleName();
        Set<Role> userRoles = user.getRoles();
        if (userRoles.size() == 1) {
            throw new LastRoleException(roleToBeRemoved);
        }
    }

    private void checkAdminRoleNotRemovable(User user) {
        if (isAdmin(user)) {
            throw new AdminRestrictionException();
        }
    }

    private void checkRoleGrantConflict(RoleUpdateRequest roleUpdate, User user) {
        boolean isUserAdmin = isAdmin(user);
        boolean isGrantingAdminRole = roleUpdate.roleName().equals(ROLE_ADMINISTRATOR.substring(ROLE_PREFIX.length()));
        if ((isUserAdmin && !isGrantingAdminRole) || (!isUserAdmin && isGrantingAdminRole)) {
            throw new RoleConflictException();
        }
    }

    private boolean isAdmin(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(ROLE_ADMINISTRATOR));
    }

}
