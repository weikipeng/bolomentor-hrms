function Person(){
	this.id= -1;
	this.name = '';
	this.EnglishName='';
	this.birthday='';
	this.gender='';
	this.age='';
	
	this.education=-1;
	this.maritalstatus='';
	this.workYear='';
	this.liveAddress='';
	this.belongAddress='';
	this.EnglishLevel='';
	this.otherLanguage='';
	this.salary='';
	
	this.personNature='';
	this.workStatus='';
	this.hopeAddress='';	
	this.hopeJob='';
	this.hopeVocation='';
	this.hopeSalary='';
	this.isHope='';
	this.vitae='';
	this.createUserId='';
	this.createUser='';
	this.createDate='';
	this.updateUserId='';
	this.updateUser='';
	this.updateDate='';
	
//	姓名，性别，期望地点，学历，状态（在职，离职），工作年限，人才代码。然后下面是简历 
	
	
	this.initPerson = function(data){
		
		if(data.hasOwnProperty(MString.ID)){
			this.id = data.id;
		}
		
		if(data.hasOwnProperty(MString.NAME)){
			this.name = data.name;
		}
		
		if(data.hasOwnProperty(MString.ENGLISHNAME)){
			this.EnglishName = data.EnglishName;
		}
		
		if(data.hasOwnProperty(MString.BIRTHDAY)){
			this.birthday = data[MString.BIRTHDAY];
		}
		
		if(data.hasOwnProperty(MString.GENDER)){
			this.gender = data[MString.GENDER];
		}
		
		if(data.hasOwnProperty(MString.AGE)){
			this.age = data[MString.AGE];
		}
		
		if(data.hasOwnProperty(MString.EDUCATION)){
			this.education = data[MString.EDUCATION];
		}
		
		if(data.hasOwnProperty(MString.MARITALSTATUS)){
			this.maritalstatus = data[MString.MARITALSTATUS];
		}
		
		if(data.hasOwnProperty(MString.WORKYEAR)){
			this.workYear = data[MString.WORKYEAR];
		}
		
		if(data.hasOwnProperty(MString.LIVEADDRESS)){
			this.liveAddress = data[MString.LIVEADDRESS];
		}
		
		if(data.hasOwnProperty(MString.BELONGADDRESS)){
			this.belongAddress = data[MString.BELONGADDRESS];
		}
		
		if(data.hasOwnProperty(MString.ENGLISHLEVEL)){
			this.EnglishLevel = data[MString.ENGLISHLEVEL];
		}
		
		if(data.hasOwnProperty(MString.OTHERLANGUAGE)){
			this.otherLanguage = data[MString.OTHERLANGUAGE];
		}
		
		if(data.hasOwnProperty(MString.SALARY)){
			this.salary = data[MString.SALARY];
		}
		//------------------------------------------
		
		if(data.hasOwnProperty(MString.HOPEADDRESS)){
			this.hopeAddress = data[MString.HOPEADDRESS];
		}
		
		
		if(data.hasOwnProperty(MString.WORKSTATUS)){
			this.workStatus = data[MString.WORKSTATUS];
		}
		
		if(data.hasOwnProperty(MString.WORKYEAR)){
			this.workYear = data[MString.WORKYEAR];
		}
		
		if(data.hasOwnProperty(MString.CREATEUSERID)){
			this.createUserId = data[MString.CREATEUSERID];
		}
		
		if(data.hasOwnProperty(MString.CREATEDATE)){
			this.createDate = data[MString.CREATEDATE];
		}
		
		if(data.hasOwnProperty(MString.UPDATEUSERID)){
			this.updateUserId = data[MString.UPDATEUSERID];
		}
		
		if(data.hasOwnProperty(MString.UPDATEDATE)){
			this.updateDate = data[MString.UPDATEDATE];
		}
		
//		if(data.hasOwnProperty(MString.CONTACT)){
//			contactArray = data.contact;
//			
//			telInfo = $.grep(contactArray,function(item){
////				console.log("                                       ");
////				console.log("item.infoType----->"+item.infoType);
////				console.log("ContactInfoType.Person_TEL----->"+ContactInfoType.Person_TEL);
//				return item.infoType == ContactInfoType.Person_TEL;
//			});
//			this.telephone = telInfo[0].info;//注意返回的是数组
//			
//		}
	}
	
	this.getUpdateJSON = function(nowP){
		console.log("getUpdateJSON ---------- ");
		var data = {};
		var nameV = nowP.name.val();
		if(nameV != this.name){
			data[MString.NAME] = nameV;
		}
		
		var EnglishNameV = nowP.EnglishName.val();
		if(EnglishNameV!=this.EnglishName){
			data[MString.ENGLISHNAME] = EnglishNameV;
		}
		
		var birthdayWidget = nowP.birthday.find("#birthdate");
//		var birthdayV = nowP.birthday.val();
		var birthdayV = birthdayWidget.val();
		console.log("birthdayV ---------- " + birthdayV);
		if(birthdayV!=this.birthday){
			data[MString.BIRTHDAY] = birthdayV;
		}
		
		var genderV = nowP.gender.filter(":checked").val();
		if(genderV != this.gender){
			data[MString.GENDER] = genderV;
		}
		
		var ageV = nowP.age.val();
		if(ageV != this.age){
			data[MString.AGE] = ageV;
		}

		var educationV = nowP.education.val();
		if(educationV != this.education){
			data[MString.EDUCATION] = educationV;
		}
		
		var maritalstatusV = nowP.maritalstatus.val();
		if(maritalstatusV!=this.maritalstatus){
			data[MString.MARITALSTATUS] = maritalstatusV;
		}
		
		var workYearV = nowP.workYear.val();
		if(workYearV != this.workYear){
			data[MString.WORKYEAR] = workYearV;
		}
		
		var liveAddressV = nowP.liveAddress.val();
		if(liveAddressV != this.liveAddress){
			data[MString.LIVEADDRESS] = liveAddressV;
		}
		
		var belongAddressV = nowP.belongAddress.val();
		if(belongAddressV != this.belongAddress){
			data[MString.BELONGADDRESS] = belongAddressV;
		}
		
//		var EnglishLevelV = nowP.EnglishLevel.val();
//		if(EnglishLevelV!=this.EnglishLevel){
//			data[MString.EnglishLevel] = EnglishLevelV;
//		}
		
		var hopeAddressV = nowP.hopeAddress.val();
		if(hopeAddressV != this.hopeAddress){
			data[MString.HOPEADDRESS] = hopeAddressV;
		}
		
		
		var workStatusV = nowP.workStatus.filter(":checked").val();
		if(workStatusV != this.workStatus){
			data[MString.WORKSTATUS] = workStatusV;
		}
		
		
		var vitaeV = nowP.vitae.val();
		console.log("vitaeV ---------- "+vitaeV);
		console.log("vitaeV2 ---------- "+nowP.vitae.val());
		if(vitaeV != this.vitae){
			data[MString.VITAE] = vitaeV;
		}
		
//		var EnglishNameV = nowP.EnglishName.val(); 
//		if(EnglishNameV != this.EnglishName){
//			data[MString.ENGLISHNAME] = EnglishNameV;
//		}

////		var vitaeV = nowP.vitae.val(); 
//		var vitaeV = CKEDITOR.instances['vitae'].getData();
//		console.log("vitaeV ---------- "+vitaeV);
//		if(vitaeV != this.vitae){
//			data[MString.VITAE] = vitaeV;
//		}
		
//		var natureV = nowP.nature.val(); 
//		if(natureV != this.nature){
//			data[MString.NATURE] = natureV;
//		}
//		
//		var numberV = nowP.number.val(); 
//		if(numberV != this.number){
//	  		data[MString.NUMBER] = numberV;
//		}
//		
//		var intentV = nowP.intent.val(); 
//		if(intentV != this.intent){
//	  		data[MString.INTENT] = intentV;
//		}
//
//		var addressV = nowP.address.val();
//		if(addressV!=this.address){
//			data[MString.ADDRESS] = addressV;
//		}
//		
//		var headhunterV = nowP.headhunter.filter(":checked").val();
//		if(headhunterV!=this.headhunter){
//			data[MString.HEADHUNTER] = headhunterV;
//		}
//		
//		var isListingV = nowP.isListing.filter(":checked").val();
//		if(isListingV!=this.isListing){
//			data[MString.ISLISTING] = isListingV;
//		}
//		
//		var introductionV = nowP.introduction.val();
//		if(introductionV!=this.introduction){
//			data[MString.INTRODUCTION] = introductionV;
//		}
//		//电话
//		var telephoneV = nowP.telephone.val();
//		if(telephoneV!=this.telephone){
//	  		var telInfo = $.grep(this.contactList,function(item){
//				return item.infoType == ContactInfoType.PERSON_TEL;
//			});
//			if(telInfo.length>0){
//	  			telInfo[0].info = telephoneV;
//		  		data[MString.TABLE_CONTACT] = telInfo;
//			}
//		}
  		
  		return data;
	}
	
	this.getShowName = function(){
		var showName = '';
		if(this.name!=null && this.name.length >0){
			showName = this.name;
		}else if(this.EnglishName!=null && this.EnglishName.length >0){
			showName = this.EnglishName;
		}
		return showName;
	}
	
}

