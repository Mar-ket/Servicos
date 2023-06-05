from django.urls import include, path
from rest_framework import routers
from sales.api import views

router = routers.DefaultRouter()

# There is no need to expose /users and /groups endpoint here
# router.register(r'users', views.UserViewSet)
# router.register(r'groups', views.GroupViewSet)

router.register(r'sales', views.SaleViewSet)

urlpatterns = [
    path('v1/', include(router.urls)),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
    path('v1/sales/checkout/<int:id>', views.SaleCheckout.as_view(), name='sale-checkout'),
    path('v1/sales/uid/<int:customer_id>/', views.SaleViewSet.sales_by_customer, name='sales-by-customer'),
]
