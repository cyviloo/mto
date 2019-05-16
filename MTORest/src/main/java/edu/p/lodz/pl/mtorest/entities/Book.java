/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.p.lodz.pl.mtorest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.p.lodz.pl.mtorest.utils.ConfigureRentalFilter;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author Tomasz
 */
@Customizer(ConfigureRentalFilter.class)
@Entity
@Table(name = "book")
@TableGenerator(name = "Book_Generator", table = "generator", pkColumnName = "name", valueColumnName = "value", pkColumnValue = "book", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT f FROM Book f"),
    @NamedQuery(name = "Book.findById", query = "SELECT f FROM Book f WHERE f.idBook = :idBook"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT f FROM Book f WHERE f.title = :title")})
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Book_Generator")
    @Basic(optional = false)
    //@NotNull
    @Column(name = "id_book", nullable = false)
    private Integer idBook;
    @Basic(optional = false)
    @NotNull(message = "Title is required")
    @Size(min = 1, max = 80, message = "Title field has wrong length")
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Basic(optional = false)
    @NotNull(message = "Author is required")
    @Size(min = 1, max = 80, message = "Author field has wrong length")
    @Column(name = "author", nullable = false, length = 1000)
    private String author;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year_published", nullable = false)
    private int year;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "book")
    @JsonBackReference
    @JsonIgnore
    private Rental rentalList;
    @Transient
    @JsonProperty("active")
    private boolean active;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.author = author;
        this.title = title;
        this.year = year;
    }
    
    public Book(String title, String author, int year, Rental rentalList) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.rentalList = rentalList;
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
        return "edu.p.lodz.pl.mtorest.entities.Book[ id=" + getIdBook() + " ]";
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
/**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
    @JsonIgnore
    public Rental getRentalList() {
        return rentalList;
    }

//    /**
//     * @param rentalList the rentalList to set
//     */
//    public void setRentalList(Rental rentalList) {
//        this.rentalList = rentalList;
//    }

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
    @JsonProperty("active")
    public boolean isActive() {
        if (this.rentalList!=null){
            return true;
        } else return false;
    }

    /**
     * @param active the active to set
     */
    @JsonIgnore
    public void setActive(boolean active) {
        this.active = active;
    }

}