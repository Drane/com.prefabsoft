	// The root URL for the RESTful services
	var rootURL = "http://localhost:8080/prefabsoft.com/itemtags";

	function findAll() {
		console.log('findAll');
		$.ajax({
			type : 'GET',
			url : rootURL,
			dataType : "json", // data type of response
			success : logData,
			error: function(jqXHR, textStatus, errorThrown){
				alert('findAll error: ' + textStatus);
			}
		});
	}

	function logData(data) {
		var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
		console.log("data: " + list.toString());
		
		$.each(list, function(intIndex, objValue){
			$('#testList').append($('<li/>', {    //here appendin `<li>`
			   // 'data-role': "list-divider"
			}).append($('<a/>', {    //here appending `<a>` into `<li>`
			    'href': 'test.html',
			    'data-transition': 'slide',
			    'text': objValue.name
			})));
		});
		$('#testList').listview('refresh');
	}
	findAll();