from django.shortcuts import render
from django.http import HttpResponse
from .models import User
import json
from django.views.decorators.csrf import csrf_exempt

# Create your views here.

@csrf_exempt
def get(request):
		inputID = json.loads(request.body)['userID']
		inputPassword = json.loads(request.body)['password']
		print inputID, inputPassword
		searchUser = User.objects.filter(User_ID=inputID)
		if searchUser:
				correctPassword = User.objects.filter(User_ID = inputID, User_Password = inputPassword)
				if correctPassword:
						return HttpResponse('Right')
				else :
						return HttpResponse('Wrong')

		else :
				_newUser = User()
				_newUser.User_ID = inputID
				_newUser.User_Password = inputPassword
				_newUser.save()
				return HttpResponse('New')

	
	

