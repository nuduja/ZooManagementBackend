package com.fulltack.zooManagment.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AdminRequest {
    @NotNull
    private String name;
    @NotNull
    private String username;
    private String password;
    private String role;
}