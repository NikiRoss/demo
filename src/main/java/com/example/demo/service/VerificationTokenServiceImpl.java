package com.example.demo.service;

import com.example.demo.dao.TokenDao;
import com.example.demo.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class VerificationTokenServiceImpl {

    @Autowired
    private TokenDao tokenDao;

    public VerificationToken findTokenById(long id){
        return tokenDao.findById(id);
    }

    public Date calculateExpiry(int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificationToken findByToken(String token){
        return tokenDao.findByToken(token);
    }

}