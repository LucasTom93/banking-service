package com.banking.loan.infrastructure.circuitbreaker;

public class CustomerCheckResultDto {
    private String customerTaxId;
    private Boolean registeredDebtor;

    public String getCustomerTaxId() {
        return customerTaxId;
    }

    public Boolean getRegisteredDebtor() {
        return registeredDebtor;
    }

    public static final class Builder {
        private String customerTaxId;
        private Boolean isRegisteredDebtor;

        private Builder() {
        }

        public static Builder customerCheckResultDto() {
            return new Builder();
        }

        public Builder withCustomerTaxId(String customerTaxId) {
            this.customerTaxId = customerTaxId;
            return this;
        }

        public Builder withIsRegisteredDebtor(Boolean isRegisteredDebtor) {
            this.isRegisteredDebtor = isRegisteredDebtor;
            return this;
        }

        public CustomerCheckResultDto build() {
            CustomerCheckResultDto customerCheckResultDto = new CustomerCheckResultDto();
            customerCheckResultDto.customerTaxId = this.customerTaxId;
            customerCheckResultDto.registeredDebtor = this.isRegisteredDebtor;
            return customerCheckResultDto;
        }
    }
}