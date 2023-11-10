package webwbd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Logging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter
    private int id;

    @Lob
    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @Column(name = "ip_address", nullable = false)
    @Getter
    @Setter
    private String ipAddress;

    @Column(name = "endpoint", nullable = false)
    @Getter
    @Setter
    private String endpoint;

    @Column(name = "request_time", nullable = false)
    @Getter
    @Setter
    private Date requestTime;

    public Logging(){}
}
