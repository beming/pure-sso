jQuery(document).ready(function() {
	var sFeatures = "height=600, width=810, scrollbars=yes, resizable=yes";
	jQuery('a[rel="external"]').click( function() {
		window.open( jQuery(this).attr('href'), '3km', sFeatures );
		return false;
	});
});

jQuery(document).ready(function() {
	jQuery("a[rel=external]").attr('target', '_blank');
});

jQuery.each($('.TextContent img'),function(idx,v){
    $(v).wrap("<a href='"+$(this).attr('src')+"' target='_blank'></a>");
});