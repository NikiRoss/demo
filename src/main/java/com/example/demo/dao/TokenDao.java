package com.example.demo.dao;

import com.example.demo.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenDao extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
}
