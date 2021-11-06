function loadDashboard() {
    $.ajax(
        {
            "type": "GET",
            "url": `modules/dashboardContent.html`,
            "async": true
        }
    ).done(
        function (data) {
            // Loading the content
            $("#content").html(data);
        }
    );
}