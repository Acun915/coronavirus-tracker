package pl.pm.coronavirustracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.pm.coronavirustracker.services.CoronaVirusDataService;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RestController
public class CoronaVirusDataController {

    private final CoronaVirusDataService coronaVirusDataService;

    @Autowired
    public CoronaVirusDataController(CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @PostConstruct
    public void printDataToConsole() throws IOException, InterruptedException {
        coronaVirusDataService.fetchVirusData();
    }

    //    @Autowired
//    public CoronaVirusDataController(CoronaVirusDataService coronaVirusDataService){
//        this.coronaVirusDataService =coronaVirusDataService;
//    }

}
