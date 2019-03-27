package com.bsuir.sdtt.dto.processor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

/**
 * Class of Customer Comment Parameter.
 * Used to create order from offer.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class CustomerCommentParameterDto {
    private UUID id;

    @NotNull
    private UUID customerId;

    @NotNull
    private String message;

    /**
     * Field of customer name.
     */
    @NotNull
    private String name;

    /**
     * Field of customer surname.
     */
    @NotNull
    private String surname;

    /**
     * Field of customer email.
     */
    @NotNull
    @Email
    private String email;

    /**
     * Field of customer age.
     */
    @NotNull
    @Min(1)
    private int age;

    /**
     * Field of customer number
     */
    @NotNull
    @Pattern(regexp = "^\\+375(29|33|44)\\d{7}$")
    private String phoneNumber;

    public CustomerCommentParameterDto(){

    }
}
