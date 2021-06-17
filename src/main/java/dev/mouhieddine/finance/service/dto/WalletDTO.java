package dev.mouhieddine.finance.service.dto;

import dev.mouhieddine.finance.domain.enumeration.Currency;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link dev.mouhieddine.finance.domain.Wallet} entity.
 */
public class WalletDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Currency currency;

    private Boolean countTotal;

    private String color;

    private Integer order;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Boolean getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Boolean countTotal) {
        this.countTotal = countTotal;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalletDTO)) {
            return false;
        }

        WalletDTO walletDTO = (WalletDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, walletDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalletDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", countTotal='" + getCountTotal() + "'" +
            ", color='" + getColor() + "'" +
            ", order=" + getOrder() +
            ", user=" + getUser() +
            "}";
    }
}
