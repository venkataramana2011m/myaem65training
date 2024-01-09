package com.myaem65training.core.cqcomponents;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.adobe.cq.sightly.WCMUsePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@SuppressWarnings("rawtypes")
public class HashMapExample extends WCMUsePojo {

    // here the sightly code to loop through the map
    //<div data-sly-use.hashmap="com.adobe.examples.htl.core.hashmap.HashMapExample"
    //	     data-sly-list="${hashmap.map.keySet.iterator}">
    //	     ${item}
    //	     <ul data-sly-list.aem="${hashmap.map[item].keySet.iterator}">
    //	     	<li>${aem} ${hashmap.map[item][aem]}</li>
    //	     </ul>
    //	</div>


    public HashMap<String, HashMap> map = new HashMap<String, HashMap>();
    public Map<String, String> myMap = new HashMap<String, String>();

    @Override
    @SuppressWarnings("unchecked")
    public void activate() throws IOException {
        HashMap m1 = new HashMap<>();
        HashMap m2 = new HashMap<>();
        for(int i=0; i<10;i++){
            m1.put("aem"+i, "6."+i);
            m2.put("cq"+i, "5."+i);
        }
        map.put("a", m1 );
        map.put("b", m2);
        myMap.put("myaem", "6.1");
        myMap.put("myaem6", "6.2");
        myMap.put("mycq", "5.5");
        myMap.put("mycq5", "5.4");
        String JSON_ARRAY
                = "[{ \"color\" : \"Blue\", \"type\" : \"Sedan\",\"name\" : \"Honda City\" }, { \"color\" : \"Red\", \"type\" : \"Hatchback\",\"name\" : \"Santro\"  }]";
        String s = "{\"name\": \"educative\",\"website\": \"https://www.educative.io/\",\"description\": \"e-learning website\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.readValue(s, Map.class));
        System.out.println(objectMapper.readValue(JSON_ARRAY, Map.class));


    }

}