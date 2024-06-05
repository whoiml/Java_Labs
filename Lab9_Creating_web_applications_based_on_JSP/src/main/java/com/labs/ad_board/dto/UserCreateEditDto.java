package com.labs.ad_board.dto;

import com.labs.ad_board.db.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateEditDto {

    String username;
    String email;
    String name;
    String password;
    String rePassword;
    Role role;
}
