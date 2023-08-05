package baykov.daniel.security.app.bootstrap;

import baykov.daniel.security.app.entity.Role;
import baykov.daniel.security.app.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findAll().size() == 0) {
            loadData();
        }
    }

    private void loadData() {
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
    }
}
