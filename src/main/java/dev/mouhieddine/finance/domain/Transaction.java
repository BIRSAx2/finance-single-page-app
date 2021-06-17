package dev.mouhieddine.finance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.mouhieddine.finance.domain.enumeration.TransactionType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "transactions", "user" }, allowSetters = true)
    private Wallet wallet;

    @ManyToOne(optional = true)
    @JsonIgnoreProperties(value = { "transactions", "user" }, allowSetters = true)
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Transaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Transaction amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return this.type;
    }

    public Transaction type(TransactionType type) {
        this.type = type;
        return this;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Instant getDate() {
        return this.date;
    }

    public Transaction date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public Transaction wallet(Wallet wallet) {
        this.setWallet(wallet);
        return this;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Category getCategory() {
        return this.category;
    }

    public Transaction category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", amount=" + getAmount() +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
