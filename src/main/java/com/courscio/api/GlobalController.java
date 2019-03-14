package com.courscio.api;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jiupeng Zhang
 * @since 03/13/2019
 */
@RestController("globalController")
@RequestMapping("/")
public class GlobalController {
    @GetMapping({"", "index", "home"})
    public ObjectNode home() {
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        return root.put("message", "Welcome to Courscio API Center")
                .put("developer", "Jiupeng (jiupeng.zhang@rochester.edu)")
                .put("documentation_url", "https://doc.courscio.com/api")
                .put("version", "1.0.1");
    }
}
