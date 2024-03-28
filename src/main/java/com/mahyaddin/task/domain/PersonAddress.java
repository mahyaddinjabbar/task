package com.mahyaddin.task.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahyaddin.task.domain.Person;
import com.mahyaddin.task.domain.enums.addressType;
import com.mahyaddin.task.domain.idClasses.PersonAddressKey;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "person_address")
@IdClass(PersonAddressKey.class)
@EntityListeners(AuditingEntityListener.class)
@Audited
public class PersonAddress {

    @Id
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "address_type", nullable = false)
    private addressType addressType;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "apartment")
    private String apartment;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    public PersonAddress(addressType addressType, Person person, String city, String street, String apartment) {
        this.addressType = addressType;
        this.person = person;
        this.city = city;
        this.street = street;
        this.apartment = apartment;
    }

    public PersonAddress() {

    }



    public addressType getAddressType() {
        return addressType;
    }

    public void setAddressType(addressType addressType) {
        this.addressType = addressType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "PersonAddress{" +
                "addressType=" + addressType +
                ", person=" + person +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }
}
