package com.banking.shared.identity;

import java.util.UUID;

class IdentityGeneratorImpl implements IdentityGenerator {

    @Override
    public String generateStringId() {
        return UUID.randomUUID().toString();
    }
}