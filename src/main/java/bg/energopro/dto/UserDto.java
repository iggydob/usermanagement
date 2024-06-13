package bg.energopro.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    public Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String bio;
}
