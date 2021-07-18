package com.kjb46.webapps2020.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 */
@NamedQuery(name="findAllUsers", query="SELECT u FROM SystemUser u ")
@Entity
public class SystemUser implements Serializable {
    
    //each user belongs to one user group (user or admin)
    /* @OneToOne
    private SystemUserGroup group;    
    */
    
    //each user has many debit transaction records and each record belongs to one user
    @OneToMany(cascade = CascadeType.ALL, targetEntity = DebitTransactionRecord.class)
    private List<DebitTransactionRecord> debits;

    //each user has many credit transaction records
    @OneToMany(cascade = CascadeType.ALL, targetEntity = CreditTransactionRecord.class)
    private List<CreditTransactionRecord> credits;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // USERGROUP_ID
    
    //
    //@JoinColumn(name = "GROUP_ID", referencedColumnName = "id")
    // @JoinColumn(name = "USERGROUP_ID")
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", nullable=false, updatable=false, insertable=false)
    private List<SystemUserGroup> group;

    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "ID", nullable=false, updatable=false, insertable=false)
    private TransactionEntity account;
    
    // here on could use Bean Validation annotations to enforce specific rules - this could be alternatively implemented when validating the form in the web tier
    // for now we check only for Null values
    @NotNull
    String username;

    // here on could use Bean Validation annotations to enforce specific rules - this could be alternatively implemented when validating the form in the web tier
    // for now we check only for Null values
    @NotNull
    String userpassword;

    @NotNull
    String name;

    @NotNull
    String surname;

    public SystemUser() {
    }

    public SystemUser(String username, String userpassword, String name, String surname) {
        this.username = username;
        this.userpassword = userpassword;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.userpassword);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.surname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemUser other = (SystemUser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.userpassword, other.userpassword)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.surname, other.surname);
    }

    public List<DebitTransactionRecord> getDebits() {
        return debits;
    }

    public void setDebits(List<DebitTransactionRecord> debits) {
        this.debits = debits;
    }

    public List<CreditTransactionRecord> getCredits() {
        return credits;
    }

    public void setCredits(List<CreditTransactionRecord> credits) {
        this.credits = credits;
    }
    
    public List<SystemUserGroup> getSystemUserGroup() {
        return group;
    }

    public void setSystemUserGroup(List<SystemUserGroup> g) {
        this.group = g;
    }

    public TransactionEntity getAccount() {
        return account;
    }

    public void setAccount(TransactionEntity account) {
        this.account = account;
    }

    public List<SystemUserGroup> getGroup() {
        return group;
    }

    public void setGroup(List<SystemUserGroup> group) {
        this.group = group;
    }

}
