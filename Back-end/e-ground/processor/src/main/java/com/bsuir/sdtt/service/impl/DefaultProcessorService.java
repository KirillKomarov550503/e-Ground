package com.bsuir.sdtt.service.impl;

import com.bsuir.sdtt.client.CatalogClient;
import com.bsuir.sdtt.client.CustomerManagementClient;
import com.bsuir.sdtt.client.InventoryClient;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.dto.customer.CustomerDto;
import com.bsuir.sdtt.dto.inventory.OrderDto;
import com.bsuir.sdtt.dto.processor.CreateOrderParameterDto;
import com.bsuir.sdtt.service.ProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultProcessorService implements ProcessorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProcessorService.class);

    private final CatalogClient catalogClient;

    private final InventoryClient inventoryClient;

    private final CustomerManagementClient customerManagementClient;

    @Autowired
    public DefaultProcessorService(CatalogClient catalogClient, InventoryClient inventoryClient,
                                   CustomerManagementClient customerManagementClient) {
        this.catalogClient = catalogClient;
        this.inventoryClient = inventoryClient;
        this.customerManagementClient = customerManagementClient;
    }

    @Override
    public OrderDto addToFavorite(CreateOrderParameterDto createOrderParameter) {
        OrderDto orderDto = new OrderDto();

        UUID customerId = createOrderParameter.getCustomerId();
        UUID itemId = createOrderParameter.getItemId();

        CustomerDto customerDto = customerManagementClient.getCustomerDto(customerId);
        OfferDto offerDto = catalogClient.getOfferDto(itemId);

        LOGGER.info("Start method DefaultProcessorService.createOrder customerId = {}", customerId);

        if (customerId.equals(customerDto.getId())) {
            orderDto.setCustomerId(customerId);
            orderDto.setName(customerDto.getName());
            orderDto.setEmail(customerDto.getEmail());
            orderDto.setTotalPrice(offerDto.getPrice() * createOrderParameter.getItemCount());
            orderDto.setOrderItemCount(createOrderParameter.getItemCount());

            inventoryClient.save(orderDto);
        }

        return orderDto;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        LOGGER.debug("Start method DefaultProcessorService.createCustomer Customer DTO = {}, Address DTO = {}", customerDto);

        return customerManagementClient.save(customerDto);
    }

    @Override
    public OfferDto createOffer(OfferDto offerDto) {
        LOGGER.debug("Start method DefaultProcessorService.createOffer Offer DTO = {}", offerDto);

        return catalogClient.save(offerDto);
    }

    @Override
    public OfferDto getOfferById(UUID id) {
        LOGGER.info("Start method DefaultProcessorService.getOfferById ID = {}", id);

        return catalogClient.getOfferDto(id);
    }

    @Override
    public List<OfferDto> getOffersByFilter(String category, String priceFrom, String priceTo) {
        LOGGER.info("Start method DefaultProcessorService.getOffersByFilter");

        return catalogClient.getOffersDto(category, priceFrom, priceTo);
    }

    @Override
    public CustomerDto getCustomerById(UUID id) {
        LOGGER.info("Start method DefaultProcessorService.getCustomersByEmail ID = {}", id);

        return customerManagementClient.getCustomerDto(id);
    }

    @Override
    public List<OrderDto> getOrderByCustomerId(UUID id) {
        LOGGER.info("Start method DefaultProcessorService.getOrderById ID = {}", id);

        return inventoryClient.getOrdersDto(id);
    }
}
