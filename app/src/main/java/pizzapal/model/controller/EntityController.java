package pizzapal.model.controller;

import pizzapal.model.service.StorageLogic;
import pizzapal.model.service.StorageService;

abstract class EntityController<E> {

    protected final StorageLogic logic;
    protected final StorageService service;

    public EntityController(StorageLogic logic, StorageService service) {
        this.logic = logic;
        this.service = service;
    }

}
