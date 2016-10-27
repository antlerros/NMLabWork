from __future__ import unicode_literals

from django.db import models

# Create your models here.

class User_Wait(models.Model):
		My_ID = models.CharField(max_length = 12)
		My_State = models.BooleanField()
		friend_ID = models.CharField(max_length =12)
		response_time = models.DateTimeField(auto_now_add = True)

