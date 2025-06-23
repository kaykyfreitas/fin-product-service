package dev.kaykyfreitas.finproductservice.adapter.inbound.api;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.CreateCategoryRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.UpdateCategoryRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.CreateCategoryResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.GetCategoryByIdResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.ListCategoryResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.UpdateCategoryResponse;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("category")
public interface CategoryAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CreateCategoryResponse> create(@RequestBody CreateCategoryRequest request);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<UpdateCategoryResponse> update(@PathVariable String id, @RequestBody UpdateCategoryRequest request);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<GetCategoryByIdResponse> getById(@PathVariable String id);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Pagination<ListCategoryResponse>> list(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @DeleteMapping(
            value = "{id}"
    )
    ResponseEntity<Void> delete(@PathVariable String id);

}
