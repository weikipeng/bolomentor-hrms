ContactInfoType = {
	MOBILE : 0,
	QQ : 1,
	COMPANY_TEL : 2,
	HOME_TEL : 3,
	COMPANY_EMAIL : 4,
	PRIVATE_EMAIL : 5,
	OTHER : 6
}

ContactInfoTypeName = ["手机", "QQ", "公司电话", "家庭电话", "公司邮箱", "私人邮箱", "其它"];

ContactType = {
	COMPANY : 0,
	HR : 1,
	PERSON : 2,
	USER : 3
}

function Contact() {
	this.id = -1;
	this.type = -1;
	this.typeId = -1;
	this.infoType = -1;
	this.info = "";

	this.initContact = function(data) {

		if (data.hasOwnProperty(MString.ID)) {
			this.id = data.id;
		}

		if (data.hasOwnProperty(MString.TYPE)) {
			this.type = data.type;
		}

		if (data.hasOwnProperty(MString.TYPEID)) {
			this.typeId = data.typeId;
		}

		if (data.hasOwnProperty(MString.INFOTYPE)) {
			this.infoType = data.infoType;
		}

		if (data.hasOwnProperty(MString.INFO)) {
			this.info = data.info;
		}
	}

	this.getContactInfoTypeLabel = function() {
		var cText = MString.UNKNOWN;
		if (this.infoType >= 0) {
			cText = ContactInfoTypeName[this.infoType];
		}

		return cText;
	}

	this.getUpdateJSON = function(nRow) {
		var updateJSON = {};
		var editContact = $(nRow).find("select,input");
		var nType = editContact.eq(0).val();
		var nInfo = editContact.eq(1).val();

		if (nType != this.infoType) {
			updateJSON[MString.INFOTYPE] = nType;
		}

		if (nInfo != this.info) {
			updateJSON[MString.INFO] = nInfo;
		}

		if (!jQuery.isEmptyObject(updateJSON)) {
			updateJSON[MString.ID] = this.id;
		}

		return updateJSON;
		//		console.log(editContact.eq(0).val()+"   联系方式---------"+editContact.eq(1).val());
	}
}

