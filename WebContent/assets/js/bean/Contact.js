ContactInfoType = {
	MOBILE:0,
	QQ:1,
	COMPANY_TEL:2,
	HOME_TEL:3,
	COMPANY_EMAIL:4,
	PRIVATE_EMAIL:5,
	OTHER:6
}

ContactInfoTypeName = ["手机","QQ","公司电话","家庭电话","公司邮箱","私人邮箱","其它"];

ContactType = {
	COMPANY:0,
	HR:1,
	PERSON:2,
	USER:3
}

function Contact(){
	this.id= -1;
	this.type = -1;
	this.typeId = -1;
	this.infoType = -1;
	this.info="";
	
	this.initContact = function(data){
		
  		if(data.hasOwnProperty(MString.ID)){
  			this.id = data.id;
  		}

		if(data.hasOwnProperty(MString.TYPE)){
  			this.type = data.type;
  		}
  		
  		if(data.hasOwnProperty(MString.TYPEID)){
  			this.typeId = data.typeId;
  		}
  		
  		if(data.hasOwnProperty(MString.INFOTYPE)){
  			this.infoType = data.infoType;
  		}
  		
  		if(data.hasOwnProperty(MString.INFO)){
  			this.info = data.info;
  		}
	}
	
	this.getContactInfoTypeLabel = function(){
		var cText = MString.UNKNOWN;
		if(this.infoType >= 0){
			cText = ContactInfoTypeName[this.infoType];
		}
		
		return cText;
	}
	
	this.getUpdateJSON = function(nRow){
		var updateJSON = {};
		var editContact = $(nRow).find("select,input");
		var nType = editContact.eq(0).val();
		var nInfo = editContact.eq(1).val();
		
		if(nType!=this.infoType){
			updateJSON[MString.INFOTYPE] = nType;
		}
		
		if(nInfo!=this.info){
			updateJSON[MString.INFO] = nInfo;
		}
		
		if(!jQuery.isEmptyObject(updateJSON)){
			updateJSON[MString.ID] = this.id;
		}
		
		return updateJSON;
//		console.log(editContact.eq(0).val()+"   联系方式---------"+editContact.eq(1).val());
	}
}

var ContactDao = function(){
	return{
		newCompanyTel:function(id,typeId,info){
			var tel = new Contact();
			tel.id = id;
			tel.type = ContactType.COMPANY;
			tel.typeId = typeId;
			tel.infoType = ContactInfoType.COMPANY_TEL;
			tel.info = info;
			
			return tel;
		},
		newHrCompanyTel:function(id,typeId,info){
			var tel = new Contact();
			tel.id = id;
			tel.type = ContactType.HR;
			tel.typeId = typeId;
			tel.infoType = ContactInfoType.COMPANY_TEL;
			tel.info = info;
			
			return tel;
		},
		newHrMobile:function(id,typeId,info){
			var mobile = new Contact();
			mobile.id = id;
			mobile.type = ContactType.HR;
			mobile.typeId = typeId;
			mobile.infoType = ContactInfoType.MOBILE;
			mobile.info = info;
			return mobile;
		},
		newHrQQ:function(id,typeId,info){
			var hrQQ = new Contact();
			hrQQ.id = id;
			hrQQ.type = ContactType.HR;
			hrQQ.typeId = typeId;
			hrQQ.infoType = ContactInfoType.QQ;
			hrQQ.info = info;
			return hrQQ;
		},
		newHrCompanyEmail:function(id,typeId,info){
			var hrCompanyEmail = new Contact();
			hrCompanyEmail.id = id;
			hrCompanyEmail.type = ContactType.HR;
			hrCompanyEmail.typeId = typeId;
			hrCompanyEmail.infoType = ContactInfoType.COMPANY_EMAIL;
			hrCompanyEmail.info = info;
			return hrCompanyEmail;
		},
		deleteContactById:function(nRow,id){
			var deleteUrl = MainUrl.DOMAIN + MainUrl.URL_CONTACT;
			var contactData = {};
			var postData = {};
			contactData[MString.ID] = id;
			postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(contactData);
			postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
			postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
			
//			console.log("delete data ---> "+JSON.stringify(postData) +"    id  "+id);
//			console.log("delete data ---> "+JSON.stringify(postData));
			
  			$.post(deleteUrl,postData,function(data,status){
  				console.log("1--->"+JSON.stringify(data));
  				if(data!=null){
  					if(data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS){
		            	nRow.remove();		        		
  					}else{
						alert(data[MainUrl.KEY_MESSAGE]);  						
  					}
  				}else{
  					alert("连接服务器失败！");
  				}
  			});
			
			
//			  			var company = {};
//			company[MString.ID] = id;
//			var companyData = {};
//			companyData[MString.TABLE_COMPANY] = company;
//			var url = MainUrl.DOMAIN + MainUrl.URL_COMPANY;
//			var postData = {};
//			postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
//			postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
//			postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(companyData);
		}
	};
}();