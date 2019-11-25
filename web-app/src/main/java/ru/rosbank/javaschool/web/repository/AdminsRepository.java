package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.util.PreparedStatementSetter;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface AdminsRepository {
    boolean check(String login, String password) ;
    void save(String login,String password);
}
