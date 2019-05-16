/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tomasz
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idAccount;
    private String login;
    private String name;
    private String surname;
    private Date birthDate;

    public Account() {
    }

    public Account(String showCurrentUser) {
        this.login = showCurrentUser;
    }

    /**
     * @return the idAccount
     */
    public Integer getIdAccount() {
        return idAccount;
    }

    /**
     * @param idAccount the idAccount to set
     */
    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccount != null ? idAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.idAccount == null && other.idAccount != null) || (this.idAccount != null && !this.idAccount.equals(other.idAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.p.lodz.pl.mto.entities.Account[ id=" + idAccount + " ]";
    }

}
