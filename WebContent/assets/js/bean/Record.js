RecordType = {
	HISTORY : 0,
	PLAN : 1
}

function Record() {
	this.id = -1;
	this.companyId = -1;
	this.type = -1;
	this.date = '';
	this.hrId = -1;
	this.createUserId = -1;
	this.content = "";

	this.initRecord = function(data) {
		//		console.log("CONTACT------> "+JSON.stringify(data));
		//		console.log("														");
		//		console.log("														");

		if (data.hasOwnProperty(MString.ID)) {
			this.id = data.id;
		}

		if (data.hasOwnProperty(MString.COMPANYID)) {
			this.companyId = data.companyId;
		}

		if (data.hasOwnProperty(MString.TYPE)) {
			this.type = data.type;
		}

		if (data.hasOwnProperty(MString.DATE)) {
			this.date = data.date;
		}

		if (data.hasOwnProperty(MString.HRID)) {
			this.hrId = data[MString.HRID];
		}

		if (data.hasOwnProperty(MString.CREATEUSERID)) {
			this.createUserId = data.createUserId;
		}

		if (data.hasOwnProperty(MString.CONTENT)) {
			this.content = data.content;
		}
	}

	this.getHR = function(hrList) {
		//		console.log("										");
		//		console.log("hrId--->"+this.hrId);
		//		console.log("hrList--->"+JSON.stringify(hrList));
		var result = MString.UNKNOWN;
		if (hrList == null || hrList.size <= 0) {
			return result;
		}

		var i = 0;
		for ( i = 0; i < hrList.length; i++) {
			if (this.hrId == hrList[i].id) {
				result = hrList[i].name;
				if (result == null || result.length <= 0) {
					result = hrList[i].EnglishName;
				}
				break;
			}
		}
		return result;
	}
	
	this.isChanged = function(nRow){
		var change = false;
		var recordItem = {};
		var nRowObj = $(nRow);
		
		var tDate = nRowObj.children().eq(0).find("input").val();
		var tHRId = nRowObj.children().eq(1).find("select").val();
		var tContent = nRowObj.children().eq(2).find("textarea").val();
		
		if(tDate!=this.date){
			console.log("tDate--------"+ tDate + "---------------"+this.date);
			change = true;
		}else if(tHRId != this.hrId){
			change = true;
			console.log("tHRId--------   "+ tHRId + "  ---------------"+this.hrId);
		}else if(tContent != this.content){
			change = true;
			console.log("tContent--------"+ tContent + "---------------"+this.content);
		}
		
		return change;
	}
	
	this.getUpdateJSON = function(nRow,type){
		var recordItem = {};
		var nRowObj = $(nRow);
		
		var tDate = nRowObj.children().eq(0).find("input").val();
		var tHRId = nRowObj.children().eq(1).find("select").val();
		var tContent = nRowObj.children().eq(2).find("textarea").val();
		
		if(tDate!=this.date){
			recordItem[MString.DATE] = tDate;
		}
		
		if(tHRId!=this.hrId){
			recordItem[MString.HRID] = tHRId;
		}
		
		if(tContent!=this.content){
			recordItem[MString.CONTENT] = tContent;
		}
		
		if(!jQuery.isEmptyObject(recordItem)){
			recordItem[MString.ID] = this.id;
			if(this.id <=0){
				recordItem[MString.TYPE] = type;	
				recordItem[MString.COMPANYID] = nowCompany.id;	
				recordItem[MString.CREATEUSERID] = nowUser.id;	
			}
		}
		
		return recordItem;
	}
}

