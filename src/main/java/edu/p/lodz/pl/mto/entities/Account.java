/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import static javax.swing.text.StyleConstants.Size;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Tomasz
 */
@Entity
@Table(name = "account", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"})})
@TableGenerator(name = "Account_Generator", table = "generator", pkColumnName = "name", valueColumnName = "value", pkColumnValue = "account", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Account_Generator")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_account", nullable = false)
    private Integer idAccount;
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Login can only consist of lower and upper case letters and digits.")
    @Basic(optional = false)
    @NotNull(message = "Account login required")
    @Size(min = 1, max = 25, message = "Wrong length")
    @Column(name = "login", nullable = false, length = 25)
    private String login;
    @Basic(optional = false)
    @NotNull(message = "Account name required")
    @Size(min = 1, max = 25, message = "Wrong length")
    @Column(name = "name", nullable = false, length = 25)
    private String name;
    @Basic(optional = false)
    @NotNull(message = "Account lastname required")
    @Size(min = 1, max = 25, message = "Wrong length")
    @Column(name = "surname", nullable = false, length = 25)
    private String surname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;
   
    
    public Account() {
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
