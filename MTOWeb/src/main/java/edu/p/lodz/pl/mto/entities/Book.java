/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mto.entities;

import edu.p.lodz.pl.mto.utils.ConfigureRentalFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author Tomasz
 */

public class Book implements Serializable {

    private Integer idBook;
    private String title;
    private String author;
    private int year;
    private Rental rentalList;
    private boolean active;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.author = author;
        this.title = title;
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getIdBook() != null ? getIdBook().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.getIdBook() == null && other.getIdBook() != null) || (this.getIdBook() != null && !this.idBook.equals(other.idBook))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.p.lodz.pl.mto.entities.Book[ id=" + getIdBook() + " ]";
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

//    /**
//     * @return the rentalList
//     */
//    public List<Rental> getRentalList() {
//        return rentalList;
//    }
//
//    /**
//     * @param rentalList the rentalList to set
//     */
//    public void setRentalList(List<Rental> rentalList) {
//        this.rentalList = rentalList;
//    }
    /**
     * @return the rentalList
     */
    public Rental getRentalList() {
        return rentalList;
    }

    /**
     * @param rentalList the rentalList to set
     */
    public void setRentalList(Rental rentalList) {
        this.rentalList = rentalList;
    }

    /**
     * @return the idBook
     */
    public Integer getIdBook() {
        return idBook;
    }

    /**
     * @param idBook the idBook to set
     */
    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
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
