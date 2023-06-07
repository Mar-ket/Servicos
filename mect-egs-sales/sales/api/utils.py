import os
import requests
from sales.api.models import Sale
from django.conf import settings
from django.http import JsonResponse

def saleCheckout(request, id):
    sale = Sale.objects.get(id=id)

    if sale.payment_status == 'paid':
        return JsonResponse({'error': 'Sale already paid'}, status=200)

    success_url = request.data.get('successUrl', '')
    cancel_url = request.data.get('cancelUrl', '')

    if not success_url or not cancel_url:
        return JsonResponse({'error': 'successUrl and cancelUrl are required'}, status=400)
    
    # Call Products API in order to get name and price
    products = []

    for product in sale.products:
        object = dict()
        response = requests.get(os.environ.get('PRODUCTS_API_URL') + '/' + str(product['product_id']) + '/')
        
        if response.status_code != 200:
            return JsonResponse({'error': 'Error in products API'}, status=500)
        
        object["name"] = response.json()["name"]
        object["price"] = response.json()["price"]
        object["quantity"] = product["quantity"]
        products.append(object)

    data = {
        "currency": os.environ.get('DEFAULT_CURRENCY'),
        "successUrl": success_url,
        "cancelUrl": cancel_url,
        "products": products
    }

    response = requests.post(os.environ.get('PAYMENTS_API_URL')+'/pay', json=data)

    if response.status_code != 200:
        return JsonResponse({'error': 'Error in payments API'}, status=500)

    sale.payment_id = response.json()['session_id']
    sale.payment_status = response.json()['payment_status']
    sale.save()

    return JsonResponse({'success': True, 'payment_link': response.json()['payment_link']})