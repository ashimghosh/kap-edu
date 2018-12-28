<%-- <!DOCTYPE html>
<html >
<head>
  <title>Login</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <link rel="stylesheet" href="resources/ss/BootstrapCssV3.3.7.css">
		<script src="resources/ss/jQueryv3.2.1.js"></script>
		<script src="resources/ss/BootstrapJsv3.3.7.js"></script>
		
   <link rel="stylesheet" href="resources/ss/FontAwesome4.4.0.css">
   <link rel="stylesheet" href="resources/ss/gfont.css">
  
  <style type="text/css">

 html { 
width: 100%; 
height:100%; 
overflow:hidden; }
body { 
background: linear-gradient(to bottom, #0099ff, #b3e0ff);
background-repeat: no-repeat;height:100%; 
} 

.form-signin
{
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
}
.form-signin .form-signin-heading, .form-signin .checkbox
{
    margin-bottom: 10px;
}
.form-signin .checkbox
{
    font-weight: normal;
}
.form-signin .form-control
{
    position: relative;
    font-size: 16px;
    height: auto;
    padding: 10px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}
.form-signin .form-control:focus
{
    z-index: 2;
}
.form-signin input[type="text"]
{
    margin-bottom: -1px;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
    height:35px !important;
}
.form-signin input[type="password"]
{
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
    height:35px !important;
}
.account-wall
{
    margin-top: 20px;
    padding: 40px 0px 20px 0px;
    background-color: #f7f7f7;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}
.login-title
{
    color: #555;
    font-size: 18px;
    font-weight: 400;
    display: block;
}
.profile-img
{
    width: 100px;
    height: 100px;
    margin: 0 auto 10px;
    display: block;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
}
.need-help
{
    margin-top: 10px;
}
.new-account
{
    display: block;
    margin-top: 10px;
}
.icno{
	font-size: 50px;
}

</style>


</head>
<body>

	 <div class="container">
	 <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <div class="panel-heading">
	               <div class="panel-title text-center">
	               		<h1 class="title" style="color: white;font-size: 35px;font-family: monospace;">Login To KAP</h1>
	               		<hr />
	               	</div>
	            </div>
	           
            <div class="well" style="box-shadow: 2px 2px 10px black;padding: 10%;margin: 5%;">
                 <img class="profile-img" src="resources/Image/admin.png" alt="" >
                    
                    <form class="form-horizontal form-signin" method="post" action="loginProcess.do">
                	<br><br>
                	<div class="form-group">
								
									<input type="text" class="form-control" name="username" placeholder="Registered Mobile Number" required="required" />
								
						</div>

						<div class="form-group">
								
									<input type="password" class="form-control" name="password" placeholder="Password" required="required" />
								
						</div>

				
							<div class="form-group ">
							<button type="submit" class="btn btn-info btn-block btn-large" style="background-color:#4da6ff;color: white; ">Log In  <i class="fa fa-sign-in"></i></button>
						</div>

                </form>
            </div>
        </div>
    </div>
<div style=" text-align: center; color: red;"><h3>${msg}</h3></div>
</body>
</html>
 --%>
 
 
<!DOCTYPE html>
<html lang="en">
<head>
	<title>kap-edu</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="resources/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="resources/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="resources/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="resources/css/login_util.css">
	<link rel="stylesheet" type="text/css" href="resources/css/login_main.css">
<!--===============================================================================================-->

</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-form-title" style="background-image: url(resources/images/bg-01.jpg);">
					<span class="login100-form-title-1">
						Sign In
					</span>
				</div>

				<form class="login100-form validate-form" method="post" action="loginProcess.do">
					<div class="wrap-input100 validate-input m-b-26" data-validate="Username is required">
						<span class="label-input100">Username</span>
						<input class="input100"     type="text" name="username" placeholder="Registered Mobile Number" required="required" maxlength="12">
						<!-- <input class="form-control" type="text" name="username" placeholder="Registered Mobile Number" required="required" /> -->
						<span class="focus-input100"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-18" data-validate = "Password is required">
						<span class="label-input100">Password</span>
						<input class="input100"     type="password" name="password" placeholder="Enter password" required="required" maxlength="25">
						<!-- <input class="form-control" type="password" name="password" placeholder="Password" required="required" /> -->
						<span class="focus-input100"></span>
					</div>

					<div class="flex-sb-m w-full p-b-30">
						<div class="contact100-form-checkbox">
							<input class="input-checkbox100" id="ckb1" type="checkbox" name="remember-me">
							<label class="label-checkbox100" for="ckb1">
								Remember me
							</label>
						</div>

						<div>
							<a href="forgotpassword.do" class="txt1">
								Forgot Password?
							</a>
						</div>
					</div>
					<div style="text-align: center; color: red;"><h7>${msg}</h7></div>
					<div class="container-login100-form-btn">
						<button type="submit" class="login100-form-btn">
							Login
						</button>
						<a href="index.jsp" class="txt1">
								<img alt="Back to Home" src="resources/images/website/home.png" width="50px">
						</a>						
						<!-- <button type="submit" class="btn btn-info btn-block btn-large" 
						style="background-color:#4da6ff;color: white; ">Log In  <i class="fa fa-sign-in"></i></button> -->
					</div>
				</form>
				
				<div align="center">For help contact &nbsp; +91-7872659206</div> 
						
			</div>
		</div>
	</div>
	
<!--===============================================================================================-->
	<script src="resources/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/vendor/bootstrap/js/popper.js"></script>
	<script src="resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="resources/vendor/daterangepicker/moment.min.js"></script>
	<script src="resources/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="resources/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="resources/js/login_main.js"></script>

</body>
</html>
 
 
 
 
 