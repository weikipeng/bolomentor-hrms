function User(){
	this.id= -1;
	this.name = '';	
	this.nickName = '';
	this.avatar = '';
	this.role = -1;
	
//	this.password = '';
//	this.newPassword = '';
//	this.confirmPassword = '';
	
	this.initUser = function(data){		
		if(data == null) return;
		this.id = data.id;
		this.name = data.name;
		this.nickName = data.nickName;
		this.avatar = data.avatar;
		this.role = data.role;
	}
	
	this.getShowName = function(data){		
		var showName = '';		
		if(this.nickName!=null && this.nickName.length >0){
			showName = this.nickName;
		}else if(this.name!=null && this.name.length >0){
			showName = this.name;
		}
		return showName;
	}
	
	this.getJSONV = function(){
		data = {};		
		data[MString.ID] = this.id;
		data[MString.ROLE] = this.role;
		
		return data;
	}
}

function LoginForm(){
	this.username = $('[name="username"]');
	this.password = $('[name="password"]');
	
	this.getJSONV = function(){
		data = {};		
		data[this.username.attr('name')] = this.username.val();
		data[this.password.attr('name')] = this.password.val();
		
		return data;
	}
}

nowUser = new User();

userList = [];

UserDao = {
	setUserList: function(jsonList){
		userList = [];
		if(jsonList!=null && jsonList.length>0){
			var i= 0;
			for(i =0;i<jsonList.length;i++){
				var tUser = new User();
				tUser.initUser(jsonList[i]);
				userList.push(tUser);
			}
				
		}
	},
	
	getUserById:function(id){
		var user = new User();
		if(userList!=null && userList.length>0){
			var i= 0;
			for(i =0;i<userList.length;i++){
				if(userList[i].id == id){
					user = userList[i];
				}
			}
		}
		return user;
	},
	
	initNowUser: function(){
//			userData = JSON.parse($.cookie(MString.TABLE_USER));
		userData = JSON.parse(localStorage.getItem(MString.TABLE_USER));
//			console.log("userData ---> "+JSON.stringify(userData));
		nowUser.initUser(userData);
//			console.log("user ---> "+nowUser.id+" ------>"+nowUser.name);			
  			console.log("initNowUser--------------");			
    	
    	headUserName = $('#head_userName');
//      	headUserName.text(nowUser.name);
    	headUserName.text(nowUser.getShowName());
    	
	  	if(nowUser == null || nowUser.id <=0){
  	  		alert("请您先登录！");
  	  		window.location.href = "login.html";
	  		return;
	  	}
	},
	
	initLoginForm: function(){
//			usernameCookie = $.cookie(MString.USERNAME);
		var usernameCookie = localStorage.getItem(MString.USERNAME);
    	$('[name="username"]').val(usernameCookie);
    	console.log("usernameCookie --->"+usernameCookie);
//      	var isRemberCookie =  $.cookie(MString.ISREMEBERUSERNAME) != null;
    	var isRemberCookie =  localStorage.getItem(MString.ISREMEBERUSERNAME) == MString.TRUE;
    	console.log("isRemberCookie --->"+isRemberCookie);
    	console.log("localStorage.getItem(MString.ISREMEBERUSERNAME) ------>"+localStorage.getItem(MString.ISREMEBERUSERNAME));
    	$('[name="remember"]').prop('checked',isRemberCookie);
	},
	
	login: function (){
        isRember = $('[name="remember"]').attr('checked');
        console.log("isRember --->"+isRember);
        if(isRember){
            console.log("remember!!@ --->");
//              $.cookie(MString.USERNAME,$('[name="username"]').val());
//              $.cookie(MString.ISREMEBERUSERNAME,true);
            localStorage.setItem(MString.USERNAME,$('[name="username"]').val());
            localStorage.setItem(MString.ISREMEBERUSERNAME,true);
        }else{
            console.log("not remember --->");
//              $.cookie(MString.USERNAME,null);
//              $.cookie(MString.ISREMEBERUSERNAME,null);
			
            localStorage.removeItem(MString.USERNAME);
            localStorage.removeItem(MString.ISREMEBERUSERNAME);
        }
        
        nowLoginForm = new LoginForm();
        
        postUrl = MainUrl.DOMAIN+MainUrl.URL_LOGIN;
        postData= nowLoginForm.getJSONV();
        
        console.log("postData --->"+JSON.stringify(postData));
        
        $.post(postUrl,postData,function(data,status){
//          	console.log("status --->"+status);
//          	if(status == 200){
        	console.log("data --->"+JSON.stringify(data));
        	if(data.status ==404){
            	login_failed = $('#login_failed');
            	login_failed.show();
            	login_failed.fadeOut(2000);	
        	}else if(data.status == 200){
//          		nowUser.initUser(data);
//					$.cookie(MString.TABLE_USER,JSON.stringify(data.user));
				localStorage.setItem(MString.TABLE_USER,JSON.stringify(data.user));
        		window.location.href="company_index.html";
        	}
        		
//          	}else{
//          		
//          	}
        	
//          	setTimeout(function(){
//          		console.log("1500 --->"+1500);	
//					login_failed.hide();
//          	},1500);
        });		 	
	}
};