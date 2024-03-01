package ru.dzmakats.springbootmvcexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dzmakats.springbootmvcexample.entity.User;

public interface Repo extends JpaRepository<User, Long> {


}
