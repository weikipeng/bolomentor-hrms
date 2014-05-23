function Company(){
	this.id= -1;
	this.createUserId= -1;
	this.createDate = '';
	this.updateUserId = -1;
	this.updateDate = '';
	this.name = '';
	this.EnglishName='';
	this.province='';
	this.city = '';
	this.address='';
	this.intent='';
	this.headhunter=-1;
	this.nature='';
	this.number='';
	this.isListing=-1;
	this.introduction='';
	this.telephone='';
	this.contactList = [];
	this.hrList = [];
	this.recordList =[];
	this.recordPlanList =[];
	this.visible = 1;
	
	this.initCompany = function(data){
//		console.log("Company---initCompany--->>"+JSON.stringify(dataText));
//		console.log("Company---initCompany--->>"+JSON.stringify(data));
		
//		data = $.parseJSON(dataText);
//		
//		console.log("Company---initCompany  data--->>"+data);
		
  		if(data.hasOwnProperty(MString.ID)){
  			this.id = data.id;
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
  		
  		if(data.hasOwnProperty(MString.NAME)){
  			this.name = data.name;
  		}
  		
  		if(data.hasOwnProperty(MString.ENGLISHNAME)){
  			this.EnglishName = data.EnglishName;
  		}
  		
  		if(data.hasOwnProperty(MString.PROVINCE)){
  			this.province = data.province;
  		}
  		
  		if(data.hasOwnProperty(MString.CITY)){
  			this.city = data.city;
  		}
  		
  		if(data.hasOwnProperty(MString.ADDRESS)){
  			this.address = data.address;
  		}
  		
  		if(data.hasOwnProperty(MString.INTENT)){
  			this.intent = data.intent;
  		}
  		
  		if(data.hasOwnProperty(MString.HEADHUNTER)){
  			this.headhunter = data.headhunter;
  		}
  		
  		if(data.hasOwnProperty(MString.NATURE)){
  			this.nature = data.nature;
  		}
  		
  		if(data.hasOwnProperty(MString.NUMBER)){
  			this.number = data.number;
  		}
  		
  		if(data.hasOwnProperty(MString.ISLISTING)){
  			this.isListing = data.isListing;
  		}
  		
  		if(data.hasOwnProperty(MString.INTRODUCTION)){
  			this.introduction = data.introduction;
  		}
  		
  		if(data.hasOwnProperty(MString.CONTACT)){
  			this.contactList = data.contact;
  			
  			telInfo = $.grep(this.contactList,function(item){
  //				console.log("                                       ");
  //				console.log("item.infoType----->"+item.infoType);
  //				console.log("ContactInfoType.COMPANY_TEL----->"+ContactInfoType.COMPANY_TEL);
  				return item.infoType == ContactInfoType.COMPANY_TEL;
  			});
  			this.telephone = telInfo[0].info;//注意返回的是数组
  		}
  		
		if(data.hasOwnProperty(MString.TABLE_HR)){
			var tHrList = data[MString.TABLE_HR];
  			var i = 0;
			for(i=0;i<tHrList.length;i++){
				var hr = new HR();
				hr.initHR(tHrList[i]);
				this.hrList.push(hr);
			}
		}
		
		if(data.hasOwnProperty(MString.TABLE_RECORD)){
			var tRecordList = data[MString.TABLE_RECORD];
  			var i = 0;
			for(i=0;i<tRecordList.length;i++){
				var tRecord = new Record();
				tRecord.initRecord(tRecordList[i]);
				this.recordList.push(tRecord);
			}
		}
		
		if(data.hasOwnProperty(MString.TABLE_RECORDPLAN)){
			var tRecordPlanList = data[MString.TABLE_RECORDPLAN];
  			var i = 0;
			for(i=0;i<tRecordPlanList.length;i++){
				var tRecord = new Record();
				tRecord.initRecord(tRecordPlanList[i]);
				this.recordPlanList.push(tRecord);
			}
		}
		
  		if(data.hasOwnProperty(MString.VISIBLE)){
  			this.visible = data[MString.VISIBLE];
  		}
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
	
	this.getShowHR = function(){
		var hr = new HR();
		var tHRList = this.hrList;
		if(tHRList!=null && tHRList.length >0){
			var i = 0;
			for(i=0;i<tHRList.length;i++){
//				console.log("															");
//				console.log("------->isWoking -------   "+JSON.stringify(tHRList[i]));
//				console.log("------->isWoking -------   "+tHRList[i].isWoking);

//				if(parseInt(tHRList[i].isWoking)==1){
//					console.log("tHRList------->isWoking -------   "+JSON.stringify(tHRList[i]));
//					hr = tHRList[i];
//					break;
//				}
  				if(parseInt(tHRList[i][MString.ISWORKING])){
//					console.log("tHRList------->isWoking -------   "+JSON.stringify(tHRList[i]));
  					hr = tHRList[i];
  					break;
  				}
			}
		}
		
//		console.log("hr------->"+JSON.stringify(hr));
		
		return hr;
	}
	
	this.getShowRecordPlan = function(){
		var recordPlan = new Record();
		var recordPlanList = this.recordPlanList;
		var i = 0;
		for(i=0;i<recordPlanList.length;i++){
			if(recordPlan.id < recordPlanList[i][MString.ID]){
				recordPlan = recordPlanList[i];
			}
		}
		
		return recordPlan;
	}
	
	this.getTel = function(){
		var tel = '';
		return tel;
	}
	
	this.getUpdateJSON = function(nowC){
//		if(data.hasOwnProperty(MString.PROVINCE)){
//			this.province = data.province;
//		}
//		
//		if(data.hasOwnProperty(MString.CITY)){
//			this.city = data.city;
//		}

		var data = {};
		var nameV = nowC.name.val();
		if(nameV != this.name){
			data[MString.NAME] = nameV;
		}
		
		var EnglishNameV = nowC.EnglishName.val(); 
		if(EnglishNameV != this.EnglishName){
			data[MString.ENGLISHNAME] = EnglishNameV;
		}
		
		var natureV = nowC.nature.val(); 
		if(natureV != this.nature){
			data[MString.NATURE] = natureV;
		}
		
		var numberV = nowC.number.val(); 
		if(numberV != this.number){
	  		data[MString.NUMBER] = numberV;
		}
		
		var intentV = nowC.intent.val(); 
		if(intentV != this.intent){
	  		data[MString.INTENT] = intentV;
		}

		var addressV = nowC.address.val();
  		if(addressV!=this.address){
  			data[MString.ADDRESS] = addressV;
  		}
  		
		var headhunterV = nowC.headhunter.filter(":checked").val();
  		if(headhunterV!=this.headhunter){
  			data[MString.HEADHUNTER] = headhunterV;
  		}
  		
		var isListingV = nowC.isListing.filter(":checked").val();
  		if(isListingV!=this.isListing){
  			data[MString.ISLISTING] = isListingV;
  		}
  		
		var introductionV = nowC.introduction.val();
		introductionV = introductionV.replace(htmlEscaper,function(match){
			return htmlEscapes[match];
		});
  		if(introductionV!=this.introduction){
  			data[MString.INTRODUCTION] = introductionV;
  		}
  		//电话
		var telephoneV = nowC.telephone.val();
  		if(telephoneV!=this.telephone){
	  		var telInfo = $.grep(this.contactList,function(item){
  				return item.infoType == ContactInfoType.COMPANY_TEL;
  			});
  			if(telInfo.length>0){
	  			telInfo[0].info = telephoneV;
		  		data[MString.TABLE_CONTACT] = telInfo;
  			}
  		}
  		
  		return data;
	}
	
	this.getJSON = function(){
		
	}
	
	this.setData = function(cData){
		
	}
	
	this.getHR = function(id){
		var nHR = new HR();
		var nHRList = $.grep(this.hrList,function(item){
  				return item.id == id;
  		});
  		if(nHRList.length > 0){
  			nHR = nHRList[0];
  		}
  		return nHR;
	}
	
	this.getRecord = function(id){
		var nRecord = new Record();
		var nRecordList = $.grep(this.recordList,function(item){
			return item.id == id;
  		});
  		if(nRecordList.length > 0){
  			nRecord = nRecordList[0];
  		}
  		return nRecord;
	}
	
	this.getRecordPlan = function(id){
		var nRecord = new Record();
		var nRecordList = $.grep(this.recordPlanList,function(item){
			return item.id == id;
  		});
  		if(nRecordList.length > 0){
  			nRecord = nRecordList[0];
  		}
  		return nRecord;
	}
}

//function CompanyDetailForm(){
//	this.name = $('[name="name"]');
//	this.EnglishName = $('[name="EnglishName"]');
//	this.nature = $('[name="nature"]');
//	this.number = $('[name="number"]');
//	this.intent= $('[name="intent"]');
//	this.headhunter = $('[name="headhunter"]');
//	this.isListing = $('[name="isListing"]');
//	this.telephone = $('[name="telephone"]');
//	this.address = $('[name="address"]');
//	this.introduction = $('[name="introduction"]');
//	this.hrTable = $('#hrTable');
//	this.recordTable = $('#recordTable');
//	this.recordPlanTable = $('#recordPlanTable');
//
//	this.setCompany = function(company){
//		this.name.text(company.name);
//		this.EnglishName.text(company.EnglishName);
//		this.nature.text(MStringF.getCompanyNature(company.nature));
//		this.number.text(MStringF.getCompanyNumber(company.number));
//		this.intent.text(MStringF.getIntent(company.intent));
//		this.headhunter.text(MStringF.getYesNo(company.headhunter));
//		this.isListing.text(MStringF.getYesNo(company.isListing));
//		this.telephone.text(company.telephone);		
//		this.address.text(company.address);
//		this.introduction.text(company.introduction);
//		
//		CompanyDao.initCompanyCreateInfo(company);
//		
//		HRDao.initHrTableData(this.hrTable,company.hrList);
//		RecordDao.initRecordTableData(this.recordTable,company.recordList);
//		RecordDao.initRecordTableData(this.recordPlanTable,company.recordPlanList);
//	}
//	
//	this.getJSONV = function(){
//		var data = {};
////		data[MString.TABLE_HR] = HRDao.getHRTableJSON(this.hrTable);
////		data[MString.TABLE_HR] = HRDao.getHRTableUpdateJSON(this.hrTable);
////		data[MString.TABLE_RECORD] = CompanyDao.getTableJSON(this.recordTable);
////		data[MString.TABLE_RECORDPLAN] = CompanyDao.getTableJSON(this.recordPlanTable);
//		
////		var tHRJSON = HRDao.getHRTableUpdateJSON(this.hrTable);
//		
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
//		
//		if(!jQuery.isEmptyObject(data)){
//			data[MString.ID] = nowCompany.id;
//		}
//		
//		console.log("566------>"+JSON.stringify(data));
//		return data;
//	}
//}

function CompanyEditForm(){
	this.mode = PAGE_MODE.VIEW;
	this.existName = false;
	this.existEnglishName = false;
//	this.existEnglishName = false;
	this.name = $('[name="name"]');
	this.EnglishName = $('[name="EnglishName"]');
	this.nature = $('[name="nature"]');
	this.number = $('[name="number"]');
	this.intent= $('[name="intent"]');
	this.headhunter = $('[name="headhunter"]');
	this.isListing = $('[name="isListing"]');
	this.telephone = $('[name="telephone"]');
	this.address = $('[name="address"]');
	this.introduction = $('[name="introduction"]');
	this.hrTable = $('#hrTable');
  	this.recordTable = $('#recordTable');
  	this.recordPlanTable = $('#recordPlanTable');
	
	this.nameLabel = "";
	this.EnglishNameLabel = "";
	this.natureLabel = "";
	this.numberLabel = "";
	this.intentLabel = "";
	this.headhunterLabel = "";
	this.isListingLabel = "";
	this.telephoneLabel = "";
	this.addressLabel = "";
	this.introductionLabel = $("#introduction_view");
	
	this.setCompany = function(company){
//		console.log("company----->"+JSON.stringify(company));
		this.name.val(company.name);
		this.EnglishName.val(company.EnglishName);
		this.nature.val(company.nature);
		this.number.val(company.number);
		this.intent.val(company.intent);
		tHeadhunter = this.headhunter.filter('[value='+company.headhunter+']');
		tHeadhunter.attr("checked",true);
		tHeadhunter.closest('span').addClass('checked');
		
		tIsListing = this.isListing.filter('[value='+company.isListing+']');
		tIsListing.attr("checked",true);
		tIsListing.closest('span').addClass('checked');
		tIsListing.closest('div').addClass('focus');
		
		this.telephone.val(company.telephone);		
		this.address.val(company.address);
		
		var cIntroduction = company.introduction.replace(/&quot;/g,'"');
		cIntroduction = cIntroduction.replace(/&#x27;/g,"'");
		this.introduction.val(cIntroduction);
		this.introduction.hide();
		this.introductionLabel.html(cIntroduction);
		
		//Label
		this.nameLabel = MStringF.createLabel(this.name,MStringF.getLabelName(MString.NAME),company.name);
		this.EnglishNameLabel = MStringF.createLabel(this.EnglishName,MStringF.getLabelName(MString.ENGLISHNAME),company.EnglishName);
		this.natureLabel = MStringF.createLabel(this.nature,MStringF.getLabelName(MString.NATURE),MStringF.getCompanyNature(company.nature));
		this.numberLabel = MStringF.createLabel(this.number,MStringF.getLabelName(MString.NUMBER),MStringF.getCompanyNumber(company.number));
		this.intentLabel = MStringF.createLabel(this.intent,MStringF.getLabelName(MString.INTENT),MStringF.getIntent(company.intent));
		this.headhunterLabel = MStringF.createLabel(this.headhunter,MStringF.getLabelName(MString.HEADHUNTER),MStringF.getYesNo(company.headhunter));
  		this.isListingLabel = MStringF.createLabel(this.isListing,MStringF.getLabelName(MString.ISLISTING),MStringF.getYesNo(company.isListing));
		this.telephoneLabel = MStringF.createLabel(this.telephone,MStringF.getLabelName(MString.TELEPHONE),company.telephone);
		this.addressLabel = MStringF.createLabel(this.address,MStringF.getLabelName(MString.ADDRESS),company.address);
//		this.introductionLabel = MStringF.createLabel(this.introduction,MStringF.getLabelName(MString.INTRODUCTION),company.introduction);
		
		CompanyDao.initCompanyCreateInfo(company);
		RecordDao.initRecordTableData(this.recordTable,company.recordList);
		RecordDao.initRecordTableData(this.recordPlanTable,company.recordPlanList);

		HRDao.initEditHRTableData(this.hrTable,company.hrList);
	}
	
	this.initEditMode = function(){
		var editButtonHtml = [	  			
			'<div class="tools">',
				'<button id="edit_company" class="btn green">',
				'编辑 <i class="icon-edit"></i>',
				'</button>',
			//<!--<a class="btn green">编辑 <i class="icon-edit"></i></a>-->
			'</div>'
	  	].join("");
	  	
	  	var layout = $("#companyInfoTitleLayout");
	  	layout.append(editButtonHtml);
	  	var editButton = $('#edit_company');
	  	
	  	if(nowUser.role!=127){
	  		editButton.hide();
	  	}
	  	
	  	editButton.live('click',function(e){
	  		e.preventDefault();
	  		var companyForm = $('#company_form');
	  		var controls = companyForm.find(".controls");

	  		if(nowCompanyEditForm.mode == PAGE_MODE.VIEW){
		  		console.log("editButton----------------");
		  		editButton.html('取消<i class="icon-remove"></i>');
		  		editButton.removeClass("green");
		  		editButton.addClass("red");
		  		
		  		controls.children().show();
		  		controls.find("span:last").hide();
		  		CompanyDao.setEditMode();
	  		}else{
	  			console.log("editButton----------------");
		  		editButton.html('编辑 <i class="icon-edit"></i>');
		  		editButton.removeClass("red");
		  		editButton.addClass("green");
		  		
		  		controls.children().hide();
		  		controls.find("span:last").show();
		  		CompanyDao.setViewMode();
	  		}
	  	});
		
		$('.ok').text("更新");
		
		this.initAddMode();
		CompanyDao.setViewMode();
	}
	
	this.initAddMode = function(){
      	jQuery.validator.addMethod("isMobile", function(value, element) {
      		var telTest = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/
      		var result = this.optional(element) || (telTest.test(value));
      		console.log("result ---> "+result);
		    return this.optional(element) || (telTest.test(value));
  		}, "请输入正确的手机或者电话号码！");
  		
      	jQuery.validator.addMethod("nameOrEnghlisName", function(value, element) {
      		var name = $('input[name="name"]').val();
      		var EnglishName = $('input[name="EnglishName"]').val();
      		
      		if(name.length>0 || EnglishName.length > 0){
      			return true;
      		}else{
      			return false;
      		}
      		
//    		console.log("name.length ---> "+name.length);
//    		console.log("EnglishName.length ---> "+EnglishName.length);
//    		
//    		var result = this.optional(element) || (name.length>0 || EnglishName.length > 0);
//    		console.log("result ---> "+result);
      		
//		    return this.optional(element) || (name.length>0 || EnglishName.length > 0);
  		}, "请输入公司名称或英文名！");
  		
      	jQuery.validator.addMethod("singleName", function(value, element) {
      		var isSuccess = true;
  			var name = $.trim($('input[name="name"]').val());
  			var oldName = $.trim($('[name="nameLabel"]').text());
    		if(name!=null && name.length > 0 && oldName != name){
  				var companyData = {};
  				companyData[MString.NAME] = name;
  				var queryData = {};
  				queryData[MString.TABLE_COMPANY] = companyData;
  				
  				var turl = MainUrl.DOMAIN + MainUrl.URL_COMPANY;
  				var tdata = {};
  				tdata[MainUrl.ACTION] = MainUrl.ACTION_SINGLE;
  				tdata[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
  				tdata[MainUrl.ACTION_DATA] = MStringF.getPostJson(queryData);
  				
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
		  							alert("该客户已存在！");
		  							isSuccess=false;
		  						}else{
		  							isSuccess=true;
		  						}
	  						}
                		}
  				});
  			}
			return isSuccess;
  		}, "该客户已存在！");
  		
    	var form1 = $('#company_form');
    	
    	var error1 = $('.alert-error', form1);
    	
    	form1.validate({
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
                	nameOREnghlisName: "请输入公司名称或英文名！",
                    required: "请输入公司名称或英文名！",
                    singleName:"该客户已存在！"
                },
				EnglishName: {
					nameOREnghlisName: "请输入公司名称或英文名！",
					required:"请输入公司名称或英文名！"
//					onfocusout:"该客户已存在！"
				},
                headhunter:{
                    required: "请选择是否与猎头合作"
                },
                isListing: {
                    required: "请输入公司是否上市!"
                },
                intent: {
                    required: "请选择公司的合作意图"
                },
				telephone:{
//					number:"请输入数字"
					isMobile:"请输入正确的手机或者电话号码"
				}
            },
    		
            errorPlacement:function(error,element){
                if(element.attr("name") == "headhunter"){
                    error.addClass("no-left-padding").insertAfter("#company_form_headhunter_error");
                }else if(element.attr("name") == "isListing"){
                    error.addClass("no-left-padding").insertAfter("#company_form_isListing_error");
                }
                else if(element.attr("name") == "name"){
                	console.log("errorPlacement -------------------- name");
//              	console.log("error -------------------- " + error.html());
//					$('[name="EnglishName"]')
//  				.closest('.help-inline').removeClass('ok'); // display OK icon
//  				$('[name="EnglishName"]')
//  				.closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
    				error.insertAfter(element);
                }else if(element.attr("name") == "EnglishName"){
                	console.log("errorPlacement -------------------- EnglishName");
//              	console.log("error -------------------- " + error.html());
//					$('[name="name"]')
//  				.closest('.help-inline').removeClass('ok'); // display OK icon
//  				$('[name="name"]')
//  				.closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
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
//					$('[name="EnglishName"]')
//	    			.closest('.help-inline').removeClass('ok'); // display OK icon
//	    			$('[name="EnglishName"]')
//	    			.closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
                }else if($(element).attr("name") == "EnglishName"){
                	console.log("highlight -------------------- 4");
//					$('[name="name"]')
//  				.closest('.help-inline').removeClass('ok'); // display OK icon
//	    			$('[name="name"]')
//	    			.closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
                }
    			
    			
    			$(element)
    			.closest('.help-inline').removeClass('ok'); // display OK icon
    			$(element)
    			.closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
    		},
    		
    		unhighlight: function (element) { // revert the change dony by hightlight
    			if($(element).attr("name") == "name"){
                	console.log("unhighlight -------------------- 1");
//					$('[name="EnglishName"]')
//					.closest('.control-group').removeClass('error'); // set error class to the control group
                }else if($(element).attr("name") == "EnglishName"){
                	console.log("unhighlight -------------------- 2");
//					$('[name="name"]')
//	    			.closest('.control-group').removeClass('error'); // set error class to the control group
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
  				var nowForm = new CompanyEditForm();
  				
  				var formData =  nowForm.getJSONV();
  				
  				if(jQuery.isEmptyObject(formData)){
//					window.location.href="company_index.html";
					window.self.close();
  					return true;
  				}
  				
  				
  				var url = MainUrl.DOMAIN+MainUrl.URL_COMPANY;
  				
  				postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(formData);
  				
    			console.log("add company-->"+JSON.stringify(postData));
  				
  				$.post(url,postData,function(data,status){
  					if(data!=null){
  						var status = data[MainUrl.STATUS];
  						status = parseInt(status);
  						if(status == MainUrl.RESULT_CODE_SUCCESS){
  							if(nowCompany.id > 0){
  								alert("修改客户信息成功！");
	  							window.location.href="company_edit.html?id="+nowCompany.id;
  							}else{
  	  							alert("添加客户信息成功！");
  	  							if (confirm("是否继续添加客户？") == true) {
  									window.location.href="company_edit.html";
  					            }else{
					            	window.location.href="company_edit.html?id="+data[MString.TABLE_COMPANY][MString.ID];
  					            }
  							}
  						}else{
  							alert(data[MainUrl.KEY_MESSAGE]);
							window.self.close();
  						}
  					}else{
  						alert("更新客户信息失败！");
//						window.location.href="company_index.html";
						window.self.close();
  					}
  				});
  				return false;
			}
    	});
	}
	
	this.getJSONV = function(){
		var data = {};
		
		if(nowCompanyEditForm.mode == PAGE_MODE.EDIT){
			data = nowCompany.getUpdateJSON(nowCompanyEditForm);
		}
		
		var hrTableJSON = HRDao.getHRTableUpdateJSON(this.hrTable);
		
		if(hrTableJSON.length > 0){
			data[MString.TABLE_HR] = hrTableJSON;
		}
		
  		var tRecord = RecordDao.getTableJSON(this.recordTable,RecordType.HISTORY);
  		if(tRecord.length > 0){
  			data[MString.TABLE_RECORD] = tRecord;
  		}
  		
  		var tPlan = RecordDao.getTableJSON(this.recordPlanTable,RecordType.PLAN);
  		if(tPlan.length > 0){
  			data[MString.TABLE_RECORDPLAN] = tPlan;
  		}
  		
		if(!jQuery.isEmptyObject(data)){
			data[MString.ID] = nowCompany.id;
			if(nowCompany.id > 0){
				data[MString.UPDATEUSERID] = nowUser.id;
			}else{
				data[MString.CREATEUSERID] = nowUser.id;
			}
		}
		
		console.log("CompanyEditForm---------->"+MStringF.getPostJson(data));
		
		return data;
	}
}

