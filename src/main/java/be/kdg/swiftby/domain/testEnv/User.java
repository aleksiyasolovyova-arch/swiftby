package be.kdg.swiftby.domain.testEnv;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull private String email;
    //encrypted
    @NonNull private String password;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String phoneNumber;

    public boolean isLoginAllowed(){
        if (this instanceof Employee employee) {
            return employee.isApproved();
        }
        return true;
    };

    public User( String email, String firstName,  String lastName,  String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String password, String firstName, String lastName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
