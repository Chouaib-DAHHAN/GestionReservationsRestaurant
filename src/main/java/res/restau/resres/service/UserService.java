package res.restau.resres.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import res.restau.resres.model.Role;
import res.restau.resres.model.User;
import res.restau.resres.repository.RoleRepository;
import res.restau.resres.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllClients() {
        Optional<Role> clientRoleOpt = roleRepository.findByName("CLIENT");
        if (clientRoleOpt.isPresent()) {
            Role clientRole = clientRoleOpt.get();
            return userRepository.findByRolesContaining(clientRole);
        } else {
            throw new RuntimeException("CLIENT role not found");
        }
    }

    public void saveClient(User client) {
        Optional<Role> clientRoleOpt = roleRepository.findByName("CLIENT");
        if (clientRoleOpt.isPresent()) {
            Role clientRole = clientRoleOpt.get();
            client.getRoles().clear();
            client.getRoles().add(clientRole);
            userRepository.save(client);
        } else {
            throw new RuntimeException("CLIENT role not found");
        }
    }

    public void deleteClient(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getClientById(Long id) {
        return userRepository.findById(id);
    }
}