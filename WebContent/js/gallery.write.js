/**
 * gallery 등록
 */
$('#filename').on('change', function(e){
	var fileSrc = URL.createObjectURL(e.target.files[0]);
	$('.picture img').attr('src', fileSrc);
});