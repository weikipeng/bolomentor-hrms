function HR(){
	this.id= -1;
	this.companyId = -1;
	this.name = '';
	this.EnglishName='';
	this.gender=-1;
	this.occupation = '';
	this.isWorking=-1;
	this.contactList = [];
	
	this.initHR = function(data){
//		console.log("CONTACT------> "+JSON.stringify(data));
//		console.log("														");
//		console.log("														");
  		
  		if(data.hasOwnProperty(MString.ID)){
  			this.id = data.id;
  		}

		if(data.hasOwnProperty(MString.COMPANYID)){
  			this.companyId = data.companyId;
  		}
  		
  		if(data.hasOwnProperty(MString.NAME)){
  			this.name = data.name;
  		}
  		
  		if(data.hasOwnProperty(MString.ENGLISHNAME)){
  			this.EnglishName = data.EnglishName;
  		}
  		
  		if(data.hasOwnProperty(MString.GENDER)){
  			this.gender = data.gender;
  		}
  		
  		if(data.hasOwnProperty(MString.OCCUPATION)){
  			this.occupation = data.occupation;
  		}
  		
  		if(data.hasOwnProperty(MString.ISWORKING)){
  			this.isWorking = data[MString.ISWORKING];
  		}
  		
  		if(data.hasOwnProperty(MString.CONTACT)){
  			var contactArray = data.contact;
  			var i = 0;
  			for(i=0;i<contactArray.length;i++){
  				var contact = new Contact();
  				contact.initContact(contactArray[i]);
  				this.contactList.push(contact);
  			}
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
	
	this.getGenderString = function(){
		return MStringF.getGender(this.gender);
	}
	
	this.isWorkingString = function(){
		return MStringF.isWorking(this.isWorking);
	}
	
	this.getCompanyTel = function(){
		var telInfo = $.grep(this.contactList,function(item){
			return item.infoType == ContactInfoType.COMPANY_TEL;
		});
		if(telInfo!=null && telInfo.length > 0){
			return telInfo[0].info;//注意返回的是数组
		}
		return "";//注意返回的是数组
	}
	
	this.getMobile = function(){
		var mobieInfo = $.grep(this.contactList,function(item){
			return item.infoType == ContactInfoType.MOBILE;
		});
		if(mobieInfo!=null && mobieInfo.length > 0){
			return mobieInfo[0].info;//注意返回的是数组
		}
		return "";//注意返回的是数组
	}
	
	this.getQQ = function(){
		var qqInfo = $.grep(this.contactList,function(item){
			return item.infoType == ContactInfoType.QQ;
		});
		if(qqInfo!=null && qqInfo.length > 0){
			return qqInfo[0].info;//注意返回的是数组
		}
		return "";//注意返回的是数组
	}
	
	this.getEmail = function(){
		var mailInfo = $.grep(this.contactList,function(item){
			return item.infoType == ContactInfoType.COMPANY_EMAIL;
		});
		if(mailInfo!=null && mailInfo.length > 0){
			return mailInfo[0].info;//注意返回的是数组
		}
		return "";//注意返回的是数组
	}

	this.getContact = function(id){
		var nContact = new Contact();
		
		var nContactList = $.grep(this.contactList,function(item){
			return item.id == id;
  		});
  		if(nContactList.length > 0){
  			nContact = nContactList[0];
  		}
		
		return nContact;
	}

	this.getShowContact = function(){
		var result = '';
		var mob = this.getMobile();
		if(mob!=null && mob.length > 0){
			result = MString.TEXT_MOBILE + MString.TEXT_MAOHAO+mob;
		}else{
			tel = this.getCompanyTel();
			if(tel!=null && tel.length>0){
				result = MString.TEXT_COMPANY_TEL + MString.TEXT_MAOHAO+tel;	
			}else{
				qq = this.getQQ();
				if(qq!=null && qq.length > 0){
					result = MString.TEXT_QQ + MString.TEXT_MAOHAO+qq;
				}
			}
		}
		return result;
	}

	this.getContactHtml = function(){
		var isEditing = (nowUser.role == 127)||(this.id == -1);
	    var cHtml = '<ul class="unstyled">';
		var cList = this.contactList;
		if(cList!=null && cList.length>0){
			var i=0;
			for(i=0;i<cList.length;i++){
				var nContact = cList[i];
				cHtml +='<li style="margin-top: 5px;">';
				if(isEditing){
					cHtml += this.getEditContactHtml(nContact);
				}
				cHtml += '<span class="wiki-ul-Left">' + nContact.getContactInfoTypeLabel() +'</span>';//<i class="icon-img-up"></i>
				cHtml += '<span class="wiki-ul-Right">' + nContact.info + '</span>';
				cHtml +='</li>';	
			}
		}
		cHtml+='</ul>';
		if(isEditing){
			cHtml+='<a id="add" href="#" class="btn blue" style="display: block;margin-top: 5px;"><i class="icon-plus"></i> 添加</a>';
		}
		return cHtml;
	}
	
//	this.getNewContactHtml = function(){
//		var cHtml ='<li style="margin-top: 5px;">';
//		var selectType = [
//			'<select tabindex="-1" style="width: 95px;" class="wiki-ul-Left">',
//			'<option value="-1">请选择...</option>'
//		];
//		var i=0;
//		var typeList = ContactInfoTypeName;
//		for(i = 0;i<typeList.length;i++){
//			selectType.push('<option value="' + i + '">'+ ContactInfoTypeName[i] +'</option>');
//		}
//		
//		selectType.push('</select>');
//		
//		cHtml += selectType.join("");
//		cHtml +='<div class="wiki-ul-Right"><input	name="contactInfo" type="text" style="height: 28px !important;"/> <button class="btn red">删除</button></div>';
//		cHtml +='<span><span></li>';
//		return cHtml;
//	}
	
	this.getEditContactHtml = function(nContact){
		var cHtml ='';
		var selectType = [
			'<select tabindex="-1" style="width: 95px;" class="wiki-ul-Left">',
			'<option value="-1">请选择...</option>'
		];
		var i=0;
		var typeList = ContactInfoTypeName;
  		var selectHtml =' selected="selected"';
//		var selectHtml =' selected';
		for(i = 0;i<typeList.length;i++){
  			var emptyHtml = '';
  			if(nContact.infoType == i){
  				emptyHtml = selectHtml;
  			}
  			selectType.push('<option '+ emptyHtml +' value="' + i + '">'+ ContactInfoTypeName[i] +'</option>');
		}
		
		selectType.push('</select>');
		
		cHtml += selectType.join("");
		cHtml +='<div class="wiki-ul-Right"><input	name="contactInfo" type="text" style="height: 28px !important;" value="'+ nContact.info +'"/> <button id="deleteContact" name="' + nContact.id +'" class="btn red">删除</button></div>';
		cHtml +='';
		return cHtml;
	}
	
	this.isChanged = function(nRow){
		var changed = false;
		var hrItem = {};
		var jqInputs = $(nRow).find('input,select');
		
		var tName = jqInputs[0].value;
		var tEnglishName = jqInputs[1].value;
		var tGender = jqInputs[2].value;
		var tOccupation = jqInputs[3].value;

		var isWorkCol = $(nRow).children(":eq(5)").find("select");
		var tIsWorking = isWorkCol[0].value;
		
		if(tName!=this.name){
			changed = true;
			return changed;
		}
		
		if(tEnglishName!=this.EnglishName){
			changed = true;
			return changed;
		}
		
		if(tGender!=this.gender){
			changed = true;
			return changed;
		}
		
		if(tOccupation!=this.occupation){
			changed = true;
			return changed;
		}
		
		if(tIsWorking!=this.isWorking){
			changed = true;
			return changed;
		}
		
		var tContactList = [];
  		var contactColList = $(nRow).children(":eq(4)").find("li");
  		for(var j=0,iLen = contactColList.length;j<iLen;j++){
  			var liC = contactColList[j];
  			var editButton = $(liC).find("#deleteContact");
  			var contactId = editButton.attr("name");
  			
  			var nContact = this.getContact(contactId);
  			var nContactJSON = nContact.getUpdateJSON(liC);
  			
  			if(!jQuery.isEmptyObject(nContactJSON)){
				changed = true;
				return changed;
			}
  		}
		
		return changed;
	}
	
	this.getUpdateJSON = function(nRow){
		var hrItem = {};
		var jqInputs = $(nRow).find('input,select');
//		console.log("jqInputs --- "+ jqInputs);
//		console.log("jqInputs --- "+ JSON.stringify(jqInputs));
		
		var tName = jqInputs[0].value;
		var tEnglishName = jqInputs[1].value;
		var tGender = jqInputs[2].value;
		var tOccupation = jqInputs[3].value;

		var isWorkCol = $(nRow).children(":eq(5)").find("select");
		var tIsWorking = isWorkCol[0].value;
		
		if(tName!=this.name){
			hrItem[MString.NAME] = tName;
		}
		
		if(tEnglishName!=this.EnglishName){
			hrItem[MString.ENGLISHNAME] = tEnglishName;
		}
		
		if(tGender!=this.gender){
			hrItem[MString.GENDER] = tGender;
		}
		
		if(tOccupation!=this.occupation){
			hrItem[MString.OCCUPATION] = tOccupation;
		}
		
		if(tIsWorking!=this.isWorking){
			hrItem[MString.ISWORKING] = tIsWorking;
		}
		
		var tContactList = [];
  		var contactColList = $(nRow).children(":eq(4)").find("li");
  		for(var j=0,iLen = contactColList.length;j<iLen;j++){
  			var liC = contactColList[j];
//			var editContact = $(liC).find("select,input");
  			var editButton = $(liC).find("#deleteContact");
  			var contactId = editButton.attr("name");
  			
  			var nContact = this.getContact(contactId);
  			var nContactJSON = nContact.getUpdateJSON(liC);
  			
  			if(!jQuery.isEmptyObject(nContactJSON)){
  				if(contactId == -1){
	  				nContactJSON[MString.TYPE] = ContactType.HR; 
	  				nContactJSON[MString.TYPEID] = this.id; 
  				}
				tContactList.push(nContactJSON);
			}
  			
//			console.log(editContact.eq(0).val()+"   联系方式---------"+editContact.eq(1).val());
  		}
  		
  		if(tContactList.length > 0){
  			hrItem[MString.CONTACT] = tContactList;
  		}
		
		if(!jQuery.isEmptyObject(hrItem)){
			hrItem[MString.ID] = this.id;
			
			hrItem[MString.COMPANYID] = nowCompany.id;
		}
		
//		console.log("   hrItem---------"+jQuery.isEmptyObject(hrItem));
		return hrItem;
	}
	
	this.getCreateJSON = function(nRow){
		var hrItem = {};
		var jqInputs = $(nRow).find('input,select');
		
		var tName = jqInputs[0].value;
		var tEnglishName = jqInputs[1].value;
		var tGender = jqInputs[2].value;
		var tOccupation = jqInputs[3].value;

		var isWorkCol = $(nRow).children(":eq(5)").find("select");
		var tIsWorking = isWorkCol[0].value;
		
		if(tName!=this.name){
			hrItem[MString.NAME] = tName;
		}
		
		if(tEnglishName!=this.EnglishName){
			hrItem[MString.ENGLISHNAME] = tEnglishName;
		}
		
		if(tGender!=this.gender){
			hrItem[MString.GENDER] = tGender;
		}
		
		if(tOccupation!=this.occupation){
			hrItem[MString.OCCUPATION] = tOccupation;
		}
		
		if(tIsWorking!=this.isWorking){
			hrItem[MString.ISWORKING] = tIsWorking;
		}
		
		var tContactList = [];
  		var contactColList = $(nRow).children(":eq(4)").find("li");
  		for(var j=0,iLen = contactColList.length;j<iLen;j++){
  			var liC = contactColList[j];
  			var editButton = $(liC).find("#deleteContact");
  			var contactId = editButton.attr("name");
  			
  			var nContact = this.getContact(contactId);
  			var nContactJSON = nContact.getUpdateJSON(liC);
  			
  			if(!jQuery.isEmptyObject(nContactJSON)){
				tContactList.push(nContactJSON);
			}
  		}
  		
  		if(tContactList.length > 0){
  			hrItem[MString.CONTACT] = tContactList;
  		}
		
		if(!jQuery.isEmptyObject(hrItem)){
			hrItem[MString.COMPANYID] = this.id;
		}
		
		return hrItem;
	}
	
}

HRDao = {
	editArray:[],

	deleteArray:[],
	
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
//		cHtml +='<input	name="contactInfo" type="text" class="wiki-ul-Right" style="height: 28px !important;" disabled="disabled"/>';
		cHtml +='<div class="wiki-ul-Right"><input	name="contactInfo" type="text" style="height: 28px !important;" disabled="disabled"/> <button id="deleteContact" name="-1" class="btn red">删除</button></div>';
		cHtml +='<span class="wiki-ul-Left"></span><span class="wiki-ul-Right"></span></li>';
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
	
	addContactDeleteListener:function(contactCol){
  		$("#hrTable").find('* #deleteContact').live('click',function(e){
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
  			
//			if(typeSpan!=null && typeSpan.length>0){
//				
//			}else{
//				if(typeSelect.val()!=-1 && valueInput.val().length > 0){
//					
//				}
//			}
  		});

//		var temp = contactCol.find("#deleteContact");
//		if(temp!=null && temp.length>0){
//			temp.live('click',function(e){
//				e.preventDefault();
//				console.log("deleteContact -----------> ");
////				var typeSpan = $(this).closest("li").children("span:last");
////				var typeSelect = $(this).closest("li").children("select");;
////				
////				var valueSpan = $(this).closest("li").children("span:first");
////				var valueInput = $(this).filter("input");
////				
////				console.log("typeSpan -----------> "+typeSpan.text());
////				console.log("typeSelect -----------> "+typeSelect.val());
////				console.log("valueSpan -----------> "+valueSpan.text());
////				console.log("valueInput -----------> "+valueInput.val());
////				console.log("														");
////				
////				if(typeSelect!=null && typeSelect.length>0){
////					
////				}
//			});
//			temp.live('click',function(e){
//				e.preventDefault();
//				console.log("deleteContact -----------> ");				
//			});
//			console.log("temp ------> "+temp.html());
//		}
  		
	},
		 
	initHrTable:function () {
	 	var aoColumnsV = [
 	 		{ "sTitle": "姓名",sWidth: '95px',sClass:"table-column-center" }, 
	        { "sTitle": "英文名",sWidth: '95px',sClass:"table-column-center" },
	        { "sTitle": "性别",sWidth: '80px',sClass:"table-column-center" },
	        { "sTitle": "职位",sWidth: '95px',sClass:"table-column-center" },
  	        { "sTitle": "联系方式",sWidth: null,sClass:"table-column-center" },//联系方式
	        { "sTitle": "是否在职",sWidth: '80px',sClass:"table-column-center" },//是否在职
	        { "sTitle": "",sWidth: '100px',sClass:"table-column-center" }//删除按钮
	 	];
	 	
	 	var aoColumnDefsV = [
	 		{'bSortable': false,'aTargets': [0]},
            {'bSortable': false,'aTargets': [6]}
	 	];
	 	
 		var hrTable = $("#hrTable").dataTable({
    		bSort:false,
        	bFilter:false,
        	bLengthChange:false,
        	bPaginate:false,
        	"bAutoWidth": false,
        	"bJQueryUI": true, //Enable smooth theme
			"sPaginationType": "full_numbers", //Enable smooth theme
			"sDom": 't',
        	"bInfo":false,
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
	
		$("#hrTable").hide();

        $('#hrTable_new').click(function (e) {
            e.preventDefault();
            
            if (!jQuery().dataTable) {
				return;
			}
            
            if(!$("#hrTable").is(":visible")){
				$("#hrTable").show();
			}	            
            
            var aiNew = hrTable.fnAddData(['', '', '','','','','']);
        	var nRow = hrTable.fnGetNodes(aiNew[0]);
            HRDao.addHRRow(hrTable, nRow);
            nEditing = nRow;
        });
        
		$('#hrTable a#remove').live('click', function (e) {
            e.preventDefault();
			
            var nRow = $(this).parents('tr')[0];
            var nId = $(this).attr("href");
  			if(nId == -1){
  				var tHR = new HR();
  				var isChanged = tHR.isChanged(nRow);
  	            if(!isChanged){
  	            	hrTable.fnDeleteRow(nRow);
	  	            return;
  	            }
 			}
  			
			if (confirm("是否删除这个HR信息？") == false) {
            	return;
       		}	
            
            if(nId == -1){
            	hrTable.fnDeleteRow(nRow);
            }else{
            	HRDao.deleteHRById(nRow,nId);
            }
            
            var nRows = hrTable.dataTable().fnGetNodes();
            if(nRows.length <=0){
            	$("#hrTable").hide();
            }
        });
    },
    
  	initEditHRTableData:function(hrTable,hrList){
		HRDao.editArray.length = 0;
		if(hrList.length<=0){
			hrTable.hide();
		}else{
			hrTable.show();
			var hrDataTable = hrTable.dataTable();
			var isEditing = false;
			var nowEditingHR = null;
			for(i=0;i<hrList.length;i++){
				var aiNew = hrDataTable.fnAddData(HRDao.createHRTableItemData(hrList[i]));
				var nRow = hrDataTable.fnGetNodes(aiNew[0]);
				HRDao.initRowData(hrDataTable,nRow,hrList[i]);
				HRDao.initRowEvent(hrDataTable,nRow,hrList[i]);
			};
			
			HRDao.addContactDeleteListener(null);
		}
	},
    
    createHRTableItemData:function(hr){
		var dataArray =[];
		
		if(nowUser.role == 127){
			var nameHTML = '<input type="text" class="m-wrap small wiki" value="' + hr.name + '"/>'+MStringF.createSpan(hr.name);
			var EnglishNameHTML = '<input type="text" class="m-wrap small wiki" value="' + hr.EnglishName + '"/>'+MStringF.createSpan(hr.EnglishName);
			
			var genderHTML = '<select class="m-wrap small wiki" data-placeholder="Choose a Category" tabindex="1">'
				+'<option value="-1">请选择</option>'
				+'<option value="0">女</option>'
				+'<option value="1">男</option>'
				+'</select>'
				+MStringF.createSpan(hr.getGenderString());
			var occupationHTML = '<input type="text" class="m-wrap small wiki" value="'   + hr.occupation + '"/>'+MStringF.createSpan(hr.occupation);
			var isWorkingHTML = '<select class="m-wrap small wiki" data-placeholder="Choose a Category" tabindex="1">'
				+ '<option value="-1">请选择</option>'
				+'<option value="0">离职</option>'
				+'<option value="1">在任</option>'
				+'</select>'
				+MStringF.createSpan(hr.isWorkingString());
			
			var buttonHTML = '<a id="edit" href="#" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
			buttonHTML+= '<a id="remove" style="margin-left: 10px;" href="'+hr.id+'" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
			
			dataArray.push(nameHTML);
			dataArray.push(EnglishNameHTML);
			dataArray.push(genderHTML);
			dataArray.push(occupationHTML);
			dataArray.push(hr.getContactHtml());
			dataArray.push(isWorkingHTML);
			dataArray.push(buttonHTML);
		}else{
			dataArray.push(MStringF.createSpan(hr.name));
			dataArray.push(MStringF.createSpan(hr.EnglishName));
			dataArray.push(MStringF.createSpan(hr.getGenderString()));
			dataArray.push(MStringF.createSpan(hr.occupation));
			dataArray.push(hr.getContactHtml());
			dataArray.push(MStringF.createSpan(hr.isWorkingString()));
			dataArray.push('');
		}
			
		return dataArray;
	},
	
	initRowData:function(nHRDataTable,nRow,nHR){
		var rowObject = $(nRow);
		var gender = rowObject.find("select").eq(0);
		gender.val(nHR.gender);
		var isWorking = rowObject.find("select").last();
		isWorking.val(nHR.isWorking);
		
		var spanLabel = rowObject.find("span");
		spanLabel.siblings().not("span").hide();
		
		var contactCol = rowObject.find("td").eq(4);
		
		contactCol.find("#add").hide();
		
	},
	
	initRowEvent:function(nHRDataTable,nRow,nHR){
		var rowObject = $(nRow);
		
		var contactCol = rowObject.find("td").eq(4);
		HRDao.addContactSelectListener(contactCol);
		//当点击添加时
		rowObject.find('#add').live('click',function(e){
			e.preventDefault();
			var ulObj = $(this).prev();
			ulObj.append(HRDao.getNewContactHtml());
			HRDao.addContactSelectListener($(this).parent());
		});
		
		rowObject.find('#edit').live('click',function(e){
			e.preventDefault();
			var trNow = $(this).closest("tr");
			var nowId = $(trNow).find('a:last').attr("href");
			
			if(HRDao.editArray.indexOf(nowId)<0){
				HRDao.editHRRow(trNow);
				HRDao.editArray.push(nowId);
			}else{
				var isChanged = false;
				var spanList = $(trNow).children(":not(:eq(4))").find("span");
				spanList.each(function(){
					var oldValue = $(this).text();
					var nowInput = $(this).siblings().filter("input");
					if(nowInput!=null && nowInput.length > 0){
						if(oldValue!=nowInput.val()){
							console.log(oldValue + "  !=  "+nowInput.val());
							isChanged = true;
							return false;
						}
					}
					
					var nowSelect = $(this).siblings().filter("select");
					if(nowSelect !=null && nowSelect.length > 0){
						if(oldValue!=(nowSelect.children(":selected").text())){
							console.log(oldValue + "  !=  "+nowSelect.children(":selected").text());
							isChanged = true;
							return false;
						}
					}
				});
				
				var contactTagList = $(trNow).children(":eq(4)").find("span:even");
				var contactValueList = $(trNow).children(":eq(4)").find("span:odd");
				
				contactTagList.each(function(){
					var oldValue = $(this).text();
					var nowSelect = $(this).siblings().filter("select");
					if(nowSelect !=null && nowSelect.length > 0){
						if(oldValue!=(nowSelect.children(":selected").text())){
							console.log(oldValue + "  !=  "+nowSelect.children(":selected").text());
							isChanged = true;
							return false;
						}
					}
				});
				
				contactValueList.each(function(){
					var oldValue = $(this).text();
					var nowInput = $(this).parent().find("input");
					if(nowInput!=null && nowInput.length > 0){
						if(oldValue!=nowInput.val()){
							console.log(oldValue + "  !=  "+nowInput.val());
							isChanged = true;
							return false;
						}
					}
				});
				
				if(isChanged){
					if (confirm("是否取消修改这个HR信息？") == false) {
		                return;
		            }
				}
				
				HRDao.restoreRow(nHRDataTable,nRow,nHR);
				HRDao.initRowData(nHRDataTable,nRow,nHR);
				HRDao.editArray.splice(HRDao.editArray.indexOf(nowId),1);
			}
		});
	},
    
    deleteHRById:function(nRow,nId){
		var deleteUrl = MainUrl.DOMAIN + MainUrl.URL_HR;
		var nHRData = {};
		var postData = {};
		nHRData[MString.ID] = nId;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(nHRData);
		postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
		postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		
		$.post(deleteUrl,postData,function(data,status){
			console.log("1--->"+JSON.stringify(data));
			if(data!=null){
				if(data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS){
					$("#hrTable").dataTable().fnDeleteRow(nRow);
    	            var nRows = $("#hrTable").dataTable().fnGetNodes();
		            if(nRows.length <=0){
		            	$("#hrTable").hide();
		            }
				}else{
					alert(data[MainUrl.KEY_MESSAGE]);  						
				}
			}else{
				alert("连接服务器失败！");
			}
		});
    },
    
	addHRRow:function(oTable, nRow) {
        var aData = oTable.fnGetData(nRow);
        var jqTds = $('>td', nRow);
        var nHR = new HR();

        jqTds[0].innerHTML = '<input type="text" class="m-wrap small wiki" value="' + aData[0] + '"/>';
        jqTds[1].innerHTML = '<input type="text" class="m-wrap small wiki" value="' + aData[1] + '"/>';
        jqTds[2].innerHTML = '<select class="m-wrap small wiki" data-placeholder="Choose a Category" tabindex="1">'
        	+ '<option value="-1">请选择</option>'
        	+'<option value="0">女</option>'
        	+'<option value="1">男</option>';
        jqTds[3].innerHTML = '<input type="text" class="m-wrap small wiki" value="' + aData[3] + '"/>';
        
        jqTds[4].innerHTML = nHR.getContactHtml();
        
		jqTds[5].innerHTML = '<select class="m-wrap small wiki" data-placeholder="Choose a Category" tabindex="1">'
        	+ '<option value="-1">请选择</option>'
        	+'<option value="0">离职</option>'
        	+'<option value="1">在任</option>';

        jqTds[6].innerHTML = '<a id="remove" href="'+-1+'" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
		
		//当点击添加时
		$(jqTds[4]).find('#add').live('click',function(e){
			e.preventDefault();
			var ulObj = $(this).prev();
			ulObj.append(HRDao.getNewContactHtml());
			HRDao.addContactSelectListener($(this).parent());
//			HRDao.addContactDeleteListener(nRow);
		});
		HRDao.addContactDeleteListener(null);
		HRDao.addContactSelectListener($(this).parent());
	},
	
	editHRRow:function(trNow){
		var trNowObj = $(trNow);
		
		var spanLabel = trNowObj.find("span");
		spanLabel.hide();
		spanLabel.siblings().not("span").show();
		trNowObj.find("td").eq(4).find("a").show();
  	},
  	
  	restoreRow:function(nHRDataTable,nRow,nHR){
  		var aData = HRDao.createHRTableItemData(nHR);
        var jqTds = $('>td', nRow);
		
        for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
            nHRDataTable.fnUpdate(aData[i], nRow, i, false);
        }

        nHRDataTable.fnDraw();
	},
	
  	initHrTableData:function(hrTable,hrList){
		if(hrList.length<=0){
			hrTable.hide();
		}else{
			hrTable.show();
			var hrDataTable = hrTable.dataTable();
			for(i=0;i<hrList.length;i++){
				var aiNew = hrDataTable.fnAddData(HRDao.createHRTableItemData(hrList[i]));
//  				var aiNew = hrTable.dataTable().fnAddData([
//  					hrList[i].name,
//  					hrList[i].EnglishName,
//  					hrList[i].getGenderString(),
//  					hrList[i].occupation,
//  					hrList[i].getCompanyTel(),
//  					hrList[i].getEmail(),
//  					hrList[i].isWorkingString(),
//  					''
//  				]);
			};
		}
	},
  	
	getHRTableJSON: function(hrTable){
		var hrListData = [];
		
		var nRows = hrTable.dataTable().fnGetNodes();
		for(i=0;i<nRows.length;i++){
			var nId = $(nRows[i]).find("a:last").attr("href");
			nId = parseInt(nId);
	  		console.log("nId---------"+nId);
	  		console.log("editArray---------"+JSON.stringify(HRDao.editArray));
			if(nId > 0){
				if(HRDao.editArray.indexOf(String(nId))>=0){
  	  				console.log("start---------");
					var jqInputs = $(nRows[i]).find('input,select');
					if(jqInputs.length<=0) return;
  					var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
		  			if(!jQuery.isEmptyObject(hrItem)){
			  			hrListData.push(hrItem);
		  			}
				}
			}else{
				var jqInputs = $(nRows[i]).find('input,select');
				if(jqInputs.length<=0) return;
				var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
				if(!jQuery.isEmptyObject(hrItem)){
		  			hrListData.push(hrItem);
		  		}
			}
		}
  		console.log("hrListData---------"+JSON.stringify(hrListData));
		return hrListData;
//		var hrListData = [];
//		
//		var nRows = hrTable.dataTable().fnGetNodes();
//		for(i=0;i<nRows.length;i++){
//			var jqInputs = $(nRows[i]).find('input,select');
//			if(jqInputs.length<=0) continue;
//			var hrItem = {};
//			hrItem[MString.COMPANYID] = nowCompany.id;
//			hrItem[MString.NAME] = jqInputs[0].value;
//			hrItem[MString.ENGLISHNAME] = jqInputs[1].value;
//			hrItem[MString.GENDER] = jqInputs[2].value;
//			hrItem[MString.OCCUPATION] = jqInputs[3].value;
//			hrItem[MString.ISWORKING] = jqInputs[8].value;
//			
//			var contactList = [];
//			var hrTel = ContactDao.newHrCompanyTel(-1,-1,jqInputs[4].value);
//			var hrMobile = ContactDao.newHrMobile(-1,-1,jqInputs[5].value);
//			var hrQQ = ContactDao.newHrQQ(-1,-1,jqInputs[6].value);
//			var hrCompanyEmail = ContactDao.newHrCompanyEmail(-1,-1,jqInputs[7].value);
//			
//			contactList.push(hrTel);
//			contactList.push(hrMobile);
//			contactList.push(hrQQ);
//			
//			hrItem[MString.CONTACT] = MStringF.getPostJson(contactList);
//			
//			hrListData.push(hrItem);
//		}
//		console.log("hrListData---------"+JSON.stringify(hrListData));
//		return hrListData;
	},
	
	getHRTableUpdateJSON: function(hrTable){
  		var hrListData = [];
		
		var nRows = hrTable.dataTable().fnGetNodes();
		for(var i=0,iLen = nRows.length;i<iLen;i++){
			var nId = $(nRows[i]).find("a:last").attr("href");
			nId = parseInt(nId);
	  		console.log("nId---------"+nId);
	  		console.log("editArray---------"+JSON.stringify(HRDao.editArray));
			if(nId > 0){
				if(HRDao.editArray.indexOf(String(nId))>=0){
  	  				console.log("start---------");
					var jqInputs = $(nRows[i]).find('input,select');
					if(jqInputs.length>0){
	  					var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
	//					var hrItem = {};
	//					hrItem[MString.COMPANYID] = nId;
	//		  			hrItem[MString.NAME] = jqInputs[0].value;
	//		  			hrItem[MString.ENGLISHNAME] = jqInputs[1].value;
	//		  			hrItem[MString.GENDER] = jqInputs[2].value;
	//		  			hrItem[MString.OCCUPATION] = jqInputs[3].value;
	//
	//					var isWorkCol = $(nRows[i]).children(":eq(5)").find("select");
	//		  			hrItem[MString.ISWORKING] = isWorkCol[0].value;
	//		  			
	//					var contactColList = $(nRows[i]).children(":eq(4)").find("li");
	////	  				console.log("contactCol---------"+contactColList.html());
	////	  				console.log("contactCol---------"+contactColList.length);
	//					
	//					for(var j=0,iLen = contactColList.length;j<iLen;j++){
	//						var liC = contactColList[j];
	////	  					console.log("liC---------"+liC);
	////	  					console.log("liC---------"+$(liC).html());
	//						var editContact = $(liC).find("select,input");
	//						console.log(editContact.eq(0).val()+"   联系方式---------"+editContact.eq(1).val());
	//					}
	//		  			
	//		//			var contactList = [];
	//		//			var hrTel = ContactDao.newHrCompanyTel(-1,-1,jqInputs[4].value);
	//		//			var hrMobile = ContactDao.newHrMobile(-1,-1,jqInputs[5].value);
	//		//			var hrQQ = ContactDao.newHrQQ(-1,-1,jqInputs[6].value);
	//		//			var hrCompanyEmail = ContactDao.newHrCompanyEmail(-1,-1,jqInputs[7].value);
	//		//			
	//		//			contactList.push(hrTel);
	//		//			contactList.push(hrMobile);
	//		//			contactList.push(hrQQ);
	//		  			
	//		//			hrItem[MString.CONTACT] = MStringF.getPostJson(contactList);
			  			if(!jQuery.isEmptyObject(hrItem)){
				  			hrListData.push(hrItem);
			  			}
		  			}
				}
			}else{
				var jqInputs = $(nRows[i]).find('input,select');
				if(jqInputs.length>0){
					var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
					if(!jQuery.isEmptyObject(hrItem)){
			  			hrListData.push(hrItem);
			  		}
				}
			}
		}
  		console.log("hrListData---------"+JSON.stringify(hrListData));
		return hrListData;
  	}
};
