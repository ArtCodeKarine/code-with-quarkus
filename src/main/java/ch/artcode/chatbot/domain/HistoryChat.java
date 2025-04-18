package ch.artcode.chatbot.domain;

import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
public class HistoryChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = false, columnDefinition = "VARCHAR(4096)")
    private String query;

    @Column(nullable = false, unique = false, columnDefinition = "VARCHAR(4096)")
    private String answer;

    @Column(nullable = false, unique = false)
    private long timestamp;

    @Column(nullable = false, unique = false)
    private String memoryId;

    public Validation validation(Validation validation) {
        return validation;
    }
}
