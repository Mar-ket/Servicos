package com.marketplace.payments;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PaymentRequest
{
    private String currency;
    private JSONArray products;
    private String successUrl;
    private String cancelUrl;

    public PaymentRequest(String currency, JSONArray products, String successUrl, String cancelUrl)
    {
        this.currency = currency;
        this.products = products;
        this.successUrl = successUrl;
        this.cancelUrl = cancelUrl;
    }

    public PaymentRequest() {}

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public JSONArray getProducts()
    {
        return products;
    }

    public void setProducts(JSONArray products)
    {
        this.products = products;
    }

    public String getSuccessUrl()
    {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl)
    {
        this.successUrl = successUrl;
    }

    public String getCancelUrl()
    {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl)
    {
        this.cancelUrl = cancelUrl;
    }

    public static PaymentRequest fromJsonString(String jsonString) throws ParseException
    {
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject) parser.parse(jsonString);
        String currency = (String) jsonObj.get("currency");
        JSONArray products = (JSONArray) jsonObj.get("products");
        String successUrl = (String) jsonObj.get("successUrl");
        String cancelUrl = (String) jsonObj.get("cancelUrl");
        return new PaymentRequest(currency, products, successUrl, cancelUrl);
    }
}
