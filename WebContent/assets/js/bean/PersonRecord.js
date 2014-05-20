function PersonRecord() {
	this.id = -1;
	this.personId = -1;
	this.userId = -1;
	this.date = '';
	this.content = "";
	this.type = -1;

	this.initPersonRecord = function(data) {
		//		console.log("CONTACT------> "+JSON.stringify(data));
		//		console.log("														");
		//		console.log("														");

		if (data.hasOwnProperty(MString.ID)) {
			this.id = data.id;
		}

		if (data.hasOwnProperty(MString.PERSONID)) {
			this.companyId = data[MString.PERSONID];
		}

		if (data.hasOwnProperty(MString.USERID)) {
			this.createUserId = data[MString.USERID];
		}

		if (data.hasOwnProperty(MString.DATE)) {
			this.date = data[MString.DATE];
		}

		if (data.hasOwnProperty(MString.CONTENT)) {
			this.content = data.content;
		}
		
		if (data.hasOwnProperty(MString.TYPE)) {
			this.type = data.type;
		}
	}

	this.isChanged = function(nRow){
		var change = false;
		var recordItem = {};
		var nRowObj = $(nRow);
		
		var tDate = nRowObj.children().eq(0).find("input").val();
		var tContent = nRowObj.children().eq(2).find("textarea").val();
		
		if(tDate!=this.date){
			console.log("tDate--------"+ tDate + "---------------"+this.date);
			change = true;
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
		var tContent = nRowObj.children().eq(1).find("textarea").val();
		
		if(tDate!=this.date){
			recordItem[MString.DATE] = tDate;
		}
		
		if(tContent!=this.content){
			recordItem[MString.CONTENT] = tContent;
		}
		
		if(!jQuery.isEmptyObject(recordItem)){
			recordItem[MString.ID] = this.id;
			if(this.id <=0){
				recordItem[MString.TYPE] = type;	
				recordItem[MString.PERSONID] = nowPerson.id;	
				recordItem[MString.USERID] = nowUser.id;	
			}
		}
		
		return recordItem;
	}
}

PersonRecordDao = {
	editArray:[],

	deleteArray:[],
	
	addRow:function(oTable, nRow) {
        var aData = oTable.fnGetData(nRow);
        var jqTds = $('>td', nRow);
        var nPersonRecord = new PersonRecord();

        jqTds[0].innerHTML = '<input class="m-wrap auto wiki date-picker" size="16" type="text" value=""/>';
        jqTds[1].innerHTML = '<textarea class="span10 m-wrap" rows="4" value=""></textarea>';
        jqTds[2].innerHTML = '<a id="remove" href="-1" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
        
        PersonRecordDao.initRowEvent(oTable,nRow,null);
	},
	
	initPersonRecordTable : function(oPersonRecordTable, oPersonRecordTable_New, oRemove, type) {
		var recordTable = oPersonRecordTable.dataTable({
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
				"sZeroPersonRecords" : "对不起，查询不到相关数据！",
				"sEmptyTable" : "表中无数据存在！",
				"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				"sInfoEmpty": '数据表为空',
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
		oPersonRecordTable.hide();

		oPersonRecordTable_New.click(function(e) {
			e.preventDefault();

			if (!jQuery().dataTable) {
				return;
			}

			if (!oPersonRecordTable.is(":visible")) {
				oPersonRecordTable.show();
			}

  			var aiNew = recordTable.fnAddData(['', '', '', '']);
  			var nRow = recordTable.fnGetNodes(aiNew[0]);
            PersonRecordDao.addRow(recordTable, nRow);
  			nEditing = nRow;
		});

		oRemove.live('click', function(e) {
			e.preventDefault();
			
			var nRow = $(this).parents('tr')[0];
            var nId = $(this).attr("href");
  			if(nId == -1){
  				var tPersonRecord = new PersonRecord();
  				var isChanged = tPersonRecord.isChanged(nRow);
  	            if(!isChanged){
  	            	oPersonRecordTable.fnDeleteRow(nRow);
	  	            return;
  	            }
 			}
  			
			if (confirm("是否删除这条记录？") == false) {
            	return;
       		}	
            
            if(nId == -1){
            	oPersonRecordTable.fnDeleteRow(nRow);
            }else{
            	PersonRecordDao.deletePersonRecordById(oPersonRecordTable,nRow,nId);
            }
            
            var nRows = oPersonRecordTable.fnGetNodes();
            if(nRows.length <=0){
            	oPersonRecordTable.hide();
            }
		});
	},

	initPersonRecordTableData : function(recordTable, recordList) {
		if (recordList.length <= 0) {
			recordTable.hide();
		} else {
			recordTable.show();
			var recordDataTable = recordTable.dataTable();
			for ( i = 0; i < recordList.length; i++) {
  				var aiNew = recordDataTable.fnAddData(PersonRecordDao.createTableItemData(recordList[i]));
				
				var nRow = recordDataTable.fnGetNodes(aiNew[0]);
				PersonRecordDao.initRowData(recordDataTable,nRow,recordList[i]);
				PersonRecordDao.initRowEvent(recordDataTable,nRow,recordList[i]);
			};
		}
	},
	
    createTableItemData:function(record){
		var dataArray =[];
		
		if(nowUser.role == 127){
  			var dateHTML = '<input class="m-wrap auto wiki date-picker" size="16" type="text" value="' + record.date + '"/>'+MStringF.createSpan(record.date);
			var contentHTML = '<textarea class="span10 m-wrap" rows="4" value="' + record.content + '"></textarea>'+MStringF.createSpan(record.content);
			
			var buttonHTML = '<a id="edit" href="'+record.id+'" class="btn green icn-only"><i class="icon-edit icon-white"></i></a>';
  			buttonHTML += '<a id="remove" style="margin-left: 10px;" href="'+record.id+'" class="btn red icn-only"><i class="icon-remove icon-white"></i></a>';
			
			dataArray.push(dateHTML);
			dataArray.push(contentHTML);
			dataArray.push(buttonHTML);
		}else{
			dataArray.push(MStringF.createSpan(record.date));
			dataArray.push(MStringF.createSpan(record.content));
			dataArray.push('');
		}
		return dataArray;
	},
	
	initRowData:function(nPersonRecordDataTable,nRow,nPersonRecord){
		var rowObject = $(nRow);
  		
  		var spanLabel = rowObject.find("span");
  		spanLabel.siblings().not("span").hide();
		
		var nDate = rowObject.find("input").eq(0);
		nDate.val(nPersonRecord.date);
		
		var nContent = rowObject.children().eq(1).find("textarea");
		nContent.val(nPersonRecord.content);
		
	},
	
	initRowEvent:function(nPersonRecordDataTable,nRow,nPersonRecord){
		var rowObject = $(nRow);
		
		$('.date-picker',rowObject).datepicker();
		$('.date-picker',rowObject).datepicker();
		
  		rowObject.find('#edit').live('click',function(e){
  			e.preventDefault();
  			
  			var trNow = $(this).closest("tr");
  			var nowId = $(this).attr("href");
  			
  			if(PersonRecordDao.editArray.indexOf(nowId)<0){
  				PersonRecordDao.editPersonRecordRow(trNow);
  				PersonRecordDao.editArray.push(nowId);
  			}else{
  				var isChanged = nPersonRecord.isChanged(trNow);
  				if(isChanged){
  					if (confirm("是否取消修改？") == false) {
  		                return;
  		            }
  				}
  				
  				PersonRecordDao.restoreRow(nPersonRecordDataTable,nRow,nPersonRecord);
  				PersonRecordDao.initRowData(nPersonRecordDataTable,nRow,nPersonRecord);
  				PersonRecordDao.editArray.splice(PersonRecordDao.editArray.indexOf(nowId),1);
  			}
  		});
	},
	
	editPersonRecordRow:function(trNow){
		var trNowObj = $(trNow);
		
		var spanLabel = trNowObj.find("span");
		spanLabel.hide();
		spanLabel.siblings().not("span").show();
  	},
  	
	restoreRow:function(nPersonRecordDataTable,nRow,nPersonRecord){
  		console.log("restoreRow-------------------");
  		var aData = PersonRecordDao.createTableItemData(nPersonRecord);
        var jqTds = $('>td', nRow);
		
        for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
            nPersonRecordDataTable.fnUpdate(aData[i], nRow, i, false);
        }
		
        nPersonRecordDataTable.fnDraw();
	},
	
	getTableJSON : function(recordTable,type){
		var recordListData = [];
		
		var nRows = recordTable.dataTable().fnGetNodes();
		var i=0;
		for(i=0;i<nRows.length;i++){
  			var nId = $(nRows[i]).find("a:last").attr("href");
  			nId = parseInt(nId);
			var recordItem = {};
			
			if(PersonRecordDao.editArray.indexOf(String(nId))>=0 || nId<0){
  				console.log("start---------");
				var jqInputs = $(nRows[i]).find('input,textarea,select');
				if(jqInputs.length>0){
					var recordItem = {};
  					recordItem = nowPerson.getRecord(nId).getUpdateJSON(nRows[i],type);
	  			}
			}
  			if(!jQuery.isEmptyObject(recordItem)){
	  			recordListData.push(recordItem);
  			}
		}
		
  		console.log("recordListData---------"+JSON.stringify(recordListData));
		return recordListData;
	},

	deletePersonRecordById:function(nTable,nRow,nId){
		var deleteUrl = MainUrl.DOMAIN + MainUrl.URL_PERSON_RECORD;
		var nPersonRecordData = {};
		var postData = {};
		nPersonRecordData[MString.ID] = nId;
		postData[MainUrl.ACTION_DATA] = MStringF.getPostJson(nPersonRecordData);
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
