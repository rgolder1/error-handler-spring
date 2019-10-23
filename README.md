# Resource bundle message source custom validation

Spring 5.2.0.RELEASE: Resource bundle message source custom validation message no longer resolved

Raised ticket with Spring:
https://github.com/spring-projects/spring-framework/issues/23839

To replicate run:

`mvn clean test`

Which fails with:

`Failed tests:   testNumberTooHigh(example.ConstraintErrorIntegrationTest): expected:<[The number must not be more than 10]> but was:<[{test.api.simpleupdate.number.max}]>`

To view previous behaviour update the `pom.xml` to swap in the previous versions of Spring, Spring Boot, and Hibernate Validator.  (These are commented out in the `<properties>` section).  The test then passes.

N.B. The hibernate validator version makes no difference, but switching in the `<properties>` in line with the respective Spring Boot version.

# Jira Ticket Details:

**Issue:**

Resource bundle message source custom validation message no longer resolved following upgrade from Spring 5.1.6.RELEASE to Spring 5.2.0.RELEASE.

DefaultListableBeanFactory preinstantiateSingletons() bean instantiation order:

`LocalValidatorFactoryBean "defaultValidator"`

`RequestMappingHandlerAdapter "requestMappingHandlerAdapter"`

`LocalValidatorFactoryBean "mvcValidator"`

**Spring 5.1.6 behaviour:**

WebMvcConfigurationSupport requestMappingHandlerAdapter() @Bean method takes no args. It calls WebMvcAutoConfiguration mvcValidator() which calls WebMvcConfigurerComposite getValidator() which calls the getValidator() @Override in my Configuration class, which creates a LocalValidatorFactoryBean setting the ValidationMessageSource to my ReloadableResourceBundleMessageSource messageSource.

_Upshot:_ requestMappingHandlerAdapter configured with the userResourceBundleLocator set to MessageSourceResoureBundleLocator as expected.

**Spring 5.2.0 behaviour:**

WebMvcConfigurationSupport requestMappingHandlerAdapter() @Bean method now takes a Validator. It is instantiated with the "defaultValidator" LocalValidatorFactoryBean. That has a PlatformResourceBundleLocator as the userResourceBundleLocator.

_Upshot:_ requestMappingHandlerAdapter configured with the userResourceBundleLocator set to PlatformResoureBundleLocator.

Subsequently, the mvcValidator() @Bean is instantiated, but too late for use in the requestMappingHandlerAdapter.
