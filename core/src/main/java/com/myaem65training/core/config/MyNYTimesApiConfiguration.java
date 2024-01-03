package com.myaem65training.core.config;

import org.apache.sling.api.SlingConstants;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Newyork Times Osgi Configuration",
        description = "This configuration captures the Newyork Times API configuration details"
)
public @interface MyNYTimesApiConfiguration {
    @AttributeDefinition(name = SlingConstants.PROPERTY_PATH,
            description = "Property [path for the SBG")
    String path() default "/content/myaem65training/";
    @AttributeDefinition(
            name = "nytimesEndPoint",
            description = "Enter the URL for the Newyork Times API Endpoint"
    )
    String nytimesEndPoint() default "https://api.nytimes.com/svc/";

    @AttributeDefinition(
            name = "nytimesApiId",
            description = "Enter Api Id Newyork Times API"
    )
    String nytimesApiId() default "8fa5985b-0a4b-4b12-a773-58bf5f2d1280";

    @AttributeDefinition(
            name = "nytimesAPIKey",
            description = "Enter API-Key Newyork Times API"
    )
    String nytimesAPIKey() default "y5GQJ5ljcgfpEZ4TpGRMnWb7RabGFPUB";

    @AttributeDefinition(
            name = "nytimesSecretKey",
            description = "Enter Secret Key Newyork Times API"
    )
    String nytimesSecretKey() default "AVE7orBih1FoFfsN";

    @AttributeDefinition(
            name = "nytimesBooksListByCategoryEndPoint",
            description = "Enter the URL for the Newyork Times API Endpoint"
    )
    String nytimesBooksListByCategoryEndPoint() default "books/v3/lists/names.json";

    @AttributeDefinition(
            name = "nytimesBooksListEndPoint",
            description = "Enter the URL for the Newyork Times API Endpoint"
    )
    String nytimesBooksListEndPoint() default "books/v3/lists.json";
}