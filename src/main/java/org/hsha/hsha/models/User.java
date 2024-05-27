package org.hsha.hsha.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_tbl")
public class User {
    @Id // tells spring that this is the primary key
    @GeneratedValue
    Integer id;
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //
    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
