package webwbd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Entity
@Table (name = "logging")
public class Logging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Lob
    @Column(name = "description")
    @Setter
    private String description;

    @Column(name = "ip_address", nullable = false, length = 16)
    @Setter
    private String ipAddress;

    @Column(name = "endpoint", nullable = false)
    @Setter
    private String endpoint;

    @Column(name = "request_time", nullable = false)
    @Setter
    private Date requestTime;

    public Logging(){}
}
