<?php
	include_once('db.php');
	if(!array_key_exists('email_address', $_POST) || !array_key_exists('password', $_POST)) {
		print json_encode(array('error' => 'usage: post email_address and password'));
		exit();
	}
	try {
		$dbh = new PDO($conn_str, $user, $password);
		$stmt = $dbh->prepare("SELECT user_id FROM users WHERE email_address = :email_address AND password_hash = :password_hash");
		$stmt->bindParam(':email_address', $_POST['email_address']);
		$stmt->bindParam(':password_hash', md5($_POST['password']));
		if($stmt->execute()) {
			if($res = $stmt->fetchColumn()) {
				print json_encode(array('ok' => $res));
			}
			else {
				print json_encode(array('error' => 'bad email or password'));
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