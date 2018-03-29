package com.myroslavderevyanko.controllers;

import com.myroslavderevyanko.googleGeocode.City;
import com.myroslavderevyanko.models.SimpleCity;
import com.myroslavderevyanko.requests.GetInfo;
import com.myroslavderevyanko.restcountries.Country;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

@Controller
    public class IndexController
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model)
    {
        return "home";
    }

    @RequestMapping(value = "/country", method = RequestMethod.POST)
    public String user(@Validated SimpleCity city, Model model) throws IOException
    {
        model.addAttribute("city", city.getCityName());
        GetInfo info = new GetInfo();
        String coutryStr = info.getCountry(city.getCityName());
        if (coutryStr==null)
        {
            return "error";
        }
        Country country = info.getCountryInfo(coutryStr);
        String capital = country.getCapital();
        model.addAttribute("country", country.getName());
        model.addAttribute("capital", capital);
        model.addAttribute("image", country.getFlag());
        model.addAttribute("population", country.getPopulation());
        model.addAttribute("distance", info.getDistance(city.getCityName(), capital));
        model.addAttribute("duration", info.getDuration());
        City capitalCity = info.getCityInfo(capital);
        model.addAttribute("elevation", new BigDecimal((info.getElevation(capitalCity.getResults()[0].getGeometry().getLocation().getLat(), capitalCity.getResults()[0].getGeometry().getLocation().getLng()).getResults()[0].getElevation())).setScale(2, RoundingMode.HALF_UP).doubleValue());
        model.addAttribute("lat", capitalCity.getResults()[0].getGeometry().getLocation().getLat());
        model.addAttribute("lng", capitalCity.getResults()[0].getGeometry().getLocation().getLng());
        return "country";
    }
}