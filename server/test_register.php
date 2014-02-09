<form action='register.php' method="POST" enctype="multipart/form-data">
	<input type='hidden' name='password' value='tmp'>
	<input type='hidden' name='email_address' value='tmp'>
	<input type='hidden' name='name' value='tmp'>
	<input type='hidden' name='sex' value='tmp'>
	<input type='hidden' name='age' value='1'>
	<input type='hidden' name='race' value='tmp'>
	<input type='hidden' name='height' value='1'>
	<input type='hidden' name='locations' value='g'>
	Send these files:<br />
	<input name="train_file1" type="file" /><br />
	<input name="train_file2" type="file" /><br />
	<input type="submit" value="Send files" />
</form>