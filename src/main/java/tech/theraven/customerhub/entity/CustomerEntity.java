package tech.theraven.customerhub.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers",
       schema = "customerhub")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private long id;

    @Column(name = "created",
            nullable = false)
    private long created;

    @Column(name = "updated",
            nullable = false)
    private long updated;

    @Column(name = "full_name",
            nullable = false,
            length = 50)
    private String fullName;

    @Column(name = "email",
            nullable = false,
            length = 100,
            unique = true)
    private String email;

    @Column(name = "phone",
            nullable = true,
            length = 14)
    private String phone;

    @Column(name = "is_active",
            nullable = false)
    private boolean isActive;

    private CustomerEntity(Builder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.isActive = builder.isActive;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CustomerEntity() {

    }

    public long getId() {
        return id;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
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

    public boolean isActive() {
        return isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(created,
                            email,
                            fullName,
                            id,
                            isActive,
                            phone,
                            updated);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomerEntity)) {
            return false;
        }
        CustomerEntity other = (CustomerEntity) obj;
        return created == other.created
               && Objects.equals(email,
                                 other.email)
               && Objects.equals(fullName,
                                 other.fullName)
               && id == other.id
               && isActive == other.isActive
               && Objects.equals(phone,
                                 other.phone)
               && updated == other.updated;
    }

    @Override
    public String toString() {
        return "Customer [id="
               + id
               + ", created="
               + created
               + ", updated="
               + updated
               + ", fullName="
               + fullName
               + ", email="
               + email
               + ", phone="
               + phone
               + ", isActive="
               + isActive
               + "]";
    }

    public static class Builder {
        private long id;
        private long created;
        private long updated;
        private String fullName;
        private String email;
        private String phone;
        private boolean isActive;

        private Builder() {
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withCreated(long created) {
            this.created = created;
            return this;
        }

        public Builder withUpdated(long updated) {
            this.updated = updated;
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

        public Builder withIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public CustomerEntity build() {
            return new CustomerEntity(this);
        }
    }
}
