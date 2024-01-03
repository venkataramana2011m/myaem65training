package com.myaem65training.core.models.impl;

import com.myaem65training.core.models.CardsCarouselWithMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Model(adaptables = SlingHttpServletRequest.class, adapters = CardsCarouselWithMap.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = { "myaem65training/components/cardscarousel" })
@Exporter(name = "jackson", extensions = "json")
public class CardsCarouselWithMapImpl implements CardsCarouselWithMap {
    private static final Logger LOG = LoggerFactory.getLogger(CardsCarouselWithMapImpl.class);

    @SlingObject
    Resource componentResource;

    @ValueMapValue
    @Default(values = "Cards Carousel Listing Title")
    private String cardsCarouselListingTitle;

    @Override
    public String getCardsCarouselListingTitle() {
        return cardsCarouselListingTitle;
    }

    @Override
    public List<Map<String, String>> getCardDetailsWithMap() {
        List<Map<String, String>> cardDetailsMap = new ArrayList<>();
        try {
            if (componentResource != null) {
                LOG.info("\n Cards Carousel Details with Path {}  ", componentResource.getPath());
                Resource cardDetail = componentResource.getChild("cardDetailsWithMap");
                if (cardDetail != null) {
                    for (Resource book : cardDetail.getChildren()) {
                        Map<String, String> cardMap = new HashMap<>();
                        cardMap.put("cardTitle", book.getValueMap().get("cardTitle", String.class));
                        cardMap.put("cardImage", book.getValueMap().get("cardImage", String.class));
                        cardMap.put("cardDescription", book.getValueMap().get("cardDescription", String.class).replaceAll("</?p>", ""));
                        cardMap.put("cardCategory", book.getValueMap().get("cardCategory", String.class));
                        cardDetailsMap.add(cardMap);
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("\n ERROR while getting Card Carousel Details {} ", e.getMessage());
        }
        LOG.info("\n SIZE {} ", cardDetailsMap.size());
        return cardDetailsMap;
    }
}
