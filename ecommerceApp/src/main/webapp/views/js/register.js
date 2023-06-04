<script>
    $(document).ready(function(){
        $("#signupButton").click(function(e){
            e.preventDefault();
            var formdata = $("#signupForm").serialize();
            $.ajax({
                url: "/UserServlet",
                type: "POST",
                data: formdata,
                success: function(response){
                    if (response.trim() === "success") {
                        alert("User registered successfully");
                        window.location.href = "register.html?registered=true";
                    } else {
                        alert("Registration error");
                    }
                },    
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(textStatus, errorThrown);
                }
            });
        });

        function checkRegistration() {
            const urlParams = new URLSearchParams(window.location.search);
            const registered = urlParams.get('registered');
            if (registered) {
                $('label[for=chk]').click();
            }
        }
    });
</script>