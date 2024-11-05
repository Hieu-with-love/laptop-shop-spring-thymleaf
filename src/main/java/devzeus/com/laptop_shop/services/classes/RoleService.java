package devzeus.com.laptop_shop.services.classes;

import devzeus.com.laptop_shop.dtos.requests.RoleDTO;
import devzeus.com.laptop_shop.models.Role;
import devzeus.com.laptop_shop.repositories.RoleRepository;
import devzeus.com.laptop_shop.services.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public void createRole(RoleDTO roleDTO) {
        Role role = Role.builder()
                .name(roleDTO.getName())
                .build();
        roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
