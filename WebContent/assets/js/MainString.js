MString ={
	TRUE:"true",	
	//Company
	ID : "id",
	NAME : "name",
	PROVINCE : "province",
	CITY : "city",
	ADDRESS : "address",
	INTENT : "intent",
	HEADHUNTER : "headhunter",
	NATURE : "nature",
	NUMBER : "number",
	ISLISTING : "isListing",
	INTRODUCTION : "introduction",
	TELEPHONE:"telephone",
	VISIBLE:"visible",
	
	//HR
	ENGLISHNAME : "EnglishName",
	GENDER : "gender",
	OCCUPATION : "occupation",
	ISWORKING : "isWorking",
	COMPANYID : "companyId",
	HR : "hr",
	
	//Contact
	TYPE : "type",
	TYPEID : "typeId",
	INFOTYPE : "infoType",
	INFO : "info",
	CONTACT : "contact",
	
	//Person
	BIRTHDAY : "birthday",
	AGE : "age",
	EDUCATION : "education",
	MARITALSTATUS : "maritalstatus",
	WORKYEAR : "workYear",
	LIVEADDRESS : "liveAddress",
	BELONGADDRESS : "belongAddress",
	ENGLISHLEVEL : "EnglishLevel",
	OTHERLANGUAGE : "otherLanguage",
	SALARY : "salary",
	COMPANYNATURE : "companyNature",
	WORKSTATUS : "workStatus",
	HOPEADDRESS : "hopeAddress",
	HOPEJOB : "hopeJob",
	HOPEVOCATION : "hopeVocation",
	HOPESALARY : "hopeSalary",
	ISHOPE : "isHope",
	VITAE : "vitae",
	CREATEUSERID : "createUserId",
	CREATEUSER : "createUser",
	CREATEDATE : "createDate",
	UPDATEUSERID : "updateUserId",
	UPDATEUSER : "updateUser",
	UPDATEDATE : "updateDate",
	
	//Project表
//	COMPANYID : "companyId",
	COMPANYNAME : "companyName",
	HRNAME : "HRName",
	HRID : "HRId",
	JOBID : "jobId",
	JOBNAME : "jobName",
	BELONGUSERID : "belongUserId",
	BELONGUSER : "belongUser",
	
	//Job
	STATUS : "status",
	
	
	
	TABLE_COMPANY : "company",
	TABLE_HR : "hr",
	TABLE_CONTACT : "contact",

	TABLE_PERSON : "person",
	TABLE_COMPANYJOB : "company_job",
	TABLE_JOB : "job",
	TABLE_PROJECT : "project",
	TABLE_USER : "user",
	TABLE_RECORD : "record",
	TABLE_RECORDPLAN : "recordPlan",
	
		//User
//	public static final String COLUMN_USERNAME = "username";
//	public static final String COLUMN_PASSWORD = "password";
//	public static final String COLUMN_ROLE = "role";
//	public static final String COLUMN_NICKNAME = "nickName";
//	public static final String COLUMN_AVATAR = "avatar";
	USERNAME:"username",
	PASSWORD : "password",
	ROLE: "role",
	ISREMEBERUSERNAME:"isRemeberUsername",
	NICKNAME : "nickName",
	AVATAR : "avatar",
	
	//Record
	DATE : "date",
	CONTENT : "content",
	
	
	//ContactInfoType
	MOBILE:"mobile",
	QQ:"QQ",
	COMPANY_TEL:"company_tel",
	HOME_TEL:"home_tel",
	COMPANY_EMAIL:"company_email",
	PRIVATE_EMAIL:"private_email",
	OTHER:"other",
	
	
	
	
	LABEL:"Label",
	UNKNOWN:"未知",
	YES_NO_LIST:["否","是"],
	INTENT_LIST:["无意向","一般","有意向"],
	GENDER_LIST:["女","男"],
	ISWORKING_LIST:["离职","在任"],
	NATURE_LIST:["欧美","日资","台资","合资","民企","其他"],
	NUMBER_LIST:["50以下","50-150","150-500","500-1000","1000-5000","5000-10000","10000以上"],
	EDUCATION_LIST:["大专","本科","硕士","博士"],

	MARITALSTATUS_LIST:["未婚","已婚","离异"],
	
	TEXT_PLEASE_SELECT : "请选择...",
	TEXT_MOBILE : "手机",
	TEXT_COMPANY_TEL : "公司电话",
	TEXT_MAOHAO : "：",
	TEXT_QQ : "QQ",
}

