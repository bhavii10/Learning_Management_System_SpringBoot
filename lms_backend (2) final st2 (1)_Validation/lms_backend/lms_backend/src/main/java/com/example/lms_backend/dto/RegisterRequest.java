//package com.example.lms_backend.dto;
//
//import com.example.lms_backend.model.Role;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//
//@Getter @Setter
//@NoArgsConstructor @AllArgsConstructor @Builder
//public class RegisterRequest {
//    @NotBlank
//    private String name;
//
//    @Email @NotBlank
//    private String email;
//
//    @NotBlank
//    private String password;
//
//    // optional; default USER
//    private Role role;
//}



//PassValid
package com.example.lms_backend.dto;

import com.example.lms_backend.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterRequest {
    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;

    // optional; default USER
    private Role role;
}
