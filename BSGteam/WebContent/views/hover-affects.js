//assign hover actions to each image
$('.image-container').live('mouseover', function(){
    $(this).children('div').attr('class', 'image-info-active');
});
$('.image-container').live('mouseout', function(){
    $(this).children('div').attr('class', 'image-info');
});