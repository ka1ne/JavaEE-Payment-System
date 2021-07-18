package com.kjb46.webapps2020.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 *
 * @author 
 */
@Entity
public class SystemUserGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String groupname;
    
    //@JoinColumn(name = "GROUP_ID", referencedColumnName = "id")
    //@OneToMany(cascade=ALL, mappedBy="id")
    //@JoinColumn(name = "ID", updatable=false, insertable=false)
    @ManyToMany(mappedBy="group", fetch = FetchType.LAZY) //, cascade = CascadeType.ALL
    private Set<SystemUser> users;

    public SystemUserGroup() {
    }

    public SystemUserGroup(String username, String groupName) {
        this.username = username;
        this.groupname = groupName;
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

    public String getGroupName() {
        return groupname;
    }

    public void setGroupName(String groupName) {
        this.groupname = groupName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.username);
        hash = 47 * hash + Objects.hashCode(this.groupname);
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
        final SystemUserGroup other = (SystemUserGroup) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.groupname, other.groupname)) {
            return false;
        }
        return true;
    }
    
    public Set<SystemUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SystemUser> users) {
        this.users = users;
    }
}
