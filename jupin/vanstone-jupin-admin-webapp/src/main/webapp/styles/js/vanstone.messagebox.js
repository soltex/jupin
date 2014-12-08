var MESSAGEBOX_CONSTANTS = {
		Debug : 0,
		Info : 10,
		Warning : 20,
		Error : 30
}

$(document).ready(function() {
	var timerid = setInterval(retrievalAllMessage, 5000);
});

function retrievalAllMessage() {
	$.ajax(
		{
			type:"GET",
			url : "/common/messagebox/read-messages",
			dataType : "json",
			cache : false,
			global : false,
			success : function(data) {
				if (!data) {
					return;
				}
				var messageLevel = data.messageLevel;
				if (messageLevel == MESSAGEBOX_CONSTANTS.Error) {
					error(data.content);
				}else {
					error(data.content);
				}
			}
		}
	);
}
