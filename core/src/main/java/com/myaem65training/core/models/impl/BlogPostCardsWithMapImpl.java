package com.myaem65training.core.models.impl;

import com.myaem65training.core.models.BlogPostCardsWithMap;
import com.myaem65training.core.models.CardsCarouselWithMap;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class, adapters = BlogPostCardsWithMap.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = {"myaem65training/components/blogcarddesign"})
@Exporter(name = "jackson", extensions = "json")
public class BlogPostCardsWithMapImpl implements BlogPostCardsWithMap {
    private static final Logger LOG = LoggerFactory.getLogger(BlogPostCardsWithMapImpl.class);

    @SlingObject
    Resource componentResource;

    @ValueMapValue
    @Default(values = "Blog Post Card Design Title")
    private String blogPostCardDesignTitle;

    @Override
    public String getBlogPostCardDesignTitle() {
        return blogPostCardDesignTitle;
    }

    @Override
    public List<Map<String, String>> getBlogPostCardDetailsWithMap() {
        List<Map<String, String>> blogPostCardDetailsMap = new ArrayList<>();
        try {
            if (componentResource != null) {
                LOG.info("\n Blog Post Card Design Details with Path {}  ", componentResource.getPath());
                Resource blogPostCardDetail = componentResource.getChild("blogPostCardDetailsWithMap");
                if (blogPostCardDetail != null) {
                    for (Resource blogPostCard : blogPostCardDetail.getChildren()) {
                        Map<String, String> blogPostCardMap = new HashMap<>();
                        blogPostCardMap.put("blogPostCardColor", blogPostCard.getValueMap().get("blogPostCardColor", String.class));
                        blogPostCardMap.put("blogPostCardTitle", blogPostCard.getValueMap().get("blogPostCardTitle", String.class));
                        blogPostCardMap.put("blogPostCardSubTitle", blogPostCard.getValueMap().get("blogPostCardSubTitle", String.class));
                        blogPostCardMap.put("blogPostCardImage", blogPostCard.getValueMap().get("blogPostCardImage", String.class));
                        blogPostCardMap.put("blogPostCardImagePosition", blogPostCard.getValueMap().get("blogPostCardImagePosition", String.class));
                        blogPostCardMap.put("blogPostCardDescription", blogPostCard.getValueMap().get("blogPostCardDescription", String.class).replaceAll("</?p>", ""));
                        blogPostCardMap.put("blogPostCardCategory", blogPostCard.getValueMap().get("blogPostCardCategory", String.class));
                        blogPostCardDetailsMap.add(blogPostCardMap);
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("\n ERROR while getting Blog Post Card Design Details {} ", e.getMessage());
        }
        return blogPostCardDetailsMap;
    }
}
