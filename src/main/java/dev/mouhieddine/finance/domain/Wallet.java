package dev.mouhieddine.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.mouhieddine.finance.domain.enumeration.Currency;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * A Wallet.
 */
@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "count_total")
    private Boolean countTotal;

    @Column(name = "color")
    private String color;

    @Column(name = "uaa_order")
    private Integer order;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "wallet", "category" }, allowSetters = true)
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Wallet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Wallet currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Boolean getCountTotal() {
        return this.countTotal;
    }

    public Wallet countTotal(Boolean countTotal) {
        this.countTotal = countTotal;
        return this;
    }

    public void setCountTotal(Boolean countTotal) {
        this.countTotal = countTotal;
    }

    public String getColor() {
        return this.color;
    }

    public Wallet color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOrder() {
        return this.order;
    }

    public Wallet order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public Wallet transactions(Set<Transaction> transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public Wallet addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setWallet(this);
        return this;
    }

    public Wallet removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setWallet(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setWallet(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setWallet(this));
        }
        this.transactions = transactions;
    }

    public User getUser() {
        return this.user;
    }

    public Wallet user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wallet)) {
            return false;
        }
        return id != null && id.equals(((Wallet) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Wallet{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", currency='" +
            getCurrency() +
            "'" +
            ", countTotal='" +
            getCountTotal() +
            "'" +
            ", color='" +
            getColor() +
            "'" +
            ", order=" +
            getOrder() +
            "}"
        );
    }
}
