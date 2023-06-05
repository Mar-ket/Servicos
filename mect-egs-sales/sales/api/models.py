from django.db import models

class Sale(models.Model):
    PAYMENT_STATUS_CHOICES = [
        ('paid', 'PAID'),
        ('unpaid', 'UNPAID'),
        ('no_payment_required', 'NO PAYMENT REQUIRED'),
    ]

    customer_id = models.CharField(max_length=255)
    products = models.JSONField()
    payment_id = models.CharField(max_length=255, null=True, blank=True)
    payment_status = models.CharField(max_length=20, choices=PAYMENT_STATUS_CHOICES, default='unpaid')
    amount = models.DecimalField(max_digits=12, decimal_places=2)