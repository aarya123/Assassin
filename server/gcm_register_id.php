<?php
	include_once('db.php');
	if(!array_key_exists('registration_id', $_POST) || !array_key_exists('user_id', $_POST)) {
		print json_encode(array('error' => 'usage: post gcm registration id (registration_id), user_id'));
		exit();
	}
	try {
		$dbh = new PDO($conn_str, $user, $password);
		$stmt = $dbh->prepare('SELECT count(user_id) FROM users WHERE user_id = :user_id');
		$stmt->bindParam(':user_id', $_POST['user_id']);
		$stmt->execute();
		$user_id_exists = $stmt->fetchColumn() > 0;
		if(!$user_id_exists) {
			print json_encode(array('error' => 'user_id doesn\'t exist!'));
			exit();
		}
		$stmt = $dbh->prepare('UPDATE users SET registration_id = :registration_id WHERE user_id = :user_id');
		$stmt->bindParam(':registration_id', $_POST['registration_id']);
		$stmt->bindParam(':user_id', $_POST['user_id']);
		if($stmt->execute()) {
			print json_encode(array('ok' => 'registration id updated'));
		}
		else {
			print json_encode(array('error' => 'couldn\'t update registration id'));
		}
	}
	catch(PDOException $e) {
		print json_encode(array('error' => $e->getMessage()));
	}
?>,