nowCompanyEditForm = new CompanyEditForm();

nowCompany = new Company();

CompanyDao = {
	expandArray:[],
	
    initCompanyEditForm:function(){
     	//-----------------------------------------------------公司人数
     	var numberList = MString.NUMBER_LIST;
     	var please_select = MString.TEXT_PLEASE_SELECT;
     	
     	var numberS = $("[name='number']");
     	numberS.empty();
     	
     	numberS.append(new Option(please_select,"-1"));
     	var i = 0;
     	for(i = 0;i<numberList.length;i++){
     		numberS.append(new Option(numberList[i],i));
     	}
     	//-----------------------------------------------------公司人数
     	
     	
     	
     	//-----------------------------------------------------公司人数
     	var intentList = MString.INTENT_LIST;
     	
     	var intentS = $("[name='intent']");
     	intentS.empty();
     	
     	intentS.append(new Option(please_select,"-1"));
     	i = 0;
     	for(i = 0;i<intentList.length;i++){
     		intentS.append(new Option(intentList[i],i));
     	}
     	
     	
     	
     	//-----------------------------------------------------公司性质
     	var natureList = MString.NATURE_LIST;
     	
     	var natureS = $("[name='nature']");
     	natureS.empty();
     	
     	natureS.append(new Option(please_select,"-1"));
     	i = 0;
     	for(i = 0;i<natureList.length;i++){
     		natureS.append(new Option(natureList[i],i));
     	}
     },
	
	updateTableCheck:function(){
    	var tCheckBoxList = $('#company_table > tbody').find(":checkbox:checked");
		var isAllChecked = tCheckBoxList.is(':checked');
		console.log("isAllChecked ---> "+isAllChecked);
		var headCheckBox = $('#company_table > thead').find(":checkbox:first-child"); 
		if(isAllChecked){
			headCheckBox.prop("checked",true);
		}else{
			headCheckBox.prop("checked",false);
		}
	},
	
	deleteCompanyById:function(companyTable,aTag,id){
		var company = {};
		company[MString.ID] = id;
		var companyData = {};
		companyData[MString.TABLE_COMPANY] = company;
		var url = MainUrl.DOMAIN + MainUrl.URL_COMPANY;
		var postData = {};
		postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(companyData);
		$.post(url,postData,function(data,status){
			console.log("1--->"+JSON.stringify(data));
			if(data!=null){
				if(data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS){
					var nRow = $(aTag).parents('tr')[0];
	            	companyTable.fnDeleteRow(nRow);
	        		CompanyDao.updateTableCheck();
				}else{
					alert(data[MainUrl.KEY_MESSAGE]);  						
				}
			}else{
				alert("连接服务器失败！");
			}
		});
	},
	
	deleteCompanyList:function(companyTable,aCheckList,list){
		var company = {};
		company[MString.ID] = list;
		var companyData = {};
		companyData[MString.TABLE_COMPANY] = company;
		var url = MainUrl.DOMAIN + MainUrl.URL_COMPANY;
		var postData = {};
		postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(companyData);
		
		console.log("idList ------ > "+MStringF.getPostJson(list));
		$.post(url,postData,function(data,status){
			console.log("1--->"+JSON.stringify(data));
			if(data!=null){
				if(data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS){
					aCheckList.each(function(){
						var nRow = $(this).parents('tr')[0];
	            		companyTable.fnDeleteRow(nRow);
					});
					var headCheckBox = $('#company_table > thead').find(":checkbox:first-child");
					headCheckBox.prop("checked",false);
				}else{
					alert(data[MainUrl.KEY_MESSAGE]);  						
				}
			}else{
				alert("连接服务器失败！");
			}
		});
	},
	
	initCompany: function(){
		CompanyDao.initCompanyTable();
		var companyTable = $('#company_table').dataTable();
	
		var url = MainUrl.DOMAIN+MainUrl.URL_COMPANY;
		var companyV = {};
		companyV[MString.VISIBLE] = VISIBLEMODE.VISIBLE;
		var data = {};
		data[MainUrl.ACTION] = MainUrl.ACTION_QUERY;
		data[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		data[MainUrl.ACTION_DATA] = MStringF.getPostJson(companyV);
		
		$.post(url,data,function(data,status){
//				console.log("1--->"+JSON.stringify(data));
			if(data.hasOwnProperty(MString.TABLE_USER)){
				UserDao.setUserList(data[MString.TABLE_USER]);
			}
			$(data.company).each(function(){
				var tCompany = new Company();
				tCompany.initCompany(this);
				var showHR = tCompany.getShowHR(); 
				
				var column1Html = '<input type="text" style="display:none" value="' + tCompany.id + '"/>';
				column1Html = column1Html +tCompany.getShowName(); 
//				var columnEditHtml = '<a id="edit" class="edit" href="' + tCompany.id + '">查看</a>';
  				var columnEditHtml  = '';
				if(nowUser.role == 127){
					columnEditHtml  = '<a id="expand" href="'+tCompany.id+'" class="btn icn-only blue"><i class="m-icon-swapdown m-icon-white"></i></a>';
					columnEditHtml += '<a id="edit" style="margin-left: 10px;" href="'+tCompany.id+'" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
					columnEditHtml += '<a id="remove" style="margin-left: 10px;" href="'+tCompany.id+'" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
				}else{
					columnEditHtml  = '<a id="expand" href="'+tCompany.id+'" class="btn icn-only blue"><i class="m-icon-swapdown m-icon-white"></i></a>';
					columnEditHtml += '<a id="edit" style="margin-left: 10px;" href="'+tCompany.id+'" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
				}
				
//				var nameHtml = tCompany.getShowName()+'<a href="#" class="btn icn-only blue"><i class="m-icon-swapdown m-icon-white"></i></a>';
				
				var addData = [
  					column1Html,
					showHR.getShowName(),
					showHR.getShowContact(),
					MStringF.getIntent(tCompany.intent),
					tCompany.getShowRecordPlan().date,
//					tCompany.address,
  					tCompany.updateDate,
					columnEditHtml
				];
				if(nowUser.role == 127){
					var tUser = UserDao.getUserById(tCompany.createUserId);
					var checkBoxH = '<input type="checkbox" value="'+tCompany.id+'" />';
					addData = [
  						checkBoxH,
  						tCompany.getShowName(),
  						showHR.getShowName(),
  						showHR.getShowContact(),
  						MStringF.getIntent(tCompany.intent),
  						tCompany.getShowRecordPlan().date,
//						tCompany.address,
  						tCompany.updateDate,
  						tUser.getShowName(),
  						columnEditHtml
  					]
					
//						var deleteHref = $('#company_table > tbody > tr > td:last-child');
//						deleteHref.children("a").click(function (e) {
//		  					e.preventDefault();
//		  					if (confirm("是否删除这个客户？") == false) {
//		  		                return false;
//		  		            }
//		  					var id = $(this).attr("href");
//		  					CompanyDao.deleteCompanyById(companyTable,this,id);
//		  					return false;
//		  		        });
				}
				
				var aiNew = companyTable.fnAddData(addData);
				var nRow = companyTable.fnGetNodes(aiNew);
				var rowObject = $(nRow);
				//当点击编辑时
				rowObject.find('#edit').live('click',function(e){
					e.preventDefault();
					var nowId = $(this).attr("href");
//					if(nowUser.role == 127){
//							window.open("company_edit.html?id="+nowId);
						MainUrl.openNewCompanyEdit(nowId);
//					}else{
//						MainUrl.openNewCompanyView(nowId);
////							window.open("company_detail.html?id="+nowId);
//					}
				});
				
				//当点击删除时
				rowObject.find('#remove').live('click',function(e){
  					e.preventDefault();
  					if (confirm("是否删除这个客户？") == false) {
  		                return false;
  		            }
  					var id = $(this).attr("href");
  					CompanyDao.deleteCompanyById(companyTable,this,id);
  					return false;
				});
				
				//当点击展开时
				rowObject.find('#expand').live('click',function(e){
  					e.preventDefault();
  					var childI = $(this).children("i");
		  			var nTr = this.parentNode.parentNode;
		   			var i = $.inArray(nTr,CompanyDao.expandArray);
		  		    
		  			if (i === -1) {
		  				var nDetailsRow = companyTable.fnOpen(nTr,CompanyDao.fnFormatDetails(companyTable, nTr,tCompany), 'details' );
		  				$('div.innerDetails', nDetailsRow).slideDown(function(){
		  					childI.removeClass("m-icon-swapdown");
		  					childI.addClass("m-icon-swapup");
//		  					$('div.innerDetails', nDetailsRow).child.width(nTr.children().eq(0).width);
		  				});
		  				CompanyDao.expandArray.push(nTr);
		  			}else {
		  				$('div.innerDetails', $(nTr).next()[0]).slideUp(function(){
							childI.removeClass("m-icon-swapup");
							childI.addClass("m-icon-swapdown");
							companyTable.fnClose( nTr );
							CompanyDao.expandArray.splice( i, 1 );
					    });
		  			}
				});
			});
		});
	},
	
	initCompanyTable:function(){
		
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
			    { "sTitle": "公司名称",sWidth: '134px',sClass:"table-column-center"}, 
		        { "sTitle": "HR姓名",sWidth: '60px',sClass:"table-column-center" },
		        { "sTitle": "电话",sWidth: '115px',sClass:"table-column-center" },
		        { "sTitle": "意向",sWidth: '60px',sClass:"table-column-center" },//QQ
		        { "sTitle": "下次联系时间",sWidth: null,sClass:"table-column-center" },//手机号
		        { "sTitle": "更新时间",sWidth: '170px',sClass:"table-column-center" },
		        { "sTitle": "添加者",sWidth: '72px',sClass:"table-column-center" }, 
		        { "sTitle": "",sWidth: '146px',sClass:"table-column-center" }//添加按钮
	    	];
		 	aoColumnDefsV = [
		 		{'bSortable': false,'aTargets': [0]},
		 		{'bSortable': false,'aTargets': [8]}
		 	];
		 	
		 	aaSortingV = [[ 6, "desc" ]], //更新时间排序
		 	
	    	toolBarHtml = ['<p style="width: auto;margin: 0px;">',	
				'<a id="newCompany" href="#" class="btn green"><i class="icon-plus"></i>添加客户</a>',
				'<a id="select" href="#" class="btn blue"><i class="icon-ok"></i>全选/反选</a>',
				'<a id="delete" href="#" class="btn red"><i class="icon-trash"></i>删除客户</a>',
			'</p>'].join('');
			
			var tCheckBoxList = $('#company_table > tbody').find(":checkbox");
			var headCheckBox = $('#company_table > thead').find(":checkbox:first-child");
	  		
	  		headCheckBox.live("click",function(event){
				var headChecked = $(this).is(':checked');
	  			var tCheckBoxList = $('#company_table > tbody').find(":checkbox");
				if(headChecked){
					tCheckBoxList.prop("checked",true);
				}else{
					tCheckBoxList.prop("checked",false);
				}
	  		});
	  		
	  		tCheckBoxList.live("click",function(event){
				CompanyDao.updateTableCheck();
	  		});
  	  		
	  		var selectButton = $("#select");
        	selectButton.live("click",function(event){
	  			event.preventDefault();
	  			var tNotCheckBoxList = $('#company_table').parent().find(":checkbox:not(:checked)");
				if(tNotCheckBoxList.length > 0){
					tNotCheckBoxList.prop("checked",true);
				}else{
					var tCheckBoxList = $('#company_table').parent().find(":checkbox");
					tCheckBoxList.prop("checked",false);
				}
	  		});
  	  		
  	  		var deleteButton = $("#delete");
        	deleteButton.live("click",function(event){
	  			event.preventDefault();
	  			var tCheckBoxList = $('#company_table > tbody').find(":checkbox:checked");
	  			var idArray = [];
	  			tCheckBoxList.each(function(){
					idArray.push($(this).val());
	  			});
  				
				if(idArray.length > 0){
					if (confirm("是否删除这些客户？") == false) {
  		                return;
  		            }
					var companyTable = $('#company_table').dataTable();
					CompanyDao.deleteCompanyList(companyTable,tCheckBoxList,idArray);
				}else{
					alert("请选择要删除的客户！");
				}
	  		});
    	}else{
    		aoColumnsV = [ 
			   	{ "sTitle": "公司名称",sWidth: '60px',sClass:"table-column-center" }, 
		        { "sTitle": "HR姓名",sWidth: '60px',sClass:"table-column-center" },
		        { "sTitle": "电话",sWidth: '40px',sClass:"table-column-center" },
		        { "sTitle": "意向",sWidth: '60px',sClass:"table-column-center" },//QQ
		        { "sTitle": "下次联系时间",sWidth: '60px',sClass:"table-column-center" },//手机号
		        { "sTitle": "更新时间",sWidth: '60px',sClass:"table-column-center" },
		        { "sTitle": "",sWidth: '42px',sClass:"table-column-center" }//查看按钮
		    ];
			aoColumnDefsV = [
				{'bSortable': false,'aTargets': [0]},
				{'bSortable': false,'aTargets': [6]}
			];
			
			aaSortingV = [[ 5, "desc" ]], //更新时间排序
			
			toolBarHtml = ['<p style="width: auto;margin: 0px;">',	
				'<a id="newCompany" href="#" class="btn green"><i class="icon-plus"></i>添加客户</a>',
			'</p>'].join('');
    	}
    	
    	// begin first table
    	var companyTable = $('#company_table').dataTable({
    		"aLengthMenu": [
	            [10, 25, 50, -1],
	            [5, 15, 20, "全部"]
	        ],
//	        "bStateSave": true,
            // set the initial value
            "bAutoWidth": false,
            "iDisplayLength": -1,
//          "aaSorting": [[ 5, "desc" ]], // Sort by first column descending
            "aaSorting": aaSortingV,
//  		                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
            "sDom": "<'row-fluid wiki'<'span3'l><'span3'><'span3'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
            	"sProcessing": "正在加载中......",
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "对不起，查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "sInfoEmpty": '数据表为空',
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
        
    	var companyTableToolBar = $('#company_table').prev().find('.span3').eq(1);
  		var span3List = $('#company_table').prev().find('.span3');
  		span3List.eq(0).css("margin-top","3px");
  		span3List.eq(1).css("margin-left","30px");
  		span3List.eq(2).css("float","right");
  		span3List.eq(2).css("margin-right","10px");
  		
    	companyTableToolBar.prepend(toolBarHtml);
        
        var newButton = $("#newCompany");
    	newButton.live("click",function(event){
  			event.preventDefault();
  			MainUrl.openNewCompany();
  		});
        
        $('#company_table_new').click(function (e) {
			MainUrl.openNewCompany();
        });
        
//		$('#company_table td:eq(1)').live( 'click', function () {
//			var nTr = this.parentNode;
//////			var nTr = $(this);
////			var nTr = this;
// 			var i = $.inArray(nTr,CompanyDao.expandArray);
//		    
//			if (i === -1) {
////				$('img', this).attr( 'src', sImageUrl+"details_close.png" );
//				companyTable.fnOpen(nTr,CompanyDao.fnFormatDetails(companyTable, nTr), 'details' );
////				anOpen.push( nTr );
//				CompanyDao.expandArray.push(nTr);
//			}else {
////			  $('img', this).attr( 'src', sImageUrl+"details_open.png" );
//			      companyTable.fnClose( nTr );
//			      CompanyDao.expandArray.splice( i, 1 );
//			}
//		});
	},
	
	initCompanyCreateInfo:function(company){
		var companyInfo = $('#companyInfoTitle');
		if(company.createDate!=null && company.createDate.length > 0){
			companyInfo.append('<h6 style="display:inline-block;margin-left: 20px;">创建日期：<span>'+company.createDate+'</span></h6>');
		}
		if(nowUser.role == 127){
			var createUser = UserDao.getUserById(company.createUserId).getShowName();
			console.log("createUser ----------> "+createUser);
			
			if(createUser!=null && createUser.length >0){
				companyInfo.append('<h6 style="display:inline-block;margin-left: 20px;">创建人：<span>'+createUser+'</span></h6>');
			}
		}
		
		if(company.updateDate!=null && company.updateDate.length > 0){
			companyInfo.append('<h6 style="display:inline-block;margin-left: 20px;">更新日期：<span>'+company.updateDate+'</span></h6>');
		}
		if(nowUser.role == 127){
			var updateUser = UserDao.getUserById(company.updateUserId).getShowName();
			if(updateUser!=null && updateUser.length >0){
				companyInfo.append('<h6 style="display:inline-block;margin-left: 20px;">更新人：<span>'+updateUser+'</span></h6>');	
			}
		}
	},
	
//	initCompanyDetailView:function(){
//	  
//		HRDao.initHrTable();
//	  	
//	  	RecordDao.initRecordTable($('#recordTable'),$('#recordTable_new'),$('#recordTable a#remove'),RecordType.HISTORY);
//	  	
//	  	RecordDao.initRecordTable($('#recordPlanTable'),$('#recordPlanTable_new'),$('#recordPlanTable a#remove'),RecordType.PLAN);
//	  
//	 	thisUrl = $.url();
//		
//		companyId = thisUrl.param(MString.ID);
//		
//		if(companyId!=null&&companyId.length>0){
//			App.wiki_action = MainUrl.ACTION_UPDATE;
//			nowCompany.id = companyId;
//		}else{
//			alert("未找到客户信息！");
////				window.location.href = "company_index.html"
//			window.self.close();
//			return;
//		}
//		
//		companyQueryUrl = MainUrl.DOMAIN + MainUrl.URL_COMPANY;
//		queryActionData = {};
//		queryActionData[MString.ID] = companyId;
//		queryData = {};
//		queryData[MainUrl.ACTION] = MainUrl.ACTION_QUERY;
//		queryData[MainUrl.ACTION_DATA] = JSON.stringify(queryActionData);
//		queryData[MString.TABLE_USER] = JSON.stringify(nowUser.getJSONV());
//		
//		$.post(companyQueryUrl,queryData,function(data,status){
//			if(data == null || data.length <=0){
//				alert("载入客户信息失败~！");
//			}
//			
//			if(data.hasOwnProperty(MString.TABLE_USER)){
//				UserDao.setUserList(data[MString.TABLE_USER]);
//			}
//			
//			queryCompanyList = data[MString.TABLE_COMPANY];
//			if(queryCompanyList.length > 0){
//				nowCompany.initCompany(queryCompanyList[0]);
//			}else{
//				alert("载入客户信息失败~！");
////					window.location.href = "company_index.html";
//				window.self.close();
//				return;
//			}
//			
//			var nowForm = new CompanyDetailForm();			
//			
//			nowForm.setCompany(nowCompany);
//		});
//	},
	
	setViewMode:function(){
		nowCompanyEditForm.mode = PAGE_MODE.VIEW;
		
		var nowForm = nowCompanyEditForm;
		nowForm.introduction.hide();
		nowForm.introductionLabel.show();
		
		var companyForm = $('#company_form');
		
		this.removeEditRule();
    	var companyFormError = $('.alert-error', companyForm);
    	companyFormError.hide();
    	
    	var companyValidateList = $('.control-group',companyForm);
    	companyValidateList.removeClass('error');
    	companyValidateList.removeClass('success');

		var companyHelpList = $('.help-inline',companyForm);
    	companyHelpList.remove();
	},
	
	setEditMode:function(){
		nowCompanyEditForm.mode = PAGE_MODE.EDIT;
		var nowForm = nowCompanyEditForm;
		nowForm.introduction.show();
		nowForm.introductionLabel.hide();
		
		var companyForm = $('#company_form');
		this.addEditRule();
	},
	
	addEditRule:function(){
		var cForm = nowCompanyEditForm;
		cForm.name.rules("add",{
//				onfocusout:function(element){
//					var name = $('input[name="name"]').val();
////						return isSingle(MString.NAME,name);
//		    		if(name!=null && name.length > 0){
//		  				var companyData = {};
//		  				companyData[MString.NAME] = name;
//		  				var queryData = {};
//		  				queryData[MString.TABLE_COMPANY] = companyData;
//		  				
//		  				var url = MainUrl.DOMAIN + MainUrl.URL_COMPANY;
//		  				var data = {};
//		  				data[MainUrl.ACTION] = MainUrl.ACTION_SINGLE;
//		  				data[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
//		  				data[MainUrl.ACTION_DATA] = MStringF.getPostJson(queryData);
//		  				$.post(url,data,function(data,status){
//		  					if(data!=null){
//		  						var rStatus = data.status;
//		  						rStatus = parseInt(rStatus);
//		  						if(rStatus == MainUrl.RESULT_CODE_FAILED){
//		  							alert("该客户已存在！");
//		  							return false;
//		  						}else{
//		  							return true;
//		  						}
//		  					}
//		  				});
//		  			}else{
//		  				return true;
//		  			}
//				},
			
//				required:function(element){
//					var EnglishName = $('input[name="EnglishName"]').val();
//					if(EnglishName.length > 0){
//						return false;
//					}else{
//						return true;
//					}						
//				},
			nameOrEnghlisName:true,
			singleName:true
		});
		
		cForm.EnglishName.rules("add",{
			nameOrEnghlisName:true
		});
		cForm.telephone.rules("add",{isMobile:true});
		cForm.headhunter.rules("add",{required:true});
		cForm.isListing.rules("add",{required:true});
		cForm.intent.rules("add",{required:true});
	},
	
	removeEditRule:function(){
//			var cForm = new CompanyEditForm();
		var cForm = nowCompanyEditForm;
		cForm.name.rules("remove");
		cForm.EnglishName.rules("remove");
		cForm.telephone.rules("remove");
		cForm.headhunter.rules("remove");
		cForm.isListing.rules("remove");
		cForm.intent.rules("remove");
	},
	
	initEditCompany: function (){
		nowCompanyEditForm = new CompanyEditForm();
		
	  	CompanyDao.initCompanyEditForm();
	  	
	  	HRDao.initHrTable();
	  	
	  	RecordDao.initRecordTable($('#recordTable'),$('#recordTable_new'),$('#recordTable a#remove'),RecordType.HISTORY);
	  	
	  	RecordDao.initRecordTable($('#recordPlanTable'),$('#recordPlanTable_new'),$('#recordPlanTable a#remove'),RecordType.PLAN);
	  
	 	var thisUrl = $.url();
		
		var companyId = thisUrl.param(MString.ID);

		
		var nowForm = nowCompanyEditForm;
		
		if(companyId!=null&&companyId.length>0){
			App.wiki_action = MainUrl.ACTION_UPDATE;
			nowCompany.id = companyId;
			MStringF.setPageTitle("编辑客户");
			$('#wiki_new_company_menu_title').removeClass("active");
			$('#wiki_edit_company_menu_title').show();
		}else{
			MStringF.setPageTitle("新增客户");
			nowCompany = new Company();
			App.wiki_action = MainUrl.ACTION_ADD;
			nowForm.initAddMode();
			CompanyDao.setEditMode();
			return;
		}
		
		nowForm.initEditMode();
		
		companyQueryUrl = MainUrl.DOMAIN + MainUrl.URL_COMPANY;
		queryActionData = {};
		queryActionData[MString.ID] = companyId;
		queryData = {};
		queryData[MainUrl.ACTION] = MainUrl.ACTION_QUERY;
		queryData[MainUrl.ACTION_DATA] = JSON.stringify(queryActionData);
		queryData[MString.TABLE_USER] = JSON.stringify(nowUser.getJSONV());
		
		$.post(companyQueryUrl,queryData,function(data,status){
			console.log("edit company------>"+JSON.stringify(data));
			if(data == null || data.length <=0){
				alert("载入客户信息失败~！");
			}
			
			if(data.hasOwnProperty(MString.TABLE_USER)){
				UserDao.setUserList(data[MString.TABLE_USER]);
			}
			queryCompanyList = data[MString.TABLE_COMPANY];
//				console.log("company length------>"+JSON.stringify(queryCompanyList));
//				nowCompany = new Company();
			if(queryCompanyList.length > 0){
				nowCompany.initCompany(queryCompanyList[0]);
			}else{
				alert("载入客户信息失败~！");
//					window.location.href = "company_index.html";
					window.self.close();
  					return;
  				}
  //				console.log("edit11 company------>"+nowCompany.name);
			
			nowForm.setCompany(nowCompany);
		});
	},
	
	fnFormatDetails:function(oTable, nTr, tCompany)
	{
//	  var sOut =
//	    '<div class="innerDetails">'+
//	      '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
//	        '<tr><td>公司地址</td><td>'+tCompany.address+'</td></tr>'+
//	      '</table>'+
//	    '</div>';
//	  return sOut;

  	  var sOut =
  	  '<div class="innerDetails">'+
  	    '<ul class="unstyled">'+
  	    	'<li style="margin-top: 5px;">'+
  	        '<span class="wiki-ul-Left">公司地址</span><span class="wiki-ul-Right">'+tCompany.address+'</span>'+
  	      '</li>'+
  	    '</ul>' +
  	    '</div>';
  	  return sOut;

//<ul class="unstyled">
//			<li style="margin-top: 5px;">
//				<select tabindex="-1" style="width: 95px;" class="wiki-ul-Left">
//					<option value="-1">请选择...</option><option value="0">手机</option><option value="1">QQ</option><option value="2">公司电话</option><option value="3">家庭电话</option><option value="4">公司邮箱</option><option value="5">私人邮箱</option><option value="6">其它</option>
//				</select>
//				<div class="wiki-ul-Right">
//					<input name="contactInfo" type="text" style="height: 28px !important;" disabled="disabled">
//					<button id="deleteContact" name="-1" class="btn red">
//						删除
//					</button>
//				</div><span class="wiki-ul-Left"></span><span class="wiki-ul-Right"></span>
//			</li>
//		</ul>

	}
}
