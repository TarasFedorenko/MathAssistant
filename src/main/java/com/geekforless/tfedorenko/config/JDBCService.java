package com.geekforless.tfedorenko.config;

import java.sql.Connection;
import java.sql.Statement;

public interface JDBCService {
    Connection getConnection();

    Statement getStatement();
}