MStringF = {
	initSelect:function(widget,valueList){
		var pSelect = new Option(MString.TEXT_PLEASE_SELECT,"-1");
     	widget.empty();
     	widget.append(pSelect);
     	for(var i = 0;i<valueList.length;i++){
     		widget.append(new Option(valueList[i],i));
     	}
	},
	
	getGender:function(index){
		var index = parseInt(index);
		switch(index){
			case 0:
			case 1:
				return MString.GENDER_LIST[index];
			default:
				return MString.UNKNOWN;
		}
	},
	
	getIntent:function(index){
		var index = parseInt(index);
		switch(index){
			case 0:
			case 1:
			case 2:
				return MString.INTENT_LIST[index];
			default:
				return MString.UNKNOWN;
		}
	},
	
	getYesNo:function(index){
		var index = parseInt(index);
		switch(index){
			case 0:
			case 1:
				return MString.YES_NO_LIST[index];
			default:
				return MString.UNKNOWN;
		}
	},	
	
	getCompanyNumber:function(index){
		var indexV = parseInt(index);
		if(indexV!=null && indexV >=0 && indexV < MString.NUMBER_LIST.length){
			return MString.NUMBER_LIST[indexV];
		}else if(indexV == -1){
			return MString.UNKNOWN;
		}else{
			return index;
		}
	},
	
	getCompanyNature:function(index){
		var indexV = parseInt(index);
		if(indexV!=null && indexV >=0 && indexV < MString.NATURE_LIST.length){
			return MString.NATURE_LIST[indexV];
		}else if(indexV == -1){
			return MString.UNKNOWN;
		}else{
			return index;
		}
	},
	
	getMaritalstatus:function(index){
		var indexV = parseInt(index);
		if(indexV!=null && indexV >=0 && indexV < MString.MARITALSTATUS_LIST.length){
			return MString.MARITALSTATUS_LIST[indexV];
		}else if(indexV == -1){
			return MString.UNKNOWN;
		}else{
			return index;
		}
	},
	
	getIntent:function(index){
		var index = parseInt(index);
		switch(index){
			case 0:
			case 1:
			case 2:
				return MString.INTENT_LIST[index];
			default:
				return MString.UNKNOWN;
		}
	},
	
	getEducation:function(index){
		var indexV = parseInt(index);
		if(indexV!=null && indexV >=0 && indexV < MString.EDUCATION_LIST.length){
			return MString.EDUCATION_LIST[indexV];
		}else if(indexV == -1){
			return MString.UNKNOWN;
		}else{
			return index;
		}
	},
	
	isWorking:function(index){
		var index = parseInt(index);
		switch(index){
			case 0:
			case 1:
				return MString.ISWORKING_LIST[index];
			default:
				return MString.UNKNOWN;
		}
	},
	
	setPageTitle:function(title){
		$("* #wiki_page_title").text(title);
	},
	
	getPostJson:function(data){
		return JSON.stringify(data);
	},
	
	createSpan:function(text){
		var spanText = '<span class="text" >'+text+'</span>';
		
		return spanText;
  	},
  	
  	getLabelName:function(name){
		return name+MString.LABEL;
	},
  	
  	createLabel:function(nowTag,name,value){
		var labelHtml = '<span class="text" name="'+name+'">'+value+'</span>';
  		var nowParent = nowTag.closest('.controls');
		nowParent.children().hide();
		nowParent.append(labelHtml);
		return nowParent.children().last();
	},
}

MStringFEDITMODE = {
	MODE_VIEW:0,
	MODE_EDIT:1
}

VISIBLEMODE = {
	INVISIBLE:0,
	VISIBLE:1
}
