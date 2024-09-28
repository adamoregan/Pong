
module PONG {
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    exports PONG.model;
    exports PONG.controller;
    exports PONG.view;
    exports PONG.dao;
    exports PONG.io;
}