package br.com.dv.account.config;

import br.com.dv.account.entity.Role;
import br.com.dv.account.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ACCOUNTANT = "ROLE_ACCOUNTANT";

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        createRoleIfNotFound(ROLE_ADMINISTRATOR);
        createRoleIfNotFound(ROLE_USER);
        createRoleIfNotFound(ROLE_ACCOUNTANT);
    }

    private void createRoleIfNotFound(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            roleRepository.save(new Role(roleName));
        }
    }

}
