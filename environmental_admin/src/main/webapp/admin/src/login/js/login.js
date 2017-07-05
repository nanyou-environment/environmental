jQuery(document).ready(function() {

	document.onkeydown = function(event) {
		if (event.keyCode == 13) {
			login_in();
		}
	};
	
	$('#login').click(function() {
		login_in();
	})
	
	function check(){
		if($("#username").val()==null){
			alert('请输入手机号码！');
			return false;
		}else if($("#password").val()==null){
			alert('请输入密码！');
			return false;
		}
		return true;
	}

	function login_in() {
		if(check()){
			$.ajax({
				url : '../../system/login',
				type : 'post',
				data : {
					account : $("#username").val(),
					password : hex_md5($("#password").val())
				},
				success : function(data) {
					if (data.code == 200) {
						sessionStorage.setItem("token", data.token);
						sessionStorage.setItem("admin", JSON.stringify(data.data));
						location.href = 'index.html';
					} else {
						alert("用户名或密码错误！");
					}
				}
			})
		}
	}
});
