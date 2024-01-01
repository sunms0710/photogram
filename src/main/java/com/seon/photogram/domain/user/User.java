package com.seon.photogram.domain.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seon.photogram.domain.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String website;
    private String bio;

    @Column(nullable = false)
    private String email;

    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    // mappedBy: 연관관계의 주인이 아님, 데이터 칼럼 안만듬
    // User를 Select할 때 해당 id로 등록된 image를 가져옴
    // LAZY: User를 Select할 때 해당 id로 등록된 image를 가져오지않는대신 get함수를 호출할 때 가져옴
    // EAGER: User를 Select할 때 해당 id로 등록된 image를 전부 Join해서 가져옴
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) //무한참조 방지
    private List<Image> images; //양방향 매핑

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
