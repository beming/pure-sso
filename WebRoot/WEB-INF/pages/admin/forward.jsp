<%@ page language="java" pageEncoding="UTF-8"%>
<div id="msg">请稍后……</div>
<script type="text/javascript">
function jump(){
$.ajax({
    type: 'POST',
    url: '${sys.sys_url}',
    cache: false,
    data: {${sys.login_field}:"${sys.sysuser_id}",${sys.pwd_field}:"${sys.sysuser_pwd}",randomNum:Math.random()},
    success: function(dat){
    	alert(dat);
    	$('#msg').html("Hello World");
    },
    error:$('#msg').html("Hello World, error")
});
}
jump();
</script>
