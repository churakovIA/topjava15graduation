package ru.churakov.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.churakov.graduation.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Transactional
    @Override
    Dish save(Dish dish);

}
