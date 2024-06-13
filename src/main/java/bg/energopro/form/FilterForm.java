package bg.energopro.form;

import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
public class FilterForm {
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> email;

    public FilterForm(String firstName, String lastName, String email) {
        this.firstName = Optional.ofNullable(firstName);
        this.lastName = Optional.ofNullable(lastName);
        this.email = Optional.ofNullable(email);
    }

    public FilterForm() {
        this(null, null, null);
    }
}