function PersonEditForm(){
	//id,name,gender,hopeAddress,education,workStatus,workYear,vitae
	this.name = $('#name');
	this.EnglishName = $('#EnglishName');
	this.birthday =$('#birthday');
	this.gender = $('[name="gender"]');
	this.age = $('#age');

	this.education = $('#education');
	this.maritalstatus = $('#maritalstatus');
	this.workYear =  $('#workYear');
	
	this.liveAddress = $('#liveAddress');
	this.belongAddress = $('#belongAddress');
	
	this.vitae = $('#vitae');
	this.hopeAddress = $('#hopeAddress');
	this.workStatus = $('[name="workStatus"]');
//	this.nature = $('[name="nature"]');
//	this.number = $('[name="number"]');
//	this.intent= $('[name="intent"]');
//	this.headhunter = $('[name="headhunter"]');
//	this.isListing = $('[name="isListing"]');
//	this.telephone = $('[name="telephone"]');
//	this.address = $('[name="address"]');
//	this.introduction = $('[name="introduction"]');

	this.nameLabel = "";
	this.EnglishNameLabel = "";
	this.birthdayLabel = "";
	this.genderLabel = "";
	this.ageLabel = "";
	this.educationLabel = "";
	
	this.maritalstatusLabel = "";
	this.workYearLabel = "";
	this.liveAddressLabel = "";
	this.belongAddressLabel = "";
	
	this.vitaeLabel = "";
	
	this.hopeAddressLabel = "";
	this.workStatusLabel = "";
	
	this.initAddMode = function(){
      	jQuery.validator.addMethod("isMobile", function(value, element) {
      		var telTest = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/
      		var result = this.optional(element) || (telTest.test(value));
      		console.log("result ---> "+result);
		    return this.optional(element) || (telTest.test(value));
  		}, "请输入正确的手机或者电话号码！");
  		
      	jQuery.validator.addMethod("nameOrEnghlisName", function(value, element) {
      		var name = $('#name').val();
      		var EnglishName = $('#EnglishName').val();
      		
      		if(name.length>0 || EnglishName.length > 0){
      			return true;
      		}else{
      			return false;
      		}
		}, "请输入人才名称或英文名！");
  		
      	jQuery.validator.addMethod("singleName", function(value, element) {
      		var isSuccess = true;
  			var name = $.trim($('input[name="name"]').val());
  			var oldName = $.trim($('[name="nameLabel"]').text());
    		if(name!=null && name.length > 0 && oldName != name){
  				var personData = {};
  				personData[MString.NAME] = name;
  				
  				var turl = MainUrl.DOMAIN + MainUrl.URL_PERSON;
  				var tdata = {};
  				tdata[MainUrl.ACTION] = MainUrl.ACTION_SINGLE;
  				tdata[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
  				tdata[MainUrl.ACTION_DATA] = MStringF.getPostJson(personData);
  				
  				$.ajax({
  					type:"POST",
  					url:turl,
  					data:tdata,
  					async: false, 
            		success: 
                		function(data,status) {
							if(data!=null){
		  						var rStatus = data.status;
		  						rStatus = parseInt(rStatus);
		  						if(rStatus == MainUrl.RESULT_CODE_FAILED){
		  							alert("该人才已存在！");
		  							isSuccess=false;
		  						}else{
		  							isSuccess=true;
		  						}
	  						}
                		}
  				});
  			}
			return isSuccess;
  		}, "该人才已存在！");
  		
    	var personForm = $('#person_form');
    	
    	var error1 = $('.alert-error', personForm);
    	
    	personForm.validate({
    		errorElement: 'span', //default input error message container
    		errorClass: 'help-inline', // default input error message class
    		focusInvalid: false, // do not focus the last invalid input
    		ignore: "",
    		focusCleanup:true,
    		onfocusout: function(element) {
        		$(element).valid();
    		},
            
            messages: {
                name: {
                	nameOREnghlisName: "请输入人才名称或英文名！",
                    required: "请输入人才名称！",
                    singleName:"该人才已存在！"
                },
                
                workStatus: {
                    required: "请选择人才工作状态！",
                },
                
                gender: {
                    required: "请选择人才性别！",
                },
				EnglishName: {
					nameOREnghlisName: "请输入人才名称或英文名！",
					required:"请输入人才名称或英文名！"
				}
            },
    		
            errorPlacement:function(error,element){
                if(element.attr("name") == "gender"){
                    error.addClass("no-left-padding").insertAfter("#person_form_gender_error");
                }else if(element.attr("name") == "workStatus"){
                    error.addClass("no-left-padding").insertAfter("#person_form_workStatus_error");
                }
                else if(element.attr("name") == "name"){
                	console.log("errorPlacement -------------------- name");
    				error.insertAfter(element);
                }else if(element.attr("name") == "EnglishName"){
                	console.log("errorPlacement -------------------- EnglishName");
    				error.insertAfter(element);
                }
                else{
					error.insertAfter(element);
				}
            },
            
    		invalidHandler: function (event, validator) { //display error alert on form submit                  			
    			error1.show();
    			App.scrollTo(error1, -200);
    		},
    		
    		highlight: function (element) { // hightlight error inputs
    			if($(element).attr("name") == "name"){
    				console.log("highlight -------------------- 3");
                }else if($(element).attr("name") == "EnglishName"){
                	console.log("highlight -------------------- 4");
                }
    			
    			
    			$(element)
    			.closest('.help-inline').removeClass('ok'); // display OK icon
    			$(element)
    			.closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
    		},
    		
    		unhighlight: function (element) { // revert the change dony by hightlight
    			if($(element).attr("name") == "name"){
                	console.log("unhighlight -------------------- 1");
                }else if($(element).attr("name") == "EnglishName"){
                	console.log("unhighlight -------------------- 2");
                }
    			
    			$(element)
    			.closest('.control-group').removeClass('error'); // set error class to the control group
    		},
    		
    		success: function (label) {
  				if(label.attr("name") == "name"){
                	console.log("success -------------------- 1");
                }else if(label.attr("name") == "EnglishName"){
                	console.log("success -------------------- 2");
                }
                
    			label
    			.addClass('valid').addClass('help-inline ok') // mark the current input as valid and display OK icon
    			.closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
    		},
    		
    		submitHandler: function (form) {    			
    			error1.hide();

  				var postData = {};
  				postData[MainUrl.ACTION] = App.wiki_action;
  				postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser.getJSONV());
  				var nowForm = new PersonEditForm();
  				
  				var formData =  nowForm.getJSONV();
  				
				if(jQuery.isEmptyObject(formData)){
//					window.self.close();
					return true;
				}
  				
  				
  				var url = MainUrl.DOMAIN+MainUrl.URL_PERSON;
  				
  				postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(formData);
  				
    			console.log("add person-->"+JSON.stringify(postData));
  				
				$.post(url,postData,function(data,status){
					if(data!=null){
						var status = data[MainUrl.STATUS];
						status = parseInt(status);
						if(status == MainUrl.RESULT_CODE_SUCCESS){
							if(nowPerson.id > 0){
								alert("修改人才信息成功！");
	  								window.location.href="person_edit.html?id="+nowPerson.id;
							}else{
	  							alert("添加人才信息成功！");
	  							if (confirm("是否继续添加人才？") == true) {
									window.location.href="person_edit.html";
					                return;
					            }
	  							window.self.close();
							}
						}else{
							alert(data[MainUrl.KEY_MESSAGE]);
							window.self.close();
						}
					}else{
						alert("更新人才信息失败！");
						window.self.close();
					}
				});
  				return false;
			}
    	});
	}
	
	this.initEditMode = function(){
//		var editButtonHtml = [	  			
//			'<div class="tools">',
//				'<button id="edit_person" class="btn green">',
//				'编辑 <i class="icon-edit"></i>',
//				'</button>',
//			'</div>'
//	  	].join("");
//	  	
//	  	var layout = $("#personInfoTitleLayout");
//	  	layout.append(editButtonHtml);
  	  	var editButton = $('#edit_person');
  	  	$("#cancel").text("返回"); 
		$("#ok").hide(); 
  	  	
//	  	if(nowUser.role == 127){
  		editButton.show();
//	  	}
  	  	
  	  	editButton.live('click',function(e){
  	  		e.preventDefault();
  	  		var personForm = $('#person_form');
  	  		var controls = personForm.find(".controls");
  	  		this.isEditMode = !this.isEditMode;
  	  		if(this.isEditMode){
  		  		editButton.html('取消<i class="icon-remove"></i>');
  		  		editButton.removeClass("green");
  		  		editButton.addClass("red");
  		  		
  		  		controls.children().show();
  		  		controls.find("span:last").hide();
  		  		$('#vitae').prop("readonly",false);
  		  		$("#ok").show();
//		  		$("#vitae").show();
//		  		$("#vitae").next().hide();
  		  		PersonDao.setEditMode();
  	  		}else{
  		  		editButton.html('编辑 <i class="icon-edit"></i>');
  		  		editButton.removeClass("red");
  		  		editButton.addClass("green");
  		  		
  		  		controls.children().hide();
  		  		controls.find("span:last").show();
  		  		$('#vitae').prop("readonly",true);
  		  		$("#ok").hide();
//		  		$("#vitae").hide();
//		  		$("#vitae").next().show();
  		  		PersonDao.setViewMode();
  	  		}
  	  	});
  		
  		$('.ok').text("更新");
  		
  		this.initAddMode();
  		PersonDao.setViewMode();
	}
	
	this.setPerson = function(person){
//		//id,name,gender,hopeAddress,education,workStatus,workYear,vitae
//		this.name.val(Person.name);
//		
//		var tGender = this.gender.filter('[value='+person.gender+']');
//		tGender.attr("checked",true);
//		tGender.closest('span').addClass('checked');
//		
//		this.hopeAddress.val(Person.hopeAddress);
//		this.education.val(Person.education);
//		
//		var tWorkStatus = this.workStatus.filter('[value='+person.workStatus+']');
//		tWorkStatus.attr("checked",true);
//		tWorkStatus.closest('span').addClass('checked');
//		
//		this.workYear.val(Person.workYear);
//		
//		
////		this.EnglishName.val(person.EnglishName);
////		this.nature.val(person.nature);
////		this.number.val(person.number);
////		this.intent.val(person.intent);
////		tHeadhunter = this.headhunter.filter('[value='+person.headhunter+']');
////		tHeadhunter.attr("checked",true);
////		tHeadhunter.closest('span').addClass('checked');
////		
////		tIsListing = this.isListing.filter('[value='+person.isListing+']');
////		tIsListing.attr("checked",true);
////		tIsListing.closest('span').addClass('checked');
////		tIsListing.closest('div').addClass('focus');
////		
////		
////		this.telephone.val(person.telephone);		
////		this.address.val(person.address);
////		this.introduction.val(person.introduction);

		//id,name,gender,hopeAddress,education,workStatus,workYear,vitae
		this.name.val(person.name);
		this.EnglishName.val(person.EnglishName);
		
		var tBirthday ="";
		if(person.birthday!=null && person.birthday.length>0){
			tBirthday = $.format.date(person.birthday,"yyyy-MM-dd");
		}
		
		console.log("tBirthDay ---> "+tBirthday);
		
//		this.birthday.val(person.birthday);
		this.birthday.empty();
		this.birthday.birthdaypicker({
			dateFormat : "bigEndian",
			wraper:"fieldset",
			futureDates : false,
			defaultDate : tBirthday,
			maxAge : 70,
			minAge:10
		});
		
  		var tGender = this.gender.filter('[value='+person.gender+']');
  		tGender.attr("checked",true);
  		tGender.closest('span').addClass('checked');
  		
  		//年龄
  		this.age.val(person.age);
  		
  		var tMaritalstatus = this.maritalstatus.filter('[value='+person.maritalstatus+']');
  		tMaritalstatus.attr("checked",true);
  		tMaritalstatus.closest('span').addClass('checked');
  		
  		this.liveAddress.val(person.liveAddress);
  		this.belongAddress.val(person.belongAddress);
  		
  		this.hopeAddress.val(person.hopeAddress);
  		this.education.val(person.education);
  		
  		var tWorkStatus = this.workStatus.filter('[value='+person.workStatus+']');
  		tWorkStatus.attr("checked",true);
  		tWorkStatus.closest('span').addClass('checked');
  		
  		this.workYear.val(person.workYear);
		this.vitae.val(person.vitae);
		this.vitae.prop("readonly",true);
		
		//Label
		this.nameLabel = MStringF.createLabel(this.name,MStringF.getLabelName(MString.NAME),person.name);
		this.EnglishNameLabel = MStringF.createLabel(this.EnglishName,MStringF.getLabelName(MString.ENGLISHNAME),person.EnglishName);
		this.birthdayLabel = MStringF.createLabel(this.birthday,MStringF.getLabelName(MString.BIRTHDAY),person.birthday);
		this.genderLabel = MStringF.createLabel(this.gender,MStringF.getLabelName(MString.GENDER),MStringF.getGender(person.gender));
		this.ageLabel = MStringF.createLabel(this.age,MStringF.getLabelName(MString.AGE),person.age);
		this.maritalstatusLabel = MStringF.createLabel(this.maritalstatus,MStringF.getLabelName(MString.MARITALSTATUS),MStringF.getMaritalstatus(person.maritalstatus));
		this.liveAddressLabel = MStringF.createLabel(this.liveAddress,MStringF.getLabelName(MString.LIVEADDRESS),person.liveAddress);
		this.belongAddressLabel = MStringF.createLabel(this.belongAddress,MStringF.getLabelName(MString.BELONGADDRESS),person.belongAddress);
		this.educationLabel = MStringF.createLabel(this.education,MStringF.getLabelName(MString.EDUCATION),MStringF.getEducation(person.education));
		
		this.hopeAddressLabel = MStringF.createLabel(this.hopeAddress,MStringF.getLabelName(MString.HOPEADDRESS),person.hopeAddress);
		this.workStatusLabel = MStringF.createLabel(this.workStatus,MStringF.getLabelName(MString.WORKSTATUS),MStringF.isWorking(person.workStatus));
		this.workYearLabel = MStringF.createLabel(this.workYear,MStringF.getLabelName(MString.WORKYEAR),person.workYear);
  		this.vitaeLabel = MStringF.createLabel(this.vitae,MStringF.getLabelName(MString.VITAE),person.vitae);
		
		
		
  		PersonDao.initPersonCreateInfo(person);
		
//		this.EnglishNameLabel = MStringF.createLabel(this.EnglishName,MStringF.getLabelName(MString.ENGLISHNAME),person.EnglishName);
//		this.natureLabel = MStringF.createLabel(this.nature,MStringF.getLabelName(MString.NATURE),MStringF.getPersonNature(person.nature));
//		this.numberLabel = MStringF.createLabel(this.number,MStringF.getLabelName(MString.NUMBER),MStringF.getPersonNumber(person.number));
//		this.intentLabel = MStringF.createLabel(this.intent,MStringF.getLabelName(MString.INTENT),MStringF.getIntent(person.intent));
//		this.headhunterLabel = MStringF.createLabel(this.headhunter,MStringF.getLabelName(MString.HEADHUNTER),MStringF.getYesNo(person.headhunter));
//		this.isListingLabel = MStringF.createLabel(this.isListing,MStringF.getLabelName(MString.ISLISTING),MStringF.getYesNo(person.isListing));
//		this.telephoneLabel = MStringF.createLabel(this.telephone,MStringF.getLabelName(MString.TELEPHONE),person.telephone);
//		this.addressLabel = MStringF.createLabel(this.address,MStringF.getLabelName(MString.ADDRESS),person.address);
//		this.introductionLabel = MStringF.createLabel(this.introduction,MStringF.getLabelName(MString.INTRODUCTION),person.introduction);
//		
//		RecordDao.initRecordTableData(this.recordTable,person.recordList);
//		RecordDao.initRecordTableData(this.recordPlanTable,person.recordPlanList);
//
//		HRDao.initEditHRTableData(this.hrTable,person.hrList);

	}
	
	this.getJSONV = function(){
		var data = {};
		
		if(this.name.is(":visible")){
			data = nowPerson.getUpdateJSON(nowPersonEditForm);
		}
		
//		var hrTableJSON = HRDao.getHRTableUpdateJSON(this.hrTable);
//		
//		if(hrTableJSON.length > 0){
//			data[MString.TABLE_HR] = hrTableJSON;
//		}
//		
//		var tRecord = RecordDao.getTableJSON(this.recordTable,RecordType.HISTORY);
//		if(tRecord.length > 0){
//			data[MString.TABLE_RECORD] = tRecord;
//		}
//		
//		var tPlan = RecordDao.getTableJSON(this.recordPlanTable,RecordType.PLAN);
//		if(tPlan.length > 0){
//			data[MString.TABLE_RECORDPLAN] = tPlan;
//		}
  		
		if(!jQuery.isEmptyObject(data)){
			data[MString.ID] = nowPerson.id;
			if(nowPerson.id > 0){
				data[MString.UPDATEUSERID] = nowUser.id;
			}else{
				data[MString.CREATEUSERID] = nowUser.id;
				data[MString.UPDATEUSERID] = nowUser.id;
			}
		}
		
		console.log("PersonEditForm---------->"+MStringF.getPostJson(data));
		
		return data;
	}
}

