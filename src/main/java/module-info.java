module src.com {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires java.naming;

    opens src.com.model to org.hibernate.orm.core;
    opens src.com to javafx.fxml;

    exports src.com;
}