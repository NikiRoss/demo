package com.example.demo.dao;

import com.example.demo.security.Authorities;
import org.springframework.data.repository.CrudRepository;

public interface AuthoritiesRepo extends CrudRepository<Authorities, Long> {



}
