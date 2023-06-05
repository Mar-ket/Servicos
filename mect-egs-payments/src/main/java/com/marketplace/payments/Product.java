package com.marketplace.payments;

public class Product 
{    
    private String name;
    private Long quantity;
    private Double price;
    
    public Product(String name, Long quantity, Double price) 
    {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
    public Long getQuantity() 
    {
        return quantity;
    }
    
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }
    
    public String getName() 
    {
        return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }
    
    public Double getPrice() 
    {
        return price;
    }
    
    public void setPrice(Double price) 
    {
        this.price = price;
    }
}