var ContactDao = {
	editArray:[],

	deleteArray:[],
	
	newCompanyTel : function(id, typeId, info) {
		var tel = new Contact();
		tel.id = id;
		tel.type = ContactType.COMPANY;
		tel.typeId = typeId;
		tel.infoType = ContactInfoType.COMPANY_TEL;
		tel.info = info;

		return tel;
	},
	
	newHrCompanyTel : function(id, typeId, info) {
		var tel = new Contact();
		tel.id = id;
		tel.type = ContactType.HR;
		tel.typeId = typeId;
		tel.infoType = ContactInfoType.COMPANY_TEL;
		tel.info = info;

		return tel;
	},
	
	newHrMobile : function(id, typeId, info) {
		var mobile = new Contact();
		mobile.id = id;
		mobile.type = ContactType.HR;
		mobile.typeId = typeId;
		mobile.infoType = ContactInfoType.MOBILE;
		mobile.info = info;
		return mobile;
	},
	
	newHrQQ : function(id, typeId, info) {
		var hrQQ = new Contact();
		hrQQ.id = id;
		hrQQ.type = ContactType.HR;
		hrQQ.typeId = typeId;
		hrQQ.infoType = ContactInfoType.QQ;
		hrQQ.info = info;
		return hrQQ;
	},
	
	newHrCompanyEmail : function(id, typeId, info) {
		var hrCompanyEmail = new Contact();
		hrCompanyEmail.id = id;
		hrCompanyEmail.type = ContactType.HR;
		hrCompanyEmail.typeId = typeId;
		hrCompanyEmail.infoType = ContactInfoType.COMPANY_EMAIL;
		hrCompanyEmail.info = info;
		return hrCompanyEmail;
	},
	
	deleteContactById : function(nRow, id) {
		var deleteUrl = MainUrl.DOMAIN + MainUrl.URL_CONTACT;
		var contactData = {};
		var postData = {};
		contactData[MString.ID] = id;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(contactData);
		postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
		postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);

		//			console.log("delete data ---> "+JSON.stringify(postData) +"    id  "+id);
		//			console.log("delete data ---> "+JSON.stringify(postData));

		$.post(deleteUrl, postData, function(data, status) {
			console.log("1--->" + JSON.stringify(data));
			if (data != null) {
				if (data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS) {
					nRow.remove();
				} else {
					alert(data[MainUrl.KEY_MESSAGE]);
				}
			} else {
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
	},

	getCompanyTel : function() {
		var telInfo = $.grep(this.contactList, function(item) {
			return item.infoType == ContactInfoType.COMPANY_TEL;
		});
		if (telInfo != null && telInfo.length > 0) {
			return telInfo[0].info;
			//注意返回的是数组
		}
		return "";
		//注意返回的是数组
	},

	getMobile : function() {
		var mobieInfo = $.grep(this.contactList, function(item) {
			return item.infoType == ContactInfoType.MOBILE;
		});
		if (mobieInfo != null && mobieInfo.length > 0) {
			return mobieInfo[0].info;
			//注意返回的是数组
		}
		return "";
		//注意返回的是数组
	},

	getQQ : function() {
		var qqInfo = $.grep(this.contactList, function(item) {
			return item.infoType == ContactInfoType.QQ;
		});
		if (qqInfo != null && qqInfo.length > 0) {
			return qqInfo[0].info;
			//注意返回的是数组
		}
		return "";
		//注意返回的是数组
	},

	getEmail : function() {
		var mailInfo = $.grep(this.contactList, function(item) {
			return item.infoType == ContactInfoType.COMPANY_EMAIL;
		});
		if (mailInfo != null && mailInfo.length > 0) {
			return mailInfo[0].info;
			//注意返回的是数组
		}
		return "";
		//注意返回的是数组
	},

	getContact : function(id) {
		var nContact = new Contact();

		var nContactList = $.grep(this.contactList, function(item) {
			return item.id == id;
		});
		if (nContactList.length > 0) {
			nContact = nContactList[0];
		}

		return nContact;
	},

	getShowContact : function() {
		var result = '';
		var mob = this.getMobile();
		if (mob != null && mob.length > 0) {
			result = MString.TEXT_MOBILE + MString.TEXT_MAOHAO + mob;
		} else {
			tel = this.getCompanyTel();
			if (tel != null && tel.length > 0) {
				result = MString.TEXT_COMPANY_TEL + MString.TEXT_MAOHAO + tel;
			} else {
				qq = this.getQQ();
				if (qq != null && qq.length > 0) {
					result = MString.TEXT_QQ + MString.TEXT_MAOHAO + qq;
				}
			}
		}
		return result;
	},

	getContactHtml : function() {
		var isEditing = (nowUser.role == 127) || (this.id == -1);
		var cHtml = '<ul class="unstyled">';
		var cList = this.contactList;
		if (cList != null && cList.length > 0) {
			var i = 0;
			for ( i = 0; i < cList.length; i++) {
				var nContact = cList[i];
				cHtml += '<li style="margin-top: 5px;">';
				if (isEditing) {
					cHtml += this.getEditContactHtml(nContact);
				}
				cHtml += '<span class="wiki-ul-Left">' + nContact.getContactInfoTypeLabel() + '</span>';
				//<i class="icon-img-up"></i>
				cHtml += '<span class="wiki-ul-Right">' + nContact.info + '</span>';
				cHtml += '</li>';
			}
		}
		cHtml += '</ul>';
		if (isEditing) {
			cHtml += '<a id="add" href="#" class="btn blue" style="display: block;margin-top: 5px;"><i class="icon-plus"></i> 添加</a>';
		}
		return cHtml;
	},
	
	getNewContactHtml: function(){
		var cHtml ='<li style="margin-top: 5px;">';
		var selectType = [
			'<select tabindex="-1" style="width: 95px;" class="wiki-ul-Left">',
			'<option value="-1">请选择...</option>'
		];
		var i=0;
		var typeList = ContactInfoTypeName;
		for(i = 0;i<typeList.length;i++){
			selectType.push('<option value="' + i + '">'+ ContactInfoTypeName[i] +'</option>');
		}
		
		selectType.push('</select>');
		
		cHtml += selectType.join("");
		cHtml +='<div class="wiki-ul-Right"><input	name="contactInfo" type="text" style="height: 28px !important;" disabled="disabled"/> <button id="deleteContact" href="-1" name="-1" class="btn red">删除</button></div>';
		cHtml +='<span class="wiki-ul-Left"></span><span class="wiki-ul-Right"></span></li>';
		return cHtml;
	},
	
	getEditContactHtml : function(nContact) {
		var cHtml = '';
		var selectType = ['<select tabindex="-1" style="width: 95px;" class="wiki-ul-Left">', '<option value="-1">请选择...</option>'];
		var i = 0;
		var typeList = ContactInfoTypeName;
		var selectHtml = ' selected="selected"';
		for ( i = 0; i < typeList.length; i++) {
			var emptyHtml = '';
			if (nContact.infoType == i) {
				emptyHtml = selectHtml;
			}
			selectType.push('<option ' + emptyHtml + ' value="' + i + '">' + ContactInfoTypeName[i] + '</option>');
		}

		selectType.push('</select>');

		cHtml += selectType.join("");
		cHtml += '<div class="wiki-ul-Right"><input	name="contactInfo" type="text" style="height: 28px !important;" value="' + nContact.info + '"/> <button id="deleteContact" name="' + nContact.id + '" class="btn red">删除</button></div>';
		cHtml += '';
		return cHtml;
	},
	
	addContactSelectListener:function(contactCol){
  		//当未选择联系方式的种类时，禁止输入
  		contactCol.find("select").change(function(){
  //			console.log("this -----------> "+this.value);
  			var inputObj = $(this).next().find("input");
  //			console.log("inputObj -----------> "+inputObj.val());
  			if(this.value < 0){
  				inputObj.attr("disabled",true);
  			}else{
  				inputObj.attr("disabled",false);
  			}
  		});
	},
	
	addContactDeleteListener:function(nTable){
		nTable.find('* #deleteContact').live('click',function(e){
			e.preventDefault();
			console.log("deleteContact -----------> ");
			var nRow = $(this).closest("li");
			var typeSpan = nRow.children("span:first");
			var valueSpan = nRow.children("span:last");
			
			var typeSelect = nRow.children("select");;
			var valueInput = $(this).siblings().filter("input");
			
			console.log("typeSpan -----------> "+typeSpan.text());
			console.log("typeSelect -----------> "+typeSelect.val());
			console.log("valueSpan -----------> "+valueSpan.text());
			console.log("valueInput -----------> "+valueInput.val());
			console.log("														");
			
			if(typeSelect.val() == -1 && typeSpan.text().length==0){
				nRow.remove();
				return;
			}
			
			if (confirm("是否删除这条联系方式？") == false) {
	            return;
	        }
			
			var cId = $(this).attr("name");
			if(cId == -1){
				nRow.remove();
			}else{
	  			ContactDao.deleteContactById(nRow,cId);
			}
		});
	}
};
