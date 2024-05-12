package com.nodian.entity.user;

import com.nodian.entity.account.Account;
import com.nodian.entity.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_active = false")
public class User extends BaseEntity {
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String lastName;

    @JoinColumn(name = "account_id")
    private Account account;

    public User(String avatarUrl, String firstName, String lastName) {
        this.avatarUrl = avatarUrl;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
