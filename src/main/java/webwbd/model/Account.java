package webwbd.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, length = 256)
    @Setter
    private String username;

    @Column(name = "password", nullable = false, length = 256)
    @Setter
    private String password;

    @Column(name = "email", nullable = false, length = 256)
    @Setter
    private String email;

    @Column(name = "joined_date", nullable = false)
    @Setter
    private Date joinedDate;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    public Account(){}
}