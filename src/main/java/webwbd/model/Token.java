package webwbd.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table (name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private int id;

    @Column(name = "token", nullable = false, length = 256)
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id", nullable = false, referencedColumnName = "request_id")
    private Request request;

    public Token() {}
}
