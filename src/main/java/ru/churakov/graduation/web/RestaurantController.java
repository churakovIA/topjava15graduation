package ru.churakov.graduation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.churakov.graduation.model.Dish;
import ru.churakov.graduation.model.Menu;
import ru.churakov.graduation.model.Restaurant;
import ru.churakov.graduation.model.Vote;
import ru.churakov.graduation.service.DishService;
import ru.churakov.graduation.service.MenuService;
import ru.churakov.graduation.service.RestaurantService;
import ru.churakov.graduation.service.VoteService;
import ru.churakov.graduation.to.MenuTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.churakov.graduation.util.Util.orElse;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/rest/restaurants";

    @Autowired
    RestaurantService service;

    @Autowired
    MenuService menuService;

    @Autowired
    VoteService voteService;

    @Autowired
    DishService dishService;

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Restaurant get(@PathVariable int id) {
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = service.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        service.update(restaurant, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping(value = "/{id}/menu")
    public Menu getMenu(@PathVariable int id,
                        @RequestParam(value = "date", required = false) LocalDate date) {
        return menuService.getWithRestaurantAndDishes(orElse(date, LocalDate.now()), id);
    }

    @PutMapping(value = "/{id}/menu")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenu(@PathVariable int id,
                           @RequestParam(required = false) LocalDate date,
                           @Valid @RequestBody MenuTo menuTo) {
        menuService.updateDishes(orElse(date, LocalDate.now()), id, menuTo.getDishes());
    }

    @PostMapping(value = "/{id}/menu/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDishWithLocation(@PathVariable int id,
                                                       @RequestParam(required = false) LocalDate date,
                                                       @Valid @RequestBody Dish dish) {
        Dish created = dishService.create(dish, id, orElse(date, LocalDate.now()));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/menu/dishes/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/menu/dishes/{id}")
    public Dish getDish(@PathVariable int id) {
        return dishService.get(id);
    }

    @PutMapping(value = "/menu/dishes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@PathVariable int id, @Valid @RequestBody Dish dish) {
        dishService.update(dish, id);
    }

    @DeleteMapping(value = "/menu/dishes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int id) {
        dishService.delete(id);
    }

    @PutMapping(value = "/{id}/vote")
    public ResponseEntity<Vote> vote(@PathVariable int id) {
        VoteService.UpdatedVote updatedVote = voteService.createOrUpdate(id, SecurityUtil.authUserId());
        if (updatedVote.isCreated()) {
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(VoteController.REST_URL + "/{id}")
                    .buildAndExpand(updatedVote.getVote().getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(updatedVote.getVote());
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
