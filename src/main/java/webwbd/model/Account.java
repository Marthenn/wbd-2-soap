package webwbd.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@Getter
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Account", propOrder = {
        "id",
        "username",
        "password",
        "email",
        "joinedDate",
        "expiredDate",
        "isAdmin"
})
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, length = 256, unique = true)
    @Setter
    private String username;

    @Column(name = "password", nullable = false, length = 256, unique = true)
    @Setter
    private String password;

    @Column(name = "email", nullable = false, length = 256)
    @Setter
    private String email;

    @Column(name = "joined_date", nullable = false)
    @Setter
    private Date joinedDate;

    @Column(name = "expired_date")
    @Setter
    private Date expiredDate;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;


    public Account(int id, boolean isAdmin){this.id=id;this.isAdmin = isAdmin;}
    public Account(){this.isAdmin = false;}
}
