package ru.dzmakats.springbootmvcexample.service;



import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dzmakats.springbootmvcexample.entity.User;
import ru.dzmakats.springbootmvcexample.repository.Repo;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final Repo repo;

    public Optional<User> get(Long id) {
        return repo.findById(id);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    @Transactional
    public User save(User user) {
        return repo.saveAndFlush(user);
    }

    @Transactional
    public void delete(User user) {
        repo.delete(user);
    }

}
