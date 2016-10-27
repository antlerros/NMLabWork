# -*- coding: utf-8 -*-
# Generated by Django 1.10.1 on 2016-10-17 14:50
from __future__ import unicode_literals

from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
        ('Wait', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='user_wait',
            name='time',
        ),
        migrations.AddField(
            model_name='user_wait',
            name='response_time',
            field=models.DateTimeField(auto_now_add=True, default=django.utils.timezone.now),
            preserve_default=False,
        ),
    ]
