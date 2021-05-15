package com.webflux.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("webflux_role")
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class AppRole {
    @Id
    private String id;

    private String name;

//    private List<Permission> permissions;

    public AppRole(){
        name = "READ";
    }
}