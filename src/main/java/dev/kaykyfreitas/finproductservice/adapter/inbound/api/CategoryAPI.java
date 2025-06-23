package dev.kaykyfreitas.finproductservice.adapter.inbound.api;

import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.CreateCategoryRequest;
import dev.kaykyfreitas.finproductservice.adapter.inbound.api.model.request.UpdateCategoryRequest;
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

@Tag(name = "Categories")
@RequestMapping("categories")
public interface CategoryAPI {

    @Operation(
            summary = "Create a new category"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category created successfully"
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
    ResponseEntity<CreateCategoryResponse> create(@RequestBody CreateCategoryRequest request);

    @Operation(
            summary = "Update an existing category"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "A validation error was thrown",
                            content = @Content(schema = @Schema(implementation = DefaultErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category was not found",
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
    ResponseEntity<UpdateCategoryResponse> update(@PathVariable String id, @RequestBody UpdateCategoryRequest request);

    @Operation(
            summary = "Retrieve a category by its identifier"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category or category was not found",
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
    ResponseEntity<GetCategoryByIdResponse> getById(@PathVariable String id);

    @Operation(
            summary = "List categories by search params"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categories retrieved successfully"
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
    ResponseEntity<Pagination<ListCategoryResponse>> list(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @Operation(
            summary = "Delete a category by its identifier"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Category deleted successfully"
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
