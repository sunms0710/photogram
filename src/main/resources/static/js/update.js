//회원정보 수정
function update(userId) {
    let data = $("#profileUpdate").serialize();
    $.ajax({
        type: "put",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        console.log("업데이트 성공");
        location.href=`/user/${userId}`;
    }).fail(error => {
        console.log("업데이트 실패");
    });
}