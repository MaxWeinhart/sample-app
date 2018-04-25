package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @Value("${local.server.port}")
    private String serverPort;

    @Value("${local.server.hostname}")
    private String serverHostName;

    @RequestMapping("/")
    public String index() {
        return "Whohooooo! Greetings from Jambit! (" + serverHostName + ":" + serverPort + ")";
    }

}
