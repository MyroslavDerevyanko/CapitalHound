package com.myroslavderevyanko.GoogleGeocode;

public class Results
{
    private String place_id;

    private Address_components[] addressComponents;

    private String formatted_address;

    private String[] types;

    private Geometry geometry;

    boolean partial_match;



    public String getPlace_id()
    {
        return place_id;
    }

    public void setPlace_id(String place_id)
    {
        this.place_id = place_id;
    }

    public Address_components[] getAddress_components()
    {
        return addressComponents;
    }

    public void setAddress_components(Address_components[] addressComponents)
    {
        this.addressComponents = addressComponents;
    }

    public String getFormatted_address()
    {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address)
    {
        this.formatted_address = formatted_address;
    }

    public String[] getTypes()
    {
        return types;
    }

    public void setTypes(String[] types)
    {
        this.types = types;
    }

    public Geometry getGeometry()
    {
        return geometry;
    }

    public void setGeometry(Geometry geometry)
    {
        this.geometry = geometry;
    }

    public boolean isPartial_match()
    {
        return partial_match;
    }

    public void setPartial_match(boolean partial_match)
    {
        this.partial_match = partial_match;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [place_id = " + place_id + ", addressComponents = " + addressComponents + ", formatted_address = " + formatted_address + ", types = " + types + ", geometry = " + geometry + "]";
    }
}
