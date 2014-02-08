<?php
	include_once('db.php');
	include_once('gcm.php');
	if(!array_key_exists('user_id', $_POST)) {
		print json_encode(array('error' => 'usage: post user_id'));
	}
	try {
		$dbh = new PDO($conn_str, $user, $password);
		$result = messageUser($dbh, $_POST['user_id']);
		print $result;
	}
	catch(Exception $e) {
		print json_encode(array('error' => $e->getMessage()));
	}
?>