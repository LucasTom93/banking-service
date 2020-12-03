package com.banking.shared.identity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IdentityGeneratorImplTest {

    @Test
    void shouldGenerateStringId() {
        //given
        var identityGenerator = new IdentityGeneratorImpl();

        //when
        var stringId = identityGenerator.generateStringId();

        //then
        assertThat(stringId).isNotEmpty();
    }
}