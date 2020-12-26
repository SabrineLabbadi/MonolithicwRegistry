package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Concert.
 */
@Entity
@Table(name = "concert")
public class Concert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "concert")
    private Set<Band> bands = new HashSet<>();

    @OneToMany(mappedBy = "concert")
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Concert name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Concert date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public Concert address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Band> getBands() {
        return bands;
    }

    public Concert bands(Set<Band> bands) {
        this.bands = bands;
        return this;
    }

    public Concert addBand(Band band) {
        this.bands.add(band);
        band.setConcert(this);
        return this;
    }

    public Concert removeBand(Band band) {
        this.bands.remove(band);
        band.setConcert(null);
        return this;
    }

    public void setBands(Set<Band> bands) {
        this.bands = bands;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Concert customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Concert addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setConcert(this);
        return this;
    }

    public Concert removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setConcert(null);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concert)) {
            return false;
        }
        return id != null && id.equals(((Concert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concert{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
