package com.yeshtery.pps.data.entity.appuser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @SequenceGenerator(
            name = "user_confirmation_token_sequence",
            sequenceName = "user_confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_confirmation_token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;

    public ConfirmationToken( AppUser appUser) {
        this.token = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(15);
        this.appUser = appUser;
    }
}
