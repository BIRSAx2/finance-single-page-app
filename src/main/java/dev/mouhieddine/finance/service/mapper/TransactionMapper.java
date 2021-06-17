package dev.mouhieddine.finance.service.mapper;

import dev.mouhieddine.finance.domain.*;
import dev.mouhieddine.finance.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { WalletMapper.class, CategoryMapper.class })
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(target = "wallet", source = "wallet", qualifiedByName = "id")
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    TransactionDTO toDto(Transaction s);
}
