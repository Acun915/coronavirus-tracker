    package pl.pm.coronavirustracker.controllers;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import pl.pm.coronavirustracker.models.LocationStats;
    import pl.pm.coronavirustracker.services.CoronaVirusDataService;

    import java.util.List;

    @Controller
    public class HomeController {

        private final CoronaVirusDataService coronaVirusDataService;

        @Autowired
        public HomeController(CoronaVirusDataService coronaVirusDataService) {
            this.coronaVirusDataService = coronaVirusDataService;
        }

        @GetMapping("/")
        public String home(Model model) {
            List<LocationStats> allStats = coronaVirusDataService.getAllStats();
            model.addAttribute("locationStats",allStats);
            model.addAttribute("totalReportedCases", allStats.stream()
                    .mapToInt(locationStat -> locationStat.getLatestTotalCases())
                    .sum());
            return "home";
        }

    }