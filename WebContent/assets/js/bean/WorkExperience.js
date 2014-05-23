function WorkExperience = {
	this.id = -1;
	this.personId = -1;
	this.companyName = '';
	this.jobTitle = '';
	this.jobLevel = '';
	this.salary = '';
	this.jobInfo = '';
	this.startDate = '';
	this.endDate = '';
	this.levelReason = '';
//	this.createUserId = '';
//	this.createDate = '';
//	this.updateUserId = '';
//	this.updateDate = '';

	this.initWorkExperience = function(data){
		
		if (data.hasOwnProperty(MString.ID)) {
			this.id = data.id;
		}

		if (data.hasOwnProperty(MString.PERSONID)) {
			this.personId = data[MString.PERSONID];
		}

		if (data.hasOwnProperty(MString.COMPANYNAME)) {
			this.companyName = data[MString.COMPANYNAME];
		}
				
		if (data.hasOwnProperty(MString.JOBTITLE)) {
			this.jobTitle = data[MString.JOBTITLE];
		}
		
		if (data.hasOwnProperty(MString.JOBLEVEL)) {
			this.jobLevel = data[MString.JOBLEVEL];
		}
		
		if (data.hasOwnProperty(MString.SALARY)) {
			this.salary = data[MString.SALARY];
		}
				
		if (data.hasOwnProperty(MString.JOBINFO)) {
			this.jobInfo = data[MString.JOBINFO];
		}
				
		if (data.hasOwnProperty(MString.STARTDATE)) {
			this.startDate = data[MString.STARTDATE];
		}
		
		if (data.hasOwnProperty(MString.ENDDATE)) {
			this.endDate = data[MString.ENDDATE];
		}
				
		if (data.hasOwnProperty(MString.LEVELREASON)) {
			this.levelReason = data[MString.LEVELREASON];
		}
				
	}
	
	this.getUpdateJson = function(nRow){
		var data = {};
		
		
		
		return data;
	}
}


