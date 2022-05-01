package ru.otus.jdbc.mapper;

public class SQLQuery {
    static final String SELECT_ALL = "SELECT * FROM %s";
    static final String SELECT_BY_ID = "SELECT * FROM %s where %s = ?"; //? - usually implies a prepared statement, where the parameters are filled in later
    static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
    static final String UPDATE = "UPDATE %s set %s WHERE %s = ?";

}
