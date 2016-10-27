from __future__ import unicode_literals

from django.db import models

# Create your models here.
class chatData(models.Model):
		Sender = models.CharField(max_length = 12)
		Receiver = models.CharField(max_length = 12)
		Content = models.CharField(max_length = 50)
		Sending_Time = models.DateTimeField(auto_now_add = True)
