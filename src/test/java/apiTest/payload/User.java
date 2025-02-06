package apiTest.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    int userStatus=0;

}
