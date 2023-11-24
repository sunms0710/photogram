//회원정보 수정
function update(userId,event) {
    event.preventDefault();
    let data = $("#profileUpdate").serialize();
    $.ajax({
        type: "put",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        console.log("업데이트 성공", res);
        location.href=`/user/${userId}`;
    }).fail(error => {
        if(error.data == null) {
            alert(error.responseJSON.message);
        }else {
            alert(JSON.stringify(error.responseJSON.data));
        }
    });
}