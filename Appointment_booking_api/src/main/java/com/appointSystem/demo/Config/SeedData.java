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
        Account account03= new Account();
        Account account04= new Account();

        account01.setName("patient1");
        account01.setEmail("user@user.com");
        account01.setPassword("pass987");
        account01.setRole("PATIENT");
        account01.setAuthorities(Authority.USER.toString());
        accoutService.save(account01);

        account03.setName("patient2");
        account03.setEmail("user2@user.com");
        account03.setPassword("pass987");
        account03.setRole("PATIENT");
        account03.setAuthorities(Authority.USER.toString());
        accoutService.save(account03);

        account02.setName("doctor1");
        account02.setEmail("admin@admin.com");
        account02.setPassword("pass987");
        account02.setRole("DOCTOR");
        account02.setAuthorities(Authority.ADMIN.toString()+" "+Authority.USER.toString());
        accoutService.save(account02);

        account04.setName("doctor2");
        account04.setEmail("admin2@admin.com");
        account04.setPassword("pass987");
        account04.setRole("DOCTOR");
        account04.setAuthorities(Authority.ADMIN.toString()+" "+Authority.USER.toString());
        accoutService.save(account04);


    }

}
