package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.util.PreparedStatementSetter;
import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.AdminsModel;

import javax.sql.DataSource;
import java.sql.*;

public class AdminsRepositoryJdbcImpl implements AdminsRepository {
    private final DataSource ds;
    private final SQLTemplate template;
    private final RowMapper<AdminsModel> mapper = rs -> new AdminsModel(
            rs.getString("login"),
            rs.getString("password")

    );

    public AdminsRepositoryJdbcImpl(DataSource ds, SQLTemplate template) {
        this.ds = ds;
        this.template = template;
        try {
            template.update(ds, "CREATE TABLE IF NOT EXISTS admins (\n" +
                    "login text PRIMARY KEY not null,\n" +
                    "password TEXT NOT NULL" +
                    ");");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public boolean check(String login, String password){
        try {
            return template.queryForObject(ds, "SELECT * FROM admins WHERE login = ? and password=?;", stmt -> {
                stmt.setString(1, login);
                stmt.setString(2,password);
                return stmt;
            }, mapper).isPresent();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

    }

    @Override
    public void save(String login, String password) {
        try {

            template.update(ds, "INSERT INTO admins(login, password) VALUES ('" + login + "','" + password + "');");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
}

