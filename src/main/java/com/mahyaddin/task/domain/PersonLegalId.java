package com.mahyaddin.task.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahyaddin.task.domain.enums.idType;
import com.mahyaddin.task.domain.idClasses.PersonLegalIdKey;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Entity(name = "person_legal_id")
@IdClass(PersonLegalIdKey.class)
@EntityListeners(AuditingEntityListener.class)
@Audited
public class PersonLegalId {

    @Id
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_type", nullable = false)
    private idType idType;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "id_number", nullable = false)
    private Integer idNumber;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "issued_by")
    private String issuedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;


    public PersonLegalId(idType idType, Person person, Integer idNumber, Date issueDate, String issuedBy) {
        this.idType = idType;
        this.person = person;
        this.idNumber = idNumber;
        this.issueDate = issueDate;
        this.issuedBy = issuedBy;
    }

    public PersonLegalId() {

    }

    public idType getIdType() {
        return idType;
    }

    public Person getPerson() {
        return person;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIdType(idType idType) {
        this.idType = idType;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    @Override
    public String toString() {
        return "PersonLegalId{" +
                "idType=" + idType +
                ", person=" + person +
                ", idNumber=" + idNumber +
                ", issueDate=" + issueDate +
                ", issuedBy='" + issuedBy + '\'' +
                '}';
    }

}
