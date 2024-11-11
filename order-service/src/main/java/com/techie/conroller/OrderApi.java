package com.techie.conroller;

import com.techie.domain.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Order", description = "the Order API")
public interface OrderApi {

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order with the provided order details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided")
    })
    ResponseEntity<OrderDto> createOrder(OrderDto orderDto);

    @Operation(
            summary = "Get order by ID",
            description = "Fetches the order identified by the provided order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully fetched"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<OrderDto> getOrderById(Long orderId);

    @Operation(
            summary = "Get all orders",
            description = "Fetches all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders successfully fetched")
    })
    ResponseEntity<List<OrderDto>> getAllOrders();

    @Operation(
            summary = "Update order",
            description = "Updates the order identified by the provided order ID with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully updated"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided")
    })
    ResponseEntity<OrderDto> updateOrder(Long orderId, OrderDto orderDto);

    @Operation(
            summary = "Delete order by ID",
            description = "Deletes the order identified by the provided order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<Void> deleteOrderById(Long orderId);
}
