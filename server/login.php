<?php
	include_once('db.php');
	if(!array_key_exists('user_name', $_POST) || !array_key_exists('password', $_POST)) {
		print json_encode(array('error' => 'usage: post user_name and password'));
		exit();
	}
	try {
		$dbh = new PDO($conn_str, $user, $password);
		$stmt = $dbh->prepare("SELECT user_id FROM users WHERE user_name = :user_name AND password_hash = :password_hash");
		$stmt->bindParam(':user_name', $_POST['user_name']);
		$stmt->bindParam(':password_hash', md5($_POST['password']));
		if($stmt->execute()) {
			if($res = $stmt->fetchColumn()) {
				print json_encode(array('ok' => $res));
			}
			else {
				print json_encode(array('error' => 'bad username or password'));
			}
		}
		else {
			print json_encode(array('error' => 'couldn\'t query'));
		}
	}
	catch(PDOException $e) {
		echo json_encode(array('error' => $e->getMessage()));
	}




	
		

?>