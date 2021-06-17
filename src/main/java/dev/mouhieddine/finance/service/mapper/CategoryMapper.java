package dev.mouhieddine.finance.service.mapper;

import dev.mouhieddine.finance.domain.*;
import dev.mouhieddine.finance.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    CategoryDTO toDto(Category s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoId(Category category);
}
