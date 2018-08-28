/*
/!**
 * Created by 97390 on 8/28/2018.
 *!/
/!*VUE*!/
login: function (event) {
//alert(localStorage.getItem("ctoken"));
    var data = "account="+vm.username+"&password="+vm.password+"&captcha="+vm.captcha+"&ctoken="+localStorage.getItem("ctoken");
    $.ajax({
        type: "POST",
        url: basePath+"api/login",
        headers:{'token':localStorage.getItem("token")},
        data: data,
        dataType: "json",
        success: function(result){
            //alert(result.code);
            if(result.code == 0){//登录成功
                var token=result.token;
                var expire=result.expire;
                localStorage.setItem("token",token);
                parent.location.href = 'sysindex.html';
            }else{
                vm.error = true;
                vm.errorMsg = result.msg;

                vm.refreshCode();
            }
        }
    });
}*/
