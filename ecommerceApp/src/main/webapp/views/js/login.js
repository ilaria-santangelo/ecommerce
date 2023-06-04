$(document).ready(function() {
    $("#loginForm").submit(function(e) {
        e.preventDefault(); // Prevent the form from submitting normally

        var formdata = $(this).serialize();
        $.ajax({
            url: "/LoginServlet",
            type: "POST",
            data: formdata,
            success: function(response) {
                if (response === "success") {
                    alert("Login successful");
                    // Redirect to another page
                    window.location.href = "dashboard.html";
                } else if (response === "failure") {
                    alert("Login failed. Please check your email and password.");
                } else {
                    alert("An error occurred during login.");
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
                alert("An error occurred during login.");
            }
        });
    });
});
