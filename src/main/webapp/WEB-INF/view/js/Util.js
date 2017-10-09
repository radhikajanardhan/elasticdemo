/**
 * 
 */
$( function() {
    $( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });
} );

function fetchDetailForRecord(target) {
    $('.row').each( function() {
        if ($(this).hasClass('selectedRow')) {
            $(this).removeClass('selectedRow');
        }
    });
    target.addClass('selectedRow');
    $.ajax({
        type: "GET",
        url: "/ElasticDemo/rest/event/fetchEvent?id="+target.children(":first").text(),
        success: function(data){
            $('#detailsTable').show();
            $('#txtDate').html(data.event_date);
            $('#txtType').html(data.event_type);
            $('#txtSize').html(data.event_size);
            $('#txtSummary').html(data.event_summary);
            $('#txtDetails').html(data.event_details);
        },
        error: function(e) { }
    });
}

function getEvents() {
    $( "#datepicker" ).datepicker("hide");
    $.ajax({
		type: "GET",
        url: "/ElasticDemo/rest/event/count?startDate=" +
                $("#datepicker").datepicker().val()+"&period="+$('#datePeriod').find(":selected").text(),
		success: function(data){
            var pages = 1;
            if(data.totalEvents > 25) {
                pages = Math.ceil(data.totalEvents/25);
            }

            if(data.totalEvents > 0) {
                $('.header').show();
            }
            if(data.totalEvents > 0) {
                $('#pagination').empty();
                $('#pagination').remove();
                $('#paginationBox').append($('<ul id="pagination"></ul>').addClass("pagination-sm paginationControl"));
                showPaginator(pages);
            }
		},
		error: function(e) { }
	});

}

function showPaginator(totalPages) {
    $('#pagination').twbsPagination({
        totalPages: totalPages,
        visiblePages: totalPages <= 5 ? totalPages : 5,
        onPageClick: function (event, page) {
            $.ajax({
                type: "GET",
                url: "/ElasticDemo/rest/event/fetchAll?pageNumber=" + page + "&startDate=" +
                    $("#datepicker").datepicker().val() + "&period=" + $('#datePeriod').find(":selected").text(),
                success: function (data) {
                    $('.dataRow').empty();
                    $('.dataRow').remove();
                    $.each(data.events, function (i, obj) {
                        var $row = $('<div style="display:none;" onclick=\"fetchDetailForRecord($(this))\">').addClass("row dataRow");
                        $row.append($('<div style="display:none;">'+ obj._id + '</div>').addClass("cell"));
                        $row.append($('<div>'+ obj.event_date + '</div>').addClass("cell"));
                        $row.append($('<div>'+ obj.event_type + '</div>').addClass("cell"));
                        $row.append($('<div>'+ obj.event_summary + '</div>').addClass("cell"));
                        $row.append($('<div>'+ obj.event_size + '</div>').addClass("cell"));
                        $('.table').append($row);
                    });
                    showRows();

                },
                error: function (e) { }
            });
        }
    });
}

function showRows() {
    $('.row').each( function() {
        if (! ($(this).attr('id') || $(this).hasClass('header'))) {
            $(this).show();
        }
    });
}