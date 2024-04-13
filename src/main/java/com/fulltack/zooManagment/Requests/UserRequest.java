package com.fulltack.zooManagment.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class UserRequest {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    private String password;
    private String role;
}