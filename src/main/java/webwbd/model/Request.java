package webwbd.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table (name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    private int id;

    @Column(name="username", nullable = false, length = 256, unique = true)
    @Setter
    private String username;

    @Column(name = "email", nullable = false, length = 256, unique = true)
    @Setter
    private String email;

    @Column(name = "date", nullable = false)
    @Setter
    private Date date;

    @Column(name = "proof_directory", nullable = false)
    @Setter
    private String proofDirectory;

    @Column(name = "status", nullable = false)
    @Setter
    private String status; // Pending, Approved, Declined

    @Column(name = "description", nullable = true)
    @Setter
    private String description;

    public Request(){}
}
