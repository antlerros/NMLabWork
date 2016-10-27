from django.shortcuts import render
from django.http import HttpResponse
from .models import User_Wait
import json
import datetime
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt


# Create your views here.
@csrf_exempt 
def wait(request):
		data = json.loads(request.body)
		print data
		now = timezone.now()
		Exist_User = User_Wait.objects.filter(My_ID = data['ID'])
		Called_User = User_Wait.objects.filter(My_ID = data['Call_ID'])

					
		if Exist_User:
				user = User_Wait.objects.get(My_ID = data['ID'])
				user.response_time = now
				user.save()
		##Delete User not response
		Users = User_Wait.objects.all()
		for User in Users :
			if (now - User.response_time).total_seconds() > 8 :
					Deleted_User = User_Wait.objects.get(My_ID = User.My_ID)
					if Deleted_User.friend_ID != '':
						Chatting_ID = User_Wait.objects.get(My_ID = Deleted_User.friend_ID)
						Chatting_ID.My_State = True
						Chatting_ID.friend_ID =''
						Chatting_ID.save()
					Deleted_User.delete()
		## User not exist
		if not Exist_User : 
			_newUser = User_Wait()
			_newUser.My_ID = data['ID']
			_newUser.My_State = True 
			_newUser.friend_ID =''
			_newUser.response_time = now
			_newUser.save()

			Online =[ obj.My_ID  for obj in User_Wait.objects.filter(My_State = True).exclude(My_ID = data['ID'])]
			return HttpResponse(json.dumps({'OnlineList':Online,'Chatting':False,'Connection':True,'Friend_ID':''}), content_type='application/json')
		Exist_User = User_Wait.objects.get(My_ID = data['ID'])
	## Exit 
		if data['Exit'] == True:
			Exist_User.delete()
			return HttpResponse(json.dumps({'OnlineList':[],'Chatting':False,'Connection':False,'Friend_ID':''}), content_type='application/json')
	## not invite anyone
		if not Called_User:
			## being called
			if Exist_User.My_State == False:

					return HttpResponse(json.dumps({'OnlineList':[],'Chatting':True,'Connection':True,'Friend_ID':Exist_User.friend_ID}), content_type='application/json')

			##not being called
			else:
				Exist_User.response_time = now
				Exist_User.save()
				Online =[ obj.My_ID  for obj in User_Wait.objects.filter(My_State = True).exclude(My_ID = data['ID'])]
				return HttpResponse(json.dumps({'OnlineList':Online,'Chatting':False,'Connection':True,'Friend_ID':''}), content_type='application/json')
	## if invite 
		else : 
			Called_User = User_Wait.objects.get(My_ID = data['Call_ID'])
			if Called_User.My_State == True :
				Called_User.My_State = False
				Called_User.friend_ID = Exist_User.My_ID
				Called_User.save()
				Exist_User.My_State = False
				Exist_User.friend_ID = Called_User.My_ID
				Exist_User.save()
				return HttpResponse(json.dumps({'OnlineList':[],'Chatting':True,'Connection':True,'Friend_ID':Called_User.My_ID}), content_type='application/json')
			else :
				Online =[ obj.My_ID  for obj in User_Wait.objects.filter(My_State = True)]
				return HttpResponse(json.dumps({'OnlineList':Online,'Chatting':False,'Connection':True,'Friend_ID':''}), content_type='application/json')



							


		
		
