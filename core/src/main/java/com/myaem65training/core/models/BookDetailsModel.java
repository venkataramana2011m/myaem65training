package com.myaem65training.core.models;

import com.day.cq.tagging.TagManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, resourceType = { "myaem65training/components/bookdetail" }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json", options = { @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true") })
public class BookDetailsModel {
    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;
    @Inject
    @Self
    private List<BookDetailResult> bookDetailResults = new ArrayList<>();

    @PostConstruct
    protected void init() {
       System.out.println("Printing the Book Details :: {}"+ bookDetailResults);
    }
    public List<BookDetailResult> getBookDetailResult() {
        return bookDetailResults;
    }
}
