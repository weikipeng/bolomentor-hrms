function WorkHistory(){
	this.id= -1;
	this.personId= -1;
	this.companyName= '';
	this.job = '';
	this.salary='';
	this.startDate='';
	this.endDate='';
	this.leaveReason='';
	
//		private long id;
//	private long personId;
//	private String companyName;
//	private String job;
//	private String salary;
//	private String startDate;
//	private String endDate;
//	private String leaveReason;
	
	this.initPerson = function(data){		
		this.id = data.id;
		this.personId= data.personId;
		this.companyName= data.companyName;
		this.job = data.job;
		this.salary=data.salary;
		this.startDate=data.startDate;
		this.endDate=data.endDate;
		this.leaveReason=data.leaveReason;
	}
}

WorkHistoryDao = function(){
	
	return {
		initWorkHistory: function(){
		 	thisUrl = $.url();
			personId = thisUrl.param(MString.ID);
			
			if(personId!=null&&personId.length>0){
				return;
			}
			
			workHistoryTable = $('work_history');		
			workHistoryTable.dataTable({"bFilter": false,"bInfo": false});
				
			url = MainUrl.DOMAIN+MainUrl.URL_WORKHISTORY;
			data={
				action:MainUrl.ACTION_QUERY,
				personId:personId
			};			
			
			$.post(url,data,function(data,status){
				$(data).each(function(){
					itemData = this;
					companyName = '';
					hrName = '';
					personId = '';
					
					companyId = itemData.id;
					
					if(itemData.hasOwnProperty(MString.NAME)){
						companyName = itemData.name;
					}
					
					if(companyName.length <=0 && itemData.hasOwnProperty(MString.ENGLISHNAME)){
						companyName = itemData.EnglishName;
					}
					
					if(itemData.hasOwnProperty(MString.HR)){
						hrList = itemData.hr;
						if(hrList.length > 0){
							hr1 = hrList[0];
							if(hr1.hasOwnProperty(MString.NAME)){
								hrName = hr1.name;
							}
							
							if(hrName.length<=0 && hr1.hasOwnProperty(MString.ENGLISHNAME)){
								hrName = hr1.EnglishName;
							}
						}
						
					}
					console.log("hrName-->"+hrName);
				
					companyTable.fnAddData([
						companyName,
						hrName,
						'',
						'',
						'<a class="edit" href="" id="'+companyId + '">编辑</a>',
						''
					]);
				});
			});
		},
		
		initEditWorkHistory: function (){
		  	$('work_history').dataTable({"bFilter": false,"bInfo": false});
		 	thisUrl = $.url();
			
			personId = thisUrl.param(MString.ID);
			
			if(personId!=null&&personId.length>0){
				App.wiki_action = MainUrl.ACTION_UPDATE;
				nowCompany.id = personId;
			}else{
				App.wiki_action = MainUrl.ACTION_ADD;
				return;
			}
			
			companyQueryUrl = MainUrl.domain + MainUrl.url_company;
			data = {
				action:MainUrl.ACTION_QUERY,
				id:personId,
			};
			
			$.post(companyQueryUrl,data,function(data,status){
				if(data.length > 0){
					nowCompany.initCompany(data[0]);
				}				
				
				nowForm = new CompanyEditForm();			
				
				nowForm.setCompany(nowCompany);
			});
		  
		}
	};
}();