RecordDao = {
	editArray:[],

	deleteArray:[],
	
	addRow:function(oTable, nRow) {
        var aData = oTable.fnGetData(nRow);
        var jqTds = $('>td', nRow);
        var nRecord = new Record();
        var tHRList = nowCompany.hrList;

		var hrHTML = '<select class="m-wrap auto wiki" data-placeholder="Choose a Category" tabindex="1" name="rHR">' + '<option value="-1">请选择</option>';
		for (var i = 0; i < tHRList.length; i++) {
			hrHTML += '<option value="' + tHRList[i].id + '">' + tHRList[i].getShowName() + '</option>'
		}
		hrHTML += '</select>';

        jqTds[0].innerHTML = '<input class="m-wrap auto wiki date-picker" size="16" type="text" value=""/>';
        jqTds[1].innerHTML = hrHTML;
        jqTds[2].innerHTML = '<textarea class="span10 m-wrap" rows="4" value=""></textarea>';
        jqTds[3].innerHTML = '<a id="remove" href="-1" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
        
        RecordDao.initRowEvent(oTable,nRow,null);
	},
	
	initRecordTable : function(oRecordTable, oRecordTable_New, oRemove, type) {
		var recordTable = oRecordTable.dataTable({
			bSort : false,
			bFilter : false,
			bLengthChange : false,
			bPaginate : false,
			"bJQueryUI" : true, //Enable smooth theme
			"sPaginationType" : "full_numbers", //Enable smooth theme
			"sDom" : 't',
			"bInfo" : false,
			"oLanguage" : {
				"sProcessing" : "正在加载中......",
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "对不起，查询不到相关数据！",
				"sEmptyTable" : "表中无数据存在！",
				"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
				"sSearch" : "搜索",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "上一页",
					"sNext" : "下一页",
					"sLast" : "末页"
				}
			},

			"aoColumns" : [{
				sTitle : "时间",
				sWidth : '120px',
				sClass : "table-column-center"
			}, {
				sTitle : "HR",
				sWidth : '100px',
				sClass : "table-column-center"
			}, {
				sTitle : "内容",
				sWidth : null,
				sClass : "table-column-center"
			}, {
				sTitle : "",
				sWidth : '100px',
				sClass : "table-column-center"
			}],
			"aoColumnDefs" : [{
				'bSortable' : false,
				'aTargets' : [0]
			}]
		});
		oRecordTable.hide();

		oRecordTable_New.click(function(e) {
			e.preventDefault();

			var tHRList = nowCompany.hrList;
			if (tHRList.length <= 0) {
				alert("请先添加HR！");
				return;
			}

			if (!jQuery().dataTable) {
				return;
			}

			if (!oRecordTable.is(":visible")) {
				oRecordTable.show();
			}

  			var aiNew = recordTable.fnAddData(['', '', '', '']);
  			var nRow = recordTable.fnGetNodes(aiNew[0]);
//			RecordDao.editRecordRow(recordTable, nRow, tHRList, type);
            RecordDao.addRow(recordTable, nRow);
  			nEditing = nRow;
  			
//			var aiNew = hrTable.fnAddData(['', '', '','','','','']);
//      	var nRow = hrTable.fnGetNodes(aiNew[0]);
//          HRDao.addHRRow(hrTable, nRow);
//          nEditing = nRow;
		});

		oRemove.live('click', function(e) {
			e.preventDefault();
			
			var nRow = $(this).parents('tr')[0];
            var nId = $(this).attr("href");
  			if(nId == -1){
  				var tRecord = new Record();
  				var isChanged = tRecord.isChanged(nRow);
  	            if(!isChanged){
  	            	oRecordTable.fnDeleteRow(nRow);
	  	            return;
  	            }
 			}
  			
			if (confirm("是否删除这条记录？") == false) {
            	return;
       		}	
            
            if(nId == -1){
            	oRecordTable.fnDeleteRow(nRow);
            }else{
            	RecordDao.deleteRecordById(oRecordTable,nRow,nId);
            }
            
            var nRows = oRecordTable.fnGetNodes();
            if(nRows.length <=0){
            	oRecordTable.hide();
            }
			
			
//			if (confirm("是否删除本条记录？") == false) {
//				return;
//			}
//
//			var nRow = $(this).parents('tr')[0];
//			recordTable.fnDeleteRow(nRow);
//			var nRows = recordTable.fnGetNodes();
//			if (nRows.length <= 0) {
//				oRecordTable.hide();
//			}
		});
	},

	initRecordTableData : function(recordTable, recordList) {
		if (recordList.length <= 0) {
			recordTable.hide();
		} else {
			recordTable.show();
			var recordDataTable = recordTable.dataTable();
			for ( i = 0; i < recordList.length; i++) {
  				var aiNew = recordDataTable.fnAddData(RecordDao.createTableItemData(recordList[i]));
				
				var nRow = recordDataTable.fnGetNodes(aiNew[0]);
				RecordDao.initRowData(recordDataTable,nRow,recordList[i]);
				RecordDao.initRowEvent(recordDataTable,nRow,recordList[i]);
			};
		}
	},
	
    createTableItemData:function(record){
		var dataArray =[];
		
		var tHRList = nowCompany.hrList;
		if(nowUser.role == 127){
  			var dateHTML = '<input class="m-wrap auto wiki date-picker" size="16" type="text" value="' + record.date + '"/>'+MStringF.createSpan(record.date);
			var hrHTML = '<select class="m-wrap auto wiki" data-placeholder="Choose a Category" tabindex="1" name="rHR">' + '<option value="-1">请选择</option>';
			for (var i = 0; i < tHRList.length; i++) {
				hrHTML += '<option value="' + tHRList[i].id + '">' + tHRList[i].getShowName() + '</option>'
			}
			hrHTML += '</select>';
			hrHTML += MStringF.createSpan(record.getHR(tHRList));
			
			var contentHTML = '<textarea class="span10 m-wrap" rows="4" value="' + record.content + '"></textarea>'+MStringF.createSpan(record.content);
			
			var buttonHTML = '<a id="edit" href="'+record.id+'" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
  			buttonHTML += '<a id="remove" style="margin-left: 10px;" href="'+record.id+'" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
			
			dataArray.push(dateHTML);
			dataArray.push(hrHTML);
			dataArray.push(contentHTML);
			dataArray.push(buttonHTML);
		}else{
			dataArray.push(MStringF.createSpan(record.date));
			dataArray.push(MStringF.createSpan(record.getHR(tHRList)));
			dataArray.push(MStringF.createSpan(record.content));
			dataArray.push('');
		}
		return dataArray;
	},
	
	initRowData:function(nRecordDataTable,nRow,nRecord){
		var rowObject = $(nRow);
  		
  		var spanLabel = rowObject.find("span");
  		spanLabel.siblings().not("span").hide();
		
		var nDate = rowObject.find("input").eq(0);
		nDate.val(nRecord.date);
		
		var nHR = rowObject.children().eq(1).find("select");
		nHR.val(nRecord.hrId);
		
		var nContent = rowObject.children().eq(2).find("textarea");
		nContent.val(nRecord.content);
		
	},
	
	initRowEvent:function(nRecordDataTable,nRow,nRecord){
		var rowObject = $(nRow);
		
		$('.date-picker',rowObject).datepicker();
		$('.date-picker',rowObject).datepicker();
		
  		rowObject.find('#edit').live('click',function(e){
  			e.preventDefault();
  			
  			var trNow = $(this).closest("tr");
  			var nowId = $(this).attr("href");
  			
  			if(RecordDao.editArray.indexOf(nowId)<0){
  				RecordDao.editRecordRow(trNow);
  				RecordDao.editArray.push(nowId);
  			}else{
  				var isChanged = nRecord.isChanged(trNow);
  				if(isChanged){
  					if (confirm("是否取消修改？") == false) {
  		                return;
  		            }
  				}
  				
  				RecordDao.restoreRow(nRecordDataTable,nRow,nRecord);
  				RecordDao.initRowData(nRecordDataTable,nRow,nRecord);
  				RecordDao.editArray.splice(RecordDao.editArray.indexOf(nowId),1);
  			}
  		});
	},
	
	editRecordRow:function(trNow){
		var trNowObj = $(trNow);
		
		var spanLabel = trNowObj.find("span");
		spanLabel.hide();
		spanLabel.siblings().not("span").show();
  	},
  	
	restoreRow:function(nRecordDataTable,nRow,nRecord){
  		console.log("restoreRow-------------------");
  		var aData = RecordDao.createTableItemData(nRecord);
        var jqTds = $('>td', nRow);
		
        for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
            nRecordDataTable.fnUpdate(aData[i], nRow, i, false);
        }
		
        nRecordDataTable.fnDraw();
	},
	
	getTableJSON : function(recordTable,type){
		var recordListData = [];
		
		var nRows = recordTable.dataTable().fnGetNodes();
		var i=0;
		for(i=0;i<nRows.length;i++){
  			var nId = $(nRows[i]).find("a:last").attr("href");
  			nId = parseInt(nId);
			var recordItem = {};
			
			if(RecordDao.editArray.indexOf(String(nId))>=0 || nId<0){
  				console.log("start---------");
				var jqInputs = $(nRows[i]).find('input,textarea,select');
				if(jqInputs.length>0){
					var recordItem = {};
					if(type == RecordType.HISTORY){
  	  					recordItem = nowCompany.getRecord(nId).getUpdateJSON(nRows[i],type);
					}else{
  	  					recordItem = nowCompany.getRecordPlan(nId).getUpdateJSON(nRows[i],type);
					}
	  			}
			}
  			if(!jQuery.isEmptyObject(recordItem)){
	  			recordListData.push(recordItem);
  			}
		}
		
  		console.log("recordListData---------"+JSON.stringify(recordListData));
		return recordListData;
	},

	deleteRecordById:function(nTable,nRow,nId){
		var deleteUrl = MainUrl.DOMAIN + MainUrl.URL_RECORD;
		var nRecordData = {};
		var postData = {};
		nRecordData[MString.ID] = nId;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(nRecordData);
		postData[MainUrl.ACTION] = MainUrl.ACTION_DELETE;
		postData[MString.TABLE_USER] = MStringF.getPostJson(nowUser);
		
		$.post(deleteUrl,postData,function(data,status){
			console.log("1--->"+JSON.stringify(data));
			if(data!=null){
				if(data[MainUrl.STATUS] == MainUrl.RESULT_CODE_SUCCESS){
					nTable.dataTable().fnDeleteRow(nRow);
  	            	var nRows = nTable.dataTable().fnGetNodes();
		            if(nRows.length <=0){
		            	nTable.hide();
		            }
				}else{
					alert(data[MainUrl.KEY_MESSAGE]);  						
				}
			}else{
				alert("连接服务器失败！");
			}
		});
	}
}
