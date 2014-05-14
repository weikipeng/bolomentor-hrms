MainUrl = {
//	DOMAIN:"http://192.168.1.51/unknown",
	DOMAIN:"http://192.168.1.73:8080/unknown",
//	DOMAIN:"http://192.168.1.100:8080/bolomentor",
	url_company:"/company",
	URL_COMPANY:"/company",
	URL_WORKHISTORY:"/workHistory",
	URL_LOGIN:"/login",
	URL_CONTACT:"/contact",
	URL_HR:"/hr",
	URL_RECORD:"/record",
	URL_PERSON:"/person",
	
	DATA : "data",
	
	ACTION : "action",
	ACTION_DATA : "action_data",
	
	ACTION_ADD : "add",
	ACTION_DELETE : "delete",
	ACTION_UPDATE : "update",
	ACTION_QUERY : "query",
	ACTION_SINGLE:"single",

	KEY_RESULT: "result",
	KEY_MESSAGE: "message",
	STATUS: "status",

	RESULT_CODE_FAILED: 404,
	RESULT_CODE_SUCCESS: 200,
	RESULT_SUCCESS: "success",
	RESULT_FAILED: "failed",
	
//	var constant_domain ="http://192.168.1.73:8080/unknown";
//	var constant_url_company="/company";
//	var constant_action_add="add";
//	var constant_action="action"
	
	openNewCompanyEdit:function(id){
		window.open("company_edit.html?id="+id);
	},
	
	openNewCompanyView:function(id){
		window.open("company_detail.html?id="+id);
	},
	
	openNewCompany:function(){
		window.open("company_edit.html");
	},
	
	openNewPersonEdit:function(id){
		window.open("person_edit.html?id="+id);
	},
	
	openNewPerson:function(){
		window.open("person_edit.html");
	},
}