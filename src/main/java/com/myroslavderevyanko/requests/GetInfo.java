package com.myroslavderevyanko.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myroslavderevyanko.GoogleElevation.Elevation;
import com.myroslavderevyanko.GoogleGeocode.Address_components;
import com.myroslavderevyanko.GoogleGeocode.City;
import com.myroslavderevyanko.GoogleGeocode.Results;
import com.myroslavderevyanko.GoogleMatrix.Elements;
import com.myroslavderevyanko.GoogleMatrix.GoogleMatrixAnswer;
import com.myroslavderevyanko.GoogleMatrix.Rows;
import com.myroslavderevyanko.restcountries.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GetInfo
{
    public final String USER_AGENT = "Mozilla/5.0";
    private String duration = "";

    public City getCityInfo(String city) throws IOException
    {

        String urlEn = "https://maps.googleapis.com/maps/api/geocode/json?&address=" + URLEncoder.encode(city, "UTF-8" ) + "&language=en&key=AIzaSyCnMeFQeKKS3yXkmBxIXilyJ5563Os2xrU";
        URL objEn = new URL(urlEn);
        HttpURLConnection conEn = (HttpURLConnection) objEn.openConnection();
        conEn.setRequestMethod("GET");
        conEn.setRequestProperty("User-Agent", USER_AGENT);
        int responseCodeEn = conEn.getResponseCode();
        BufferedReader inEn = new BufferedReader(new InputStreamReader(conEn.getInputStream()));
        StringBuffer responseEn = new StringBuffer();
        String inputLineEn;
        while ((inputLineEn = inEn.readLine()) != null)
        {
            responseEn.append(inputLineEn);
        }
        ObjectMapper mapper = new ObjectMapper();
        City cityEn = mapper.readValue(responseEn.toString(), City.class);
        if(cityEn.getStatus().equalsIgnoreCase("ZERO_RESULTS"))
        {
            return null;
        }
        return cityEn;
    }

    public String getDistance(String origins, String destination) throws IOException
    {
        String distance = "";

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + URLEncoder.encode(origins, "UTF-8") + "|&destinations=" + URLEncoder.encode(destination, "UTF-8") + "&mode=driving&language=En-en&key=AIzaSyCnMeFQeKKS3yXkmBxIXilyJ5563Os2xrU";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
        {
            response.append(inputLine);
        }
        in.close();
        GoogleMatrixAnswer answ = new ObjectMapper().readValue(response.toString(), GoogleMatrixAnswer.class);
        Rows[] rows = answ.getRows();
        for (Rows r : rows)
        {
            Elements[] e = r.getElements();
            for (Elements el : e)
            {
                distance = el.getDistance().getText();
                duration = el.getDuration().getText();
            }
        }
        return distance;
    }

    public String getDuration()
    {
        return duration;
    }


    public String getCountry(String someCityName) throws IOException
    {
        String urlEn = "https://maps.googleapis.com/maps/api/geocode/json?&address=" + URLEncoder.encode(someCityName, "UTF-8" ) + "&language=en&key=AIzaSyCnMeFQeKKS3yXkmBxIXilyJ5563Os2xrU";
        URL objEn = new URL(urlEn);
        HttpURLConnection conEn = (HttpURLConnection) objEn.openConnection();
        conEn.setRequestMethod("GET");
        conEn.setRequestProperty("User-Agent", USER_AGENT);
        int responseCodeEn = conEn.getResponseCode();
        BufferedReader inEn = new BufferedReader(new InputStreamReader(conEn.getInputStream()));
        StringBuffer responseEn = new StringBuffer();
        String inputLineEn;
        while ((inputLineEn = inEn.readLine()) != null)
        {
            responseEn.append(inputLineEn);
        }
        ObjectMapper mapper = new ObjectMapper();
        City cityEn = mapper.readValue(responseEn.toString(), City.class);
        if(cityEn.getStatus().equalsIgnoreCase("ZERO_RESULTS"))
        {
            return null;
        }
        Results[] resultsEn = cityEn.getResults();
        Address_components[] addressCompEn = resultsEn[0].getAddress_components();
        String country = "";
        for (Address_components a: addressCompEn)
        {
            String[] types = a.getTypes();
            if(types[0].equalsIgnoreCase("country"))
            {
                country = a.getLong_name();
            }
        }
        return country;
    }

    public Country getCountryInfo(String country) throws IOException
    {
        if(country.equalsIgnoreCase("United States"))
        {
            country = "USA"; //restcountries API doesn`t return correct result with param "United States"
        }
        String url ="https://restcountries.eu/rest/v2/name/" + URLEncoder.encode(country, "UTF-8").replaceAll("\\+", "%20");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            response.append(inputLine);
        }
        ObjectMapper mapper = new ObjectMapper();
        Country[] countryInfo = mapper.readValue(response.toString(), Country[].class);
        return countryInfo[0];
    }

    public Elevation getElevation(String lat, String lng) throws IOException
    {
        String url ="https://maps.googleapis.com/maps/api/elevation/json?locations=" + lat +"," + lng +"&key=AIzaSyCnMeFQeKKS3yXkmBxIXilyJ5563Os2xrU";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            response.append(inputLine);
        }
        ObjectMapper mapper = new ObjectMapper();
        Elevation elevation = mapper.readValue(response.toString(), Elevation.class);
        return elevation;
    }
}
