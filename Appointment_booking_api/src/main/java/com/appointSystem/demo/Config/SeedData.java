package com.appointSystem.demo.Config;

import com.appointSystem.demo.model.Account;
import com.appointSystem.demo.service.AccountService;
import com.appointSystem.demo.util.constants.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {


    @Autowired
    private AccountService accoutService;
    @Override
    public void run(String... args) throws Exception {
        Account account01= new Account();
        Account account02= new Account();
        account01.setName("patient1");
        account01.setEmail("user@user.com");
        account01.setPassword("pass987");
        account01.setRole("PATIENT");
        account01.setAuthorities(Authority.USER.toString());
        accoutService.save(account01);

        account02.setName("doctor1");
        account02.setEmail("admin@admin.com");
        account02.setPassword("pass987");
        account02.setRole("DOCTOR");
        account02.setAuthorities(Authority.ADMIN.toString()+" "+Authority.USER.toString());
        accoutService.save(account02);


    }

}
