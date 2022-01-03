package com.reactive.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer balance;

}
