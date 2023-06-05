package com.marketplace.payments;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class StripeService 
{
    @Value("${stripe.api.key}")
	private String apiKey;
	
	public JSONObject createPaymentLink(String currency, JSONArray products, String successUrl, String cancelUrl) throws StripeException 
	{
        Stripe.apiKey = apiKey;
		List<Product> productList = new ArrayList<>();

		/* Get list of products from JSONArray */
		for (Object obj : products) 
		{
			JSONObject jsonObj = (JSONObject) obj;
			String name = (String) jsonObj.get("name");
			Long quantity = (Long) jsonObj.get("quantity");
			Double price = (Double) jsonObj.get("price");
			Product product = new Product(name, quantity, price);
			productList.add(product);
		}

        /* Create a PaymentIntentParams object */
        Map<String,Object> paymentIntentParams = new HashMap<>();
        paymentIntentParams.put("amount", 0);
        paymentIntentParams.put("currency", currency);

		/* Add products to SessionCreateParams */
		List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);

			SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
				.setQuantity(product.getQuantity())
				.setPriceData(
					SessionCreateParams.LineItem.PriceData.builder()
						.setCurrency(currency)
						.setUnitAmount(Math.round(product.getPrice() * 100L))
						.setProductData(
							SessionCreateParams.LineItem.PriceData.ProductData.builder()
								.setName(product.getName())
								.build())
						.build())
				.build();

			lineItems.add(lineItem);
			int productPrice = (int) Math.round(product.getPrice() * product.getQuantity() * 100);
			paymentIntentParams.put("amount", (int) paymentIntentParams.get("amount") + productPrice);
		}

		/* Create a session */
		SessionCreateParams sessionParams = SessionCreateParams.builder()
			.addAllLineItem(lineItems)
			.setMode(SessionCreateParams.Mode.PAYMENT)
			.setSuccessUrl(successUrl)
			.setCancelUrl(cancelUrl)
			.putAllMetadata(paymentIntentParams.entrySet()
					.stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())))
			.build();

		Session session = Session.create(sessionParams);

		/* Create a JSON object with payment intent and payment link */
		JSONObject response = new JSONObject();
		response.put("session_id", session.getId());
		response.put("payment_status", session.getPaymentStatus());
		response.put("payment_intent_id", session.getPaymentIntent());
		response.put("payment_link", session.getUrl());
		
		return response;
    }

	public JSONObject getPaymentSessionStatus(String sessionId) throws StripeException 
	{
		Stripe.apiKey = apiKey;
		Session session = Session.retrieve(sessionId);

		JSONObject response = new JSONObject();
		response.put("status", session.getPaymentStatus());

		return response;
	}
}
