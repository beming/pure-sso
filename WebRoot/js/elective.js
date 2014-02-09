function refreshCache(btn, tag) {
	var form = btn.form;
	form.cache.value = 1;
	form.tag.value = tag;
	return validateCallback(form, navTabAjaxDone);
}