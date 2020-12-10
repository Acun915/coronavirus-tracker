package pl.pm.coronavirustracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import pl.pm.coronavirustracker.services.CoronaVirusDataService;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Controller
public class CoronaVirusDataController {

    private final CoronaVirusDataService coronaVirusDataService;

    @Autowired
    public CoronaVirusDataController(CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void printDataToConsole() throws IOException, InterruptedException {
        coronaVirusDataService.fetchVirusData();
    }
}
