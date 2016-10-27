from django.shortcuts import render
from django.http import HttpResponse
from .models import chatData
from django.utils import timezone
import datetime
import json
import sys
sys.path.append('../')
from Wait.models import User_Wait
from django.views.decorators.csrf import csrf_exempt
# Create your views here.
@csrf_exempt
def chat(request):  
		data = json.loads(request.body)
		print data
		Sender_ID = data['sender']
		Receiver_ID = data['receiver']
		Content = data['content']
		Exit = data['exit']
		now = timezone.now()
	
		Sender = User_Wait.objects.get(My_ID = Sender_ID)
		Receiver = User_Wait.objects.filter(My_ID = Receiver_ID)
		Sender.response_time = now
		Sender.save()
		##if receiver is removed because of no response for a  long time
	#	Users = User_Wait.objects.all()
	#	for User in Users :
	#		if (now - User.response_time).total_seconds() > 5 :
	#				Deleted_User = User_Wait.objects.get(My_ID = User.My_ID)
	#				if Deleted_User.friend_ID != '':
	#					Chatting_ID = User_Wait.objects.get(My_ID = Deleted_User.friend_ID)
	#					Chatting_ID.My_State = True
	#					Chatting_ID.friend_ID =''
	#					Chatting_ID.save()
	#				Deleted_User.delete()

		if Sender.My_State == True:
				return HttpResponse(json.dumps({'Chatting':False,'Content':[],'ID':[]}))

		if not Receiver : 
				Sender.My_State = True
				Sender.friend_Id =''
				Sender.save()
				return HttpResponse(json.dumps({'Chatting':False,'Content':[],'ID':[]}))

		if Exit == True:
				Sender.My_State = True
				Sender.friend_Id =''
				Sender.save()
				Receiver = User_Wait.objects.get(My_ID = Receiver_ID)
				Receiver.My_State = True
				Receiver.friend_Id =''
				Receiver.save()
				return HttpResponse(json.dumps({'Chatting':False,'Content':[],'ID':[]}))

		if Content != '':
				_newContent = chatData()
				_newContent.Content = Content
				_newContent.Sender = Sender_ID
				_newContent.Receiver = Receiver_ID
				_newContent.save()

		contents_1 = [obj for obj in chatData.objects.filter(Sender = Sender_ID, Receiver = Receiver_ID)]
		contents_2 = [obj for obj in chatData.objects.filter(Sender = Receiver_ID, Receiver = Sender_ID)]
		contents = contents_1 + contents_2
#		print contents
		contents_sort = sorted(contents, key= lambda d: d.Sending_Time )
		final_contents = [obj.Content for obj in contents_sort]
		order = [obj.Sender for obj in contents_sort]
#		print "content: "
#		print final_contents
#		print order
		return HttpResponse(json.dumps({'Chatting':True,'Content':final_contents,'ID':order}))

	



				

				