nowPersonEditForm = new PersonEditForm();

nowPerson = new Person();

PersonDao = {
	expandArray:[],
	
	initPersonEditForm:function(){
//		 $('#vitae').wysiwyg();
		
		nowPersonEditForm.birthday.birthdaypicker({
			dateFormat : "bigEndian",
			wraper:"fieldset",
			futureDates : false,
			maxAge : 70,
			minAge:10
		});
		
		nowPersonEditForm.age.jStepper({minValue:0, maxValue:70, minLength:2});
		
		nowPersonEditForm.workYear.jStepper({minValue:0, maxValue:70, minLength:2});
		
		//教育经历
		MStringF.initSelect(nowPersonEditForm.education,MString.EDUCATION_LIST);
		
		//婚姻状况
		MStringF.initSelect(nowPersonEditForm.maritalstatus,MString.MARITALSTATUS_LIST);
     	
     	$("#cancel").live("click",function(e){
     		e.preventDefault();
     		if(nowPerson.id > 0){
	     		window.self.close();
	     		return;
     		}
     		window.location.href='person_index.html';
     	});
     },
	
	initPerson: function(){
		MStringF.setPageTitle("人才管理");
		
		PersonDao.initPersonTable();
		var personTable = $('#person_table').dataTable();
	
		var url = MainUrl.DOMAIN+MainUrl.URL_PERSON;
		var personV = {};
		personV[MString.VISIBLE] = VISIBLEMODE.VISIBLE;
		var data = {};
		data[MainUrl.ACTION] = MainUrl.ACTION_QUERY;
		data[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		data[MainUrl.ACTION_DATA] = MStringF.getPostJson(personV);
		
		$.post(url,data,function(data,status){
			if(data.hasOwnProperty(MString.TABLE_USER)){
				UserDao.setUserList(data[MString.TABLE_USER]);
			}
			$(data.person).each(function(){
				var tPerson = new Person();
				tPerson.initPerson(this);
				
				var column1Html = '<input type="text" style="display:none" value="' + tPerson.id + '"/>';
				column1Html = column1Html +tPerson.getShowName(); 
  				var columnEditHtml  = '';
				if(nowUser.role == 127){
//					columnEditHtml  = '<a id="expand" href="'+tPerson.id+'" class="btn icn-only blue"><i class="m-icon-swapdown m-icon-white"></i></a>';
					columnEditHtml += '<a id="edit" style="margin-left: 10px;" href="'+tPerson.id+'" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
					columnEditHtml += '<a id="remove" style="margin-left: 10px;" href="'+tPerson.id+'" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
				}else{
//					columnEditHtml  = '<a id="expand" href="'+tPerson.id+'" class="btn icn-only blue"><i class="m-icon-swapdown m-icon-white"></i></a>';
					columnEditHtml += '<a id="edit" style="margin-left: 10px;" href="'+tPerson.id+'" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
				}
				
//  		 aoColumnsV= [ 
//			    { "sTitle": checkBoxH,sWidth: '10px',sClass:"table-column-center",'aTargets': [0]}, 
//			    { "sTitle": "人才名称",sWidth: '134px',sClass:"table-column-center"}, 
//			    { "sTitle": "学历",sWidth: '134px',sClass:"table-column-center"}, 
//			    { "sTitle": "年限",sWidth: '134px',sClass:"table-column-center"}, 
//		        { "sTitle": "更新时间",sWidth: '170px',sClass:"table-column-center" },
//		        { "sTitle": "添加者",sWidth: '72px',sClass:"table-column-center" }, 
//		        { "sTitle": "",sWidth: '146px',sClass:"table-column-center" }//添加按钮
//	    	];
				
//  		aoColumnsV = [ 
//			   	{ "sTitle": "人才名称",sWidth: '60px',sClass:"table-column-center" }, 
//			   	{ "sTitle": "学历",sWidth: '134px',sClass:"table-column-center"}, 
//			    { "sTitle": "年限",sWidth: '134px',sClass:"table-column-center"}, 
//		        { "sTitle": "更新时间",sWidth: '60px',sClass:"table-column-center" },
//		        { "sTitle": "",sWidth: '42px',sClass:"table-column-center" }//查看按钮
//		    ];
				var addData = [
  					column1Html,
  					MStringF.getEducation(tPerson.education),
  					tPerson.workYear,
  					tPerson.updateDate,
					columnEditHtml
				];
				if(nowUser.role == 127){
					var tUser = UserDao.getUserById(tPerson.createUserId);
					var checkBoxH = '<input type="checkbox" value="'+tPerson.id+'" />';
					addData = [
  						checkBoxH,
  						tPerson.getShowName(),
  						MStringF.getEducation(tPerson.education),
  						tPerson.workYear,
  						tPerson.updateDate,
  						tUser.getShowName(),
  						columnEditHtml
  					]
				}
				
				var aiNew = personTable.fnAddData(addData);
				var nRow = personTable.fnGetNodes(aiNew);
				var rowObject = $(nRow);
				//当点击编辑时
				rowObject.find('#edit').live('click',function(e){
					e.preventDefault();
					var nowId = $(this).attr("href");
					MainUrl.openNewPersonEdit(nowId);
				});
				
				//当点击删除时
				rowObject.find('#remove').live('click',function(e){
  					e.preventDefault();
  					if (confirm("是否删除这个人才？") == false) {
  		                return false;
  		            }
  					var id = $(this).attr("href");
  					PersonDao.deletePersonById(personTable,this,id);
  					return false;
				});
				
//				//当点击展开时
//				rowObject.find('#expand').live('click',function(e){
//					e.preventDefault();
//					var childI = $(this).children("i");
//		  			var nTr = this.parentNode.parentNode;
//		   			var i = $.inArray(nTr,PersonDao.expandArray);
//		  		    
//		  			if (i === -1) {
//		  				var nDetailsRow = personTable.fnOpen(nTr,PersonDao.fnFormatDetails(personTable, nTr,tPerson), 'details' );
//		  				$('div.innerDetails', nDetailsRow).slideDown(function(){
//		  					childI.removeClass("m-icon-swapdown");
//		  					childI.addClass("m-icon-swapup");
//		  				});
//		  				PersonDao.expandArray.push(nTr);
//		  			}else {
//		  				$('div.innerDetails', $(nTr).next()[0]).slideUp(function(){
//							childI.removeClass("m-icon-swapup");
//							childI.addClass("m-icon-swapdown");
//							personTable.fnClose( nTr );
//							PersonDao.expandArray.splice( i, 1 );
//					    });
//		  			}
//				});
			});
		});
	},
	
	initPersonTable:function(){
		
    	if (!jQuery().dataTable) {
    		return;
    	}
    	var aoColumnsV = [];
    	var aoColumnDefsV = [];
    	var aaSortingV=[];

		var toolBarHtml = '';
    	if(nowUser.role == 127){
			var checkBoxH = '<input type="checkbox" value="" />';
    		 aoColumnsV= [ 
			    { "sTitle": checkBoxH,sWidth: '10px',sClass:"table-column-center",'aTargets': [0]}, 
			    { "sTitle": "人才名称",sWidth: '134px',sClass:"table-column-center"}, 
			    { "sTitle": "学历",sWidth: '134px',sClass:"table-column-center"}, 
			    { "sTitle": "年限",sWidth: '134px',sClass:"table-column-center"}, 
		        { "sTitle": "更新时间",sWidth: '170px',sClass:"table-column-center" },
		        { "sTitle": "添加者",sWidth: '72px',sClass:"table-column-center" }, 
		        { "sTitle": "",sWidth: '146px',sClass:"table-column-center" }//添加按钮
	    	];
		 	aoColumnDefsV = [
		 		{'bSortable': false,'aTargets': [0]},
		 		{'bSortable': false,'aTargets': [6]}
		 	];
		 	
		 	aaSortingV = [[ 4, "desc" ]], //更新时间排序
		 	
	    	toolBarHtml = ['<p style="width: auto;margin: 0px;">',	
				'<a id="newPerson" href="#" class="btn green"><i class="icon-plus"></i>添加人才</a>',
				'<a id="select" href="#" class="btn blue"><i class="icon-ok"></i>全选/反选</a>',
				'<a id="delete" href="#" class="btn red"><i class="icon-trash"></i>删除人才</a>',
			'</p>'].join('');
			
			var tCheckBoxList = $('#person_table > tbody').find(":checkbox");
			var headCheckBox = $('#person_table > thead').find(":checkbox:first-child");
	  		
	  		headCheckBox.live("click",function(event){
				var headChecked = $(this).is(':checked');
	  			var tCheckBoxList = $('#person_table > tbody').find(":checkbox");
				if(headChecked){
					tCheckBoxList.prop("checked",true);
				}else{
					tCheckBoxList.prop("checked",false);
				}
	  		});
	  		
	  		tCheckBoxList.live("click",function(event){
				PersonDao.updateTableCheck();
	  		});
  	  		
	  		var selectButton = $("#select");
        	selectButton.live("click",function(event){
	  			event.preventDefault();
	  			var tNotCheckBoxList = $('#person_table').parent().find(":checkbox:not(:checked)");
				if(tNotCheckBoxList.length > 0){
					tNotCheckBoxList.prop("checked",true);
				}else{
					var tCheckBoxList = $('#person_table').parent().find(":checkbox");
					tCheckBoxList.prop("checked",false);
				}
	  		});
  	  		
  	  		var deleteButton = $("#delete");
        	deleteButton.live("click",function(event){
	  			event.preventDefault();
	  			var tCheckBoxList = $('#person_table > tbody').find(":checkbox:checked");
	  			var idArray = [];
	  			tCheckBoxList.each(function(){
					idArray.push($(this).val());
	  			});
  				
				if(idArray.length > 0){
					if (confirm("是否删除这些人才？") == false) {
  		                return;
  		            }
					var personTable = $('#person_table').dataTable();
					PersonDao.deletePersonList(personTable,tCheckBoxList,idArray);
				}else{
					alert("请选择要删除的人才！");
				}
	  		});
    	}else{
    		aoColumnsV = [ 
			   	{ "sTitle": "人才名称",sWidth: '60px',sClass:"table-column-center" }, 
			   	{ "sTitle": "学历",sWidth: '134px',sClass:"table-column-center"}, 
			    { "sTitle": "年限",sWidth: '134px',sClass:"table-column-center"}, 
		        { "sTitle": "更新时间",sWidth: '60px',sClass:"table-column-center" },
		        { "sTitle": "",sWidth: '42px',sClass:"table-column-center" }//查看按钮
		    ];
			aoColumnDefsV = [
				{'bSortable': false,'aTargets': [0]},
				{'bSortable': false,'aTargets': [4]}
			];
			
			aaSortingV = [[ 3, "desc" ]], //更新时间排序
			
			toolBarHtml = ['<p style="width: auto;margin: 0px;">',	
				'<a id="newPerson" href="#" class="btn green"><i class="icon-plus"></i>添加人才</a>',
			'</p>'].join('');
    	}
    	
    	var personTable = $('#person_table').dataTable({
    		"aLengthMenu": [
	            [10, 25, 50, -1],
	            [5, 15, 20, "全部"]
	        ],
            "bAutoWidth": false,
            "iDisplayLength": -1,
            "aaSorting": aaSortingV,
            "sDom": "<'row-fluid wiki'<'span3'l><'span3'><'span3'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
            	"sProcessing": "正在加载中......",
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "对不起，查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
                "sSearch": "搜索",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上一页",
                    "sNext": "下一页",
                    "sLast": "末页"
                }
            },
            "aoColumns" : aoColumnsV,
            "aoColumnDefs": aoColumnDefsV
    	});
        
    	var personTableToolBar = $('#person_table').prev().find('.span3').eq(1);
  		var span3List = $('#person_table').prev().find('.span3');
  		span3List.eq(0).css("margin-top","3px");
  		span3List.eq(1).css("margin-left","30px");
  		span3List.eq(2).css("float","right");
  		span3List.eq(2).css("margin-right","10px");
  		
    	personTableToolBar.prepend(toolBarHtml);
        
        var newButton = $("#newPerson");
    	newButton.live("click",function(event){
  			event.preventDefault();
  			MainUrl.openNewPerson();
  		});
        
        $('#person_table_new').click(function (e) {
			MainUrl.openNewPerson();
        });
        
	},
	
	updateTableCheck:function(){
    	var tCheckBoxList = $('#person_table > tbody').find(":checkbox:checked");
		var isAllChecked = tCheckBoxList.is(':checked');
		console.log("isAllChecked ---> "+isAllChecked);
		var headCheckBox = $('#person_table > thead').find(":checkbox:first-child"); 
		if(isAllChecked){
			headCheckBox.prop("checked",true);
		}else{
			headCheckBox.prop("checked",false);
		}
	},
	
	initPersonCreateInfo:function(person){
  		var personInfo = $('#personInfoTitle');
	  	personInfo.append('<h6 style="display:inline-block;margin-left: 20px;">人才ID：<span>'+person.id+'</span></h6>');
  		if(person.createDate!=null && person.createDate.length > 0){
  			personInfo.append('<h6 style="display:inline-block;margin-left: 20px;">创建日期：<span>'+person.createDate+'</span></h6>');
  		}
  		if(nowUser.role == 127){
  			var createUser = UserDao.getUserById(person.createUserId).getShowName();
  			console.log("createUser ----------> "+createUser);
  			
  			if(createUser!=null && createUser.length >0){
  				personInfo.append('<h6 style="display:inline-block;margin-left: 20px;">创建人：<span>'+createUser+'</span></h6>');
  			}
  		}
  		
  		if(person.updateDate!=null && person.updateDate.length > 0){
  			personInfo.append('<h6 style="display:inline-block;margin-left: 20px;">更新日期：<span>'+person.updateDate+'</span></h6>');
  		}
  		if(nowUser.role == 127){
  			var updateUser = UserDao.getUserById(person.updateUserId).getShowName();
  			if(updateUser!=null && updateUser.length >0){
  				personInfo.append('<h6 style="display:inline-block;margin-left: 20px;">更新人：<span>'+updateUser+'</span></h6>');	
  			}
  		}
	},
	
	initEditPerson: function (){
//		HRDao.initHrTable();
//	  	
		nowPersonEditForm = new PersonEditForm();
		
  	  	PersonDao.initPersonEditForm();
//	  	
//	  	RecordDao.initRecordTable($('#recordTable'),$('#recordTable_new'),$('#recordTable a#remove'),RecordType.HISTORY);
//	  	
//	  	RecordDao.initRecordTable($('#recordPlanTable'),$('#recordPlanTable_new'),$('#recordPlanTable a#remove'),RecordType.PLAN);
	  
	 	var thisUrl = $.url();
		
		var personId = thisUrl.param(MString.ID);

		
		var nowForm = nowPersonEditForm;
		
		if(personId!=null&&personId.length>0){
			App.wiki_action = MainUrl.ACTION_UPDATE;
			nowPerson.id = personId;
  			MStringF.setPageTitle("编辑人才");
  			$('#sidebar_person_add').removeClass("active");
  			$('#sidebar_person_edit').addClass("active").show();
		}else{
			MStringF.setPageTitle("新增人才");
			nowPerson = new Person();
			App.wiki_action = MainUrl.ACTION_ADD;
  			nowForm.initAddMode();
  			PersonDao.setEditMode();
			return;
		}
	
		nowForm.initEditMode();
		
		personQueryUrl = MainUrl.DOMAIN + MainUrl.URL_PERSON;
		queryActionData = {};
		queryActionData[MString.ID] = personId;
		queryData = {};
		queryData[MainUrl.ACTION] = MainUrl.ACTION_QUERY;
		queryData[MainUrl.ACTION_DATA] = JSON.stringify(queryActionData);
		queryData[MString.TABLE_USER] = JSON.stringify(nowUser.getJSONV());
		
  		$.post(personQueryUrl,queryData,function(data,status){
  			console.log("edit person------>"+JSON.stringify(data));
  			if(data == null || data.length <=0){
  				alert("载入人才信息失败~！");
  			}
  			
  			if(data.hasOwnProperty(MString.TABLE_USER)){
  				UserDao.setUserList(data[MString.TABLE_USER]);
  			}
  			queryPersonList = data[MString.TABLE_PERSON];
  			if(queryPersonList.length > 0){
  				nowPerson.initPerson(queryPersonList[0]);
  			}else{
  				alert("载入人才信息失败~！");
  					window.self.close();
  					return;
  				}
  			
  			nowForm.setPerson(nowPerson);
  		});
	},
	
	setViewMode:function(){
		var personForm = $('#person_form');
		
		this.removeEditRule();
    	var personFormError = $('.alert-error', personForm);
    	personFormError.hide();
    	
    	var personValidateList = $('.control-group',personForm);
    	personValidateList.removeClass('error');
    	personValidateList.removeClass('success');

		var personHelpList = $('.help-inline',personForm);
    	personHelpList.remove();
	},
	
	setEditMode:function(){
		this.addEditRule();
	},
	
	addEditRule:function(){
		var cForm = nowPersonEditForm;
		cForm.name.rules("add",{
//			nameOrEnghlisName:true,
			required:true,
			singleName:true
		});
		
		cForm.gender.rules("add",{
			required:true
		});
		
		cForm.workStatus.rules("add",{
			required:true
		});
		
//		cForm.EnglishName.rules("add",{
//			nameOrEnghlisName:true
//		});
	},
	
	removeEditRule:function(){
		var cForm = nowPersonEditForm;
		cForm.name.rules("remove");
		cForm.gender.rules("remove");
		cForm.workStatus.rules("remove");
//		cForm.EnglishName.rules("remove");
//		cForm.telephone.rules("remove");
//		cForm.headhunter.rules("remove");
//		cForm.isListing.rules("remove");
//		cForm.intent.rules("remove");
	},
	
	deletePersonById:function(personTable,aTag,id){
		var person = {};
		person[MString.ID] = id;
		var personData = {};
		personData[MString.TABLE_PERSON] = person;
		var url = MainUrl.DOMAIN + MainUrl.URL_PERSON;
		var postData = {};
		postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(personData);
		$.post(url,postData,function(data,status){
			console.log("1--->"+JSON.stringify(data));
			if(data!=null){
				if(data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS){
					var nRow = $(aTag).parents('tr')[0];
	            	personTable.fnDeleteRow(nRow);
	        		PersonDao.updateTableCheck();
				}else{
					alert(data[MainUrl.KEY_MESSAGE]);  						
				}
			}else{
				alert("连接服务器失败！");
			}
		});
	},
	
	deletePersonList:function(personTable,aCheckList,list){
		var person = {};
		person[MString.ID] = list;
		var personData = {};
		personData[MString.TABLE_PERSON] = person;
		var url = MainUrl.DOMAIN + MainUrl.URL_PERSON;
		var postData = {};
		postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(personData);
		
		console.log("idList ------ > "+MStringF.getPostJson(list));
		$.post(url,postData,function(data,status){
			console.log("1--->"+JSON.stringify(data));
			if(data!=null){
				if(data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS){
					aCheckList.each(function(){
						var nRow = $(this).parents('tr')[0];
	            		personTable.fnDeleteRow(nRow);
					});
					var headCheckBox = $('#person_table > thead').find(":checkbox:first-child");
					headCheckBox.prop("checked",false);
				}else{
					alert(data[MainUrl.KEY_MESSAGE]);  						
				}
			}else{
				alert("连接服务器失败！");
			}
		});
	},
	
	fnFormatDetails:function(oTable, nTr, tPerson){
  	  var sOut =
  	  '<div class="innerDetails">'+
  	    '<ul class="unstyled">'+
  	    	'<li style="margin-top: 5px;">'+
  	        '<span class="wiki-ul-Left">公司地址</span><span class="wiki-ul-Right">'+tPerson.address+'</span>'+
  	      '</li>'+
  	    '</ul>' +
  	    '</div>';
  	  return sOut;
	}
};
