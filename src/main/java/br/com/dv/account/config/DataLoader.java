package br.com.dv.account.config;

import br.com.dv.account.entity.Role;
import br.com.dv.account.enums.RoleType;
import br.com.dv.account.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        createRoleIfNotFound(RoleType.ADMINISTRATOR);
        createRoleIfNotFound(RoleType.USER);
        createRoleIfNotFound(RoleType.ACCOUNTANT);
    }

    private void createRoleIfNotFound(RoleType role) {
        if (roleRepository.findByName(role.getName()).isEmpty()) {
            roleRepository.save(new Role(role.getName()));
        }
    }

}
