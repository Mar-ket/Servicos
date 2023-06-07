import requests
import os
from django.contrib.auth.models import User, Group
from rest_framework import viewsets, views
from rest_framework import permissions
from rest_framework.response import Response
from sales.api.serializers import UserSerializer, GroupSerializer, SaleSerializer
from sales.api.models import Sale
from django.http import JsonResponse
from django.conf import settings
from rest_framework.decorators import api_view

class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = User.objects.all().order_by('-date_joined')
    serializer_class = UserSerializer
    permission_classes = [permissions.AllowAny]


class GroupViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """
    queryset = Group.objects.all()
    serializer_class = GroupSerializer
    permission_classes = [permissions.AllowAny]

class SaleViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows sales to be viewed or edited.
    """
    queryset = Sale.objects.all().order_by('-customer_id')
    serializer_class = SaleSerializer
    permission_classes = [permissions.AllowAny]

    @api_view(['GET'])
    def sales_by_customer(request, customer_id):
        sales = Sale.objects.filter(customer_id=customer_id)
        serializer = SaleSerializer(sales, many=True, context={'request': request})
        return Response(serializer.data)

class SaleCheckout(views.APIView):
    def post(self, request, id):
        sale = Sale.objects.get(id=id)

        if sale.payment_status == 'paid':
            return JsonResponse({'error': 'Sale already paid'}, status=400)

        success_url = request.data.get('successUrl', '')
        cancel_url = request.data.get('cancelUrl', '')

        if not success_url or not cancel_url:
            return JsonResponse({'error': 'successUrl and cancelUrl are required'}, status=400)

        data = {
            "currency": os.environ.get('DEFAULT_CURRENCY'),
            "successUrl": success_url,
            "cancelUrl": cancel_url,
            "products": sale.products
        }

        response = requests.post(os.environ.get('PAYMENTS_API_URL')+'/pay', json=data)

        if response.status_code != 200:
            return JsonResponse({'error': 'Error in payments API'}, status=500)

        sale.payment_id = response.json()['session_id']
        sale.payment_status = response.json()['payment_status']
        sale.save()

        return JsonResponse({'success': True, 'payment_link': response.json()['payment_link']})
