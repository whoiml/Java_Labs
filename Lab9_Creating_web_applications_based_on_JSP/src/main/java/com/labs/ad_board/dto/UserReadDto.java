package com.labs.ad_board.dto;

import com.labs.ad_board.db.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserReadDto {

    String username;
    String email;
    String name;
    Role role;
}
