package com.example.demo.repository;

import com.example.demo.security.Authorities;
import org.springframework.data.repository.CrudRepository;

public interface AuthoritiesDao extends CrudRepository<Authorities, Long> {

}
