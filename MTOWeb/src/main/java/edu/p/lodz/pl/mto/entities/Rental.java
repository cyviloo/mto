/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tomasz
 */
//@JsonAutoDetect
//@Entity
public class Rental implements Serializable {
    
//    @Id
    private Integer idRental;
    private Book book;
    private Account account;
    //@JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date startDate;
    private Date endDate;
    private boolean active;

    public Rental() {
}
//    @JsonCreator
//    public Rental(@JsonProperty("idRental") Integer idRental, 
//            @JsonProperty("book") Book book,@JsonProperty("account") Account account, @JsonProperty("startDate") Date startDate,
//            @JsonProperty("endDate") Date endDate, @JsonProperty("active") boolean active) {
//        this.idRental = idRental;
//        this.book = book;
//        this.account = account;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.active = active;
//        
//    }
    
    public Integer getIdRental() {
        return idRental;
    }

    public void setIdRental(Integer idRental) {
        this.idRental = idRental;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the startDate
     */
     //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
//    @JsonSetter("startDate")
    //@JsonDeserialize(using = CustomDateDeserializer.class)
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRental != null ? idRental.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rental)) {
            return false;
        }
        Rental other = (Rental) object;
        if ((this.idRental == null && other.idRental != null) || (this.idRental != null && !this.idRental.equals(other.idRental))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.p.lodz.pl.mto.entities.Rental[ id=" + idRental + " ]";
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
