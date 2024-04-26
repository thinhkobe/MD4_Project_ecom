package ra.md4_project.service.roleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.md4_project.model.entity.Role;
import ra.md4_project.repository.IRoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private IRoleRepository repository;

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }
}
