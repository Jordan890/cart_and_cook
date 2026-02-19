package com.cartandcook.adapters.persistencejpa;

import com.cartandcook.core.api.GroceryListRepository;
import com.cartandcook.core.domain.GroceryList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GroceryListRepositoryJpa implements GroceryListRepository {

    private final SpringDataGroceryListRepository jpaRepository;

    public GroceryListRepositoryJpa(SpringDataGroceryListRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public GroceryList save(GroceryList groceryList) {
        GroceryListEntity groceryListEntity = new GroceryListEntity();
        if(groceryList.getId() != null) {
            groceryListEntity.setId(groceryList.getId());
        }
        groceryListEntity.setName(groceryList.getName());
        groceryListEntity.setDescription(groceryList.getDescription());
        groceryListEntity.setIngredients(groceryList.getIngredients());
        GroceryListEntity saved = jpaRepository.save(groceryListEntity);
        return toDomain(saved);
    }

    @Override
    public Optional<GroceryList> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<GroceryList> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    private GroceryList toDomain(GroceryListEntity entity) {
        return GroceryList.hydrate(entity.getId(), entity.getName(), entity.getDescription(), entity.getIngredients());
    }
}
