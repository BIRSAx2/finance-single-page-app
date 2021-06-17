package dev.mouhieddine.finance.service.mapper;

import dev.mouhieddine.finance.domain.*;
import dev.mouhieddine.finance.service.dto.WalletDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wallet} and its DTO {@link WalletDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface WalletMapper extends EntityMapper<WalletDTO, Wallet> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    WalletDTO toDto(Wallet s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WalletDTO toDtoId(Wallet wallet);
}
