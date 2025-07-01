package com.niteshkr.online_book_store.dto;


import com.niteshkr.online_book_store.helper.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

        private String username;
        private UserRole role;
        private  String email;
        private  long mobile;
        private  String address;
        private String password;


}
