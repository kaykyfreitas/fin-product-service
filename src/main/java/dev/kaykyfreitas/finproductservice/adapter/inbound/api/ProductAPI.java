package dev.kaykyfreitas.finproductservice.adapter.inbound.api;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.CreateProductRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.UpdateProductRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.response.*;
import dev.kaykyfreitas.finproductservice.domain.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Products")
@RequestMapping("products")
public interface ProductAPI {

    @Operation(
            summary = "Create a new product"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "A validation error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    )
            }
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CreateProductResponse> create(@RequestBody CreateProductRequest request);

    @Operation(
            summary = "Update an existing product"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "A validation error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product or category was not found",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    )
            }
    )
    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<UpdateProductResponse> update(@PathVariable String id, @RequestBody UpdateProductRequest request);

    @Operation(
            summary = "Retrieve a product by its identifier"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product or category was not found",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    )
            }
    )
    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<GetProductByIdResponse> getById(@PathVariable String id);

    @Operation(
            summary = "List products by search params"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Products retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    )
            }
    )
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

    @Operation(
            summary = "Delete a product by its identifier"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Products deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An internal server error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    )
            }
    )
    @DeleteMapping(
            value = "{id}"
    )
    ResponseEntity<Void> delete(@PathVariable String id);
    
}
