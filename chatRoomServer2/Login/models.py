from __future__ import unicode_literals

from django.db import models

# Create your models here.
class User(models.Model):
		User_ID = models.CharField(max_length = 12)
		User_Password = models.CharField(max_length = 12)
