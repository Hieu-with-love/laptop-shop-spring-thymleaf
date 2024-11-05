package devzeus.com.laptop_shop.services.interfaces;

import devzeus.com.laptop_shop.dtos.requests.RoleDTO;
import devzeus.com.laptop_shop.models.Role;

public interface IRoleService {
    void createRole(RoleDTO roleDTO);

    Role getRoleByName(String name);
}