WorkExperienceDao ={
	editArray:[],

	deleteArray:[],
	
	initTable:function(){
	 	var aoColumnsV = [
 	 		{ "sTitle": "公司名称",sWidth: '95px',sClass:"table-column-center" }, 
	        { "sTitle": "部门/职务",sWidth: '95px',sClass:"table-column-center" },
	        { "sTitle": "职级",sWidth: '80px',sClass:"table-column-center" },
	        { "sTitle": "薪资",sWidth: '95px',sClass:"table-column-center" },
  	        { "sTitle": "工作内容",sWidth: null,sClass:"table-column-center" },
	        { "sTitle": "开始时间",sWidth: '80px',sClass:"table-column-center" },
	        { "sTitle": "结束时间",sWidth: '80px',sClass:"table-column-center" },
	        { "sTitle": "离职原因",sWidth: '80px',sClass:"table-column-center" },
	        { "sTitle": "",sWidth: '100px',sClass:"table-column-center" }
	 	];
	 	
	 	var columnSize = aoColumnsV.length;
	 	var lastColumn = columnSize - 1;
	 	var lastColumnArray = [];
	 	lastColumnArray.push(lastColumn);
	 	
	 	var emptyArray = [];
            
        for(var i=0;i<columnSize;i++){
        	emptyArray.push('');
        }
	 	
	 	var aoColumnDefsV = [
            {'bSortable': false,'aTargets': lastColumnArray}
	 	];
	 	
 		var oTable = oTable.dataTable({
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
	
		var oTable = $("#workExperienceTable");
		oTable.hide();
		
		var oTableNew = $('#workExperienceTable_new'); 
		
        oTableNew.click(function (e) {
            e.preventDefault();
            
            if (!jQuery().dataTable) {
				return;
			}
            
            if(!oTable.is(":visible")){
				oTable.show();
			}	            
            
            var aiNew = oTable.fnAddData(emptyArray);
        	var nRow = oTable.fnGetNodes(aiNew[0]);
            WorkExperienceDao.addHRRow(oTable, nRow);
        });
        
		oTable.find('a#remove').live('click', function (e) {
            e.preventDefault();
			
            var nRow = $(this).parents('tr')[0];
            var nId = $(this).attr("href");
  			if(nId == -1){
  				var tHR = new HR();
  				var isChanged = tHR.isChanged(nRow);
  	            if(!isChanged){
  	            	oTable.fnDeleteRow(nRow);
	  	            return;
  	            }
 			}
  			
			if (confirm("是否删除这条工作经历？") == false) {
            	return;
       		}	
            
            if(nId == -1){
            	oTable.fnDeleteRow(nRow);
            }else{
            	WorkExperienceDao.deleteHRById(nRow,nId);
            }
            
            var nRows = oTable.dataTable().fnGetNodes();
            if(nRows.length <=0){
            	oTable.hide();
            }
        });
	},
	
	initTableData:function(oTable,dataList){
		WorkExperienceDao.editArray.length = 0;
		if(dataList.length<=0){
			oTable.hide();
		}else{
			oTable.show();
			var oDataTable = oTable.dataTable();
			var isEditing = false;
			for(i=0;i<dataList.length;i++){
				var aiNew = oDataTable.fnAddData(WorkExperienceDao.createTableItem(dataList[i]));
				var nRow = oDataTable.fnGetNodes(aiNew[0]);
				WorkExperienceDao.initRowData(oDataTable,nRow,dataList[i]);
				WorkExperienceDao.initRowEvent(oDataTable,nRow,dataList[i]);
			};
			
			ContactDao.addContactDeleteListener($('#oTable'));
		}
	},
    
    createTableItem:function(item){
		var dataArray =[];
		
		if(nowUser.role == 127){
			var nameHTML = '<input type="text" class="m-wrap small wiki" value="' + item.name + '"/>'+MStringF.createSpan(item.name);
			var EnglishNameHTML = '<input type="text" class="m-wrap small wiki" value="' + item.EnglishName + '"/>'+MStringF.createSpan(item.EnglishName);
			
			var genderHTML = '<select class="m-wrap small wiki" data-placeholder="Choose a Category" tabindex="1">'
				+'<option value="-1">请选择</option>'
				+'<option value="0">女</option>'
				+'<option value="1">男</option>'
				+'</select>'
				+MStringF.createSpan(item.getGenderString());
			var occupationHTML = '<input type="text" class="m-wrap small wiki" value="'   + item.occupation + '"/>'+MStringF.createSpan(item.occupation);
			var isWorkingHTML = '<select class="m-wrap small wiki" data-placeholder="Choose a Category" tabindex="1">'
				+ '<option value="-1">请选择</option>'
				+'<option value="0">离职</option>'
				+'<option value="1">在任</option>'
				+'</select>'
				+MStringF.createSpan(item.isWorkingString());
			
			var buttonHTML = '<a id="edit" href="#" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
			buttonHTML+= '<a id="remove" style="margin-left: 10px;" href="'+item.id+'" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
			
			dataArray.push(nameHTML);
			dataArray.push(EnglishNameHTML);
			dataArray.push(genderHTML);
			dataArray.push(occupationHTML);
			dataArray.push(item.getContactHtml());
			dataArray.push(isWorkingHTML);
			dataArray.push(buttonHTML);
		}else{
			dataArray.push(MStringF.createSpan(item.name));
			dataArray.push(MStringF.createSpan(item.EnglishName));
			dataArray.push(MStringF.createSpan(item.getGenderString()));
			dataArray.push(MStringF.createSpan(item.occupation));
			dataArray.push(item.getContactHtml());
			dataArray.push(MStringF.createSpan(item.isWorkingString()));
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
		ContactDao.addContactSelectListener(contactCol);
		//当点击添加时
		rowObject.find('#add').live('click',function(e){
			e.preventDefault();
			var ulObj = $(this).prev();
			ulObj.append(ContactDao.getNewContactHtml());
			ContactDao.addContactSelectListener($(this).parent());
		});
		
		rowObject.find('#edit').live('click',function(e){
			e.preventDefault();
			var trNow = $(this).closest("tr");
			var nowId = $(trNow).find('a:last').attr("href");
			
			if(WorkExperienceDao.editArray.indexOf(nowId)<0){
				WorkExperienceDao.editHRRow(trNow);
				WorkExperienceDao.editArray.push(nowId);
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
				
				WorkExperienceDao.restoreRow(nHRDataTable,nRow,nHR);
				WorkExperienceDao.initRowData(nHRDataTable,nRow,nHR);
				WorkExperienceDao.editArray.splice(WorkExperienceDao.editArray.indexOf(nowId),1);
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
					$("#oTable").dataTable().fnDeleteRow(nRow);
    	            var nRows = $("#oTable").dataTable().fnGetNodes();
		            if(nRows.length <=0){
		            	$("#oTable").hide();
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
			ulObj.append(ContactDao.getNewContactHtml());
			ContactDao.addContactSelectListener($(this).parent());
		});
		ContactDao.addContactDeleteListener('#oTable');
		ContactDao.addContactSelectListener($(this).parent());
	},
	
	editHRRow:function(trNow){
		var trNowObj = $(trNow);
		
		var spanLabel = trNowObj.find("span");
		spanLabel.hide();
		spanLabel.siblings().not("span").show();
		trNowObj.find("td").eq(4).find("a").show();
  	},
  	
  	restoreRow:function(nHRDataTable,nRow,nHR){
  		var aData = WorkExperienceDao.createTableItem(nHR);
        var jqTds = $('>td', nRow);
		
        for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
            nHRDataTable.fnUpdate(aData[i], nRow, i, false);
        }

        nHRDataTable.fnDraw();
	},
	
  	initHrTableData:function(oTable,dataList){
		if(dataList.length<=0){
			oTable.hide();
		}else{
			oTable.show();
			var oDataTable = oTable.dataTable();
			for(i=0;i<dataList.length;i++){
				var aiNew = oDataTable.fnAddData(WorkExperienceDao.createTableItem(dataList[i]));
			};
		}
	},
  	
	getWorkExperienceTableJSON: function(oTable){
		var dataListData = [];
		
		var nRows = oTable.dataTable().fnGetNodes();
		for(i=0;i<nRows.length;i++){
			var nId = $(nRows[i]).find("a:last").attr("href");
			nId = parseInt(nId);
	  		console.log("nId---------"+nId);
	  		console.log("editArray---------"+JSON.stringify(WorkExperienceDao.editArray));
			if(nId > 0){
				if(WorkExperienceDao.editArray.indexOf(String(nId))>=0){
  	  				console.log("start---------");
					var jqInputs = $(nRows[i]).find('input,select');
					if(jqInputs.length<=0) return;
  					var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
		  			if(!jQuery.isEmptyObject(hrItem)){
			  			dataListData.push(hrItem);
		  			}
				}
			}else{
				var jqInputs = $(nRows[i]).find('input,select');
				if(jqInputs.length<=0) return;
				var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
				if(!jQuery.isEmptyObject(hrItem)){
		  			dataListData.push(hrItem);
		  		}
			}
		}
  		console.log("dataListData---------"+JSON.stringify(dataListData));
		return dataListData;
	},
	
	getWorkExperienceTableUpdateJSON: function(oTable){
  		var dataListData = [];
		
		var nRows = oTable.dataTable().fnGetNodes();
		for(var i=0,iLen = nRows.length;i<iLen;i++){
			var nId = $(nRows[i]).find("a:last").attr("href");
			nId = parseInt(nId);
	  		console.log("nId---------"+nId);
	  		console.log("editArray---------"+JSON.stringify(WorkExperienceDao.editArray));
			if(nId > 0){
				if(WorkExperienceDao.editArray.indexOf(String(nId))>=0){
  	  				console.log("start---------");
					var jqInputs = $(nRows[i]).find('input,select');
					if(jqInputs.length>0){
	  					var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
			  			if(!jQuery.isEmptyObject(hrItem)){
				  			dataListData.push(hrItem);
			  			}
		  			}
				}
			}else{
				var jqInputs = $(nRows[i]).find('input,select');
				if(jqInputs.length>0){
					var hrItem = nowCompany.getHR(nId).getUpdateJSON(nRows[i]);
					if(!jQuery.isEmptyObject(hrItem)){
			  			dataListData.push(hrItem);
			  		}
				}
			}
		}
  		console.log("dataListData---------"+JSON.stringify(dataListData));
		return dataListData;
  	}



}
