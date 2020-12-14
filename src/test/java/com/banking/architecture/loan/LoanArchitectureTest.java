package com.banking.architecture.loan;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import org.junit.jupiter.api.Test;

import com.banking.shared.domain.annotations.ApplicationService;
import com.banking.shared.domain.annotations.DomainAggregateRoot;
import com.banking.shared.domain.annotations.DomainRepository;
import com.banking.shared.domain.annotations.DomainService;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

class LoanArchitectureTest {
    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.banking.loan");

    @Test
    void loanDomainShouldConsistOfDomainDrivenDesignLayers() {
        layeredArchitecture()
                .layer("API-UI").definedBy("..loan.api")
                .layer("Application").definedBy("..loan.application..")
                .layer("Domain").definedBy("..loan.domain..")
                .layer("Infrastructure").definedBy("..loan.infrastructure..")
                .whereLayer("API-UI").mayNotBeAccessedByAnyLayer()
                .whereLayer("Application").mayOnlyBeAccessedByLayers("API-UI")
                .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("API-UI", "Application", "Domain")
                .check(importedClasses);
    }

    @Test
    void applicationServiceAnnotationShouldBeUsedOnlyInApplicationLayer() {
        classes()
                .that()
                .areAnnotatedWith(ApplicationService.class)
                .should()
                .resideInAPackage("..loan.application..")
                .check(importedClasses);
    }

    @Test
    void domainServiceAnnotationShouldBeUsedOnlyInDomainLayer() {
        classes()
                .that()
                .areAnnotatedWith(DomainService.class)
                .should()
                .resideInAPackage("..loan.domain..")
                .check(importedClasses);
    }

    @Test
    void domainRepositoryAnnotationShouldBeUsedOnlyInInfrastructureLayer() {
        classes()
                .that()
                .areAnnotatedWith(DomainRepository.class)
                .should()
                .resideInAPackage("..loan.infrastructure..")
                .check(importedClasses);
    }

    @Test
    void applicationLayerShouldNotDependOnApiLayer() {
        noClasses()
                .that()
                .resideInAPackage("..loan.application..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..loan.api")
                .check(importedClasses);
    }

    @Test
    void domainLayerShouldNotDependOnSpringContainer() {
        noClasses()
                .that()
                .resideInAPackage("..loan.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..spring..")
                .check(importedClasses);
    }

    @Test
    void domainLayerShouldNotDependOnHigherLayers() {
        noClasses()
                .that()
                .resideInAPackage("..loan.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..loan.api", "..loan.application..")
                .check(importedClasses);
    }

    @Test
    void aggregatesShouldBeDefinedOnlyInDomainLayer() {
        classes()
                .that()
                .areAnnotatedWith(DomainAggregateRoot.class)
                .should()
                .resideInAPackage("..loan.domain..")
                .check(importedClasses);
    }
}