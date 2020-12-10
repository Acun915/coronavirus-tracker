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

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse;

        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        Reader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            int totalCasesReported = Integer.parseInt(record.get(record.size()-1));
            int previousDayTotalCasesReported = Integer.parseInt(record.get(record.size()-2));
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatestTotalCases(totalCasesReported);
            locationStats.setDifferenceFromPreviousDay(totalCasesReported-previousDayTotalCasesReported);
            System.out.println(locationStats);
            newStats.add(locationStats);
        }
        this.allStats = newStats;
        csvBodyReader.close();
    }

}
