$(document).on("click", ".confirm", function(e) {
	var link = $(this).attr("href");
	console.log(link)
	e.preventDefault();
	bootbox.confirm({
		title : "確認刪除紀錄",
		message : "Are you sure?",
		callback : function(result) {
			if (result) {
				window.location = link;
			}
		},
		size : "big"
	});
});