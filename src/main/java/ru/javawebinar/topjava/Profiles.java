package ru.javawebinar.topjava;

public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb",
            SQLITE_DB = "sqlitedb";

public static String getActiveDbProfile() {
    try {
        Class.forName("org.postgresql.Driver");
        return POSTGRES_DB;
    } catch (ClassNotFoundException ex) {
        try {
            Class.forName("org.sqlite.JDBC");
            return Profiles.SQLITE_DB;
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                return Profiles.HSQL_DB;
            } catch (ClassNotFoundException e1) {
                throw new IllegalStateException("Could not find DB driver");
            }
        }
    }
}
}
