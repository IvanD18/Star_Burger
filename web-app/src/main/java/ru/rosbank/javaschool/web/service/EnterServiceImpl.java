package ru.rosbank.javaschool.web.service;

import ru.rosbank.javaschool.web.repository.AdminsRepository;

import java.sql.SQLException;

public class EnterServiceImpl implements EnterService {
    private AdminsRepository repository;

    public EnterServiceImpl(AdminsRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean enter(String login,String password){
        return repository.check(login,password);
    }
}
