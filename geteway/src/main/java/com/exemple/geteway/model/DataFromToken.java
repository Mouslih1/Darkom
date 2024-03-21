package com.exemple.geteway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataFromToken {

    private Long id;
    private String username;
    private String email;
    private Long agenceId;
}
