package pl.pm.coronavirustracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import pl.pm.coronavirustracker.models.LocationStats;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";


    private HttpRequest buildHttpRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
    }
    private HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    private Reader createReader(HttpResponse<String> response) {
        return new StringReader(response.body());
    }
    public List<LocationStats> fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(createReader(getResponse(buildHttpRequest(VIRUS_DATA_URL))));

        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            int totalCasesReported = Integer.parseInt(record.get(record.size()-1));
            int previousDayTotalCasesReported = Integer.parseInt(record.get(record.size()-2));
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatestTotalCases(totalCasesReported);
            locationStats.setDifferenceFromPreviousDay(totalCasesReported-previousDayTotalCasesReported);
            newStats.add(locationStats);
        }
        return newStats;
    }


}
