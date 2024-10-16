package tech.theraven.customerhub.dto;

import java.util.Objects;

import jakarta.validation.constraints.Pattern;
import tech.theraven.customerhub.utility.RegexPatternConstants;

public class CustomerDTO {

    private long id;

    @Pattern(regexp = RegexPatternConstants.FULL_NAME_PATTERN,
             message = "\"Full name\" must be from 2 to 50 characters of letters including whitespaces")
    private String fullName;

    @Pattern(regexp = RegexPatternConstants.EMAIL_PATTERN,
             message = "Email must has 2..100 chars and should include exactly one @")
    private String email;

    @Pattern(regexp = RegexPatternConstants.PHONE_PATTERN,
             message = "Phone number must start with '+' and contain from 6 to 14 digits only")
    private String phone;

    public CustomerDTO() {}

    private CustomerDTO(Builder builder) {
        this.id = builder.id;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email,
                            fullName,
                            id,
                            phone);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomerDTO)) {
            return false;
        }
        CustomerDTO other = (CustomerDTO) obj;
        return Objects.equals(email,
                              other.email)
               && Objects.equals(fullName,
                                 other.fullName)
               && id == other.id
               && Objects.equals(phone,
                                 other.phone);
    }

    @Override
    public String toString() {
        return "CustomerDTO [id="
               + id
               + ", fullName="
               + fullName
               + ", email="
               + email
               + ", phone="
               + phone
               + "]";
    }

    public static class Builder {
        private long id;
        private String fullName;
        private String email;
        private String phone;

        private Builder() {}

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this);
        }
    }

}
