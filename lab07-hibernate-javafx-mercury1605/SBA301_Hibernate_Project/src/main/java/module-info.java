module sba301.com {
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jbcrypt;
    opens sba301.fu.pojo to org.hibernate.orm.core, javafx.base;
    exports sba301.fu.pojo;
    exports sba301.fu.service;
}