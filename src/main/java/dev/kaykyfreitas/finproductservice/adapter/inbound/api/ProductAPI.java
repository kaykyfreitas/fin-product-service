package dev.kaykyfreitas.finproductservice.adapter.inbound.api;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.CreateProductRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.UpdateProductRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.CreateProductResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.GetProductByIdResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.ListProductResponse;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.UpdateProductResponse;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("products")
public interface ProductAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CreateProductResponse> create(@RequestBody CreateProductRequest request);

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<UpdateProductResponse> update(@PathVariable String id, @RequestBody UpdateProductRequest request);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<GetProductByIdResponse> getById(@PathVariable String id);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Pagination<ListProductResponse>> list(
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
