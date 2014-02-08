<?php
	include_once('db.php');
	if(!array_key_exists('user_name', $_POST) || !array_key_exists('password', $_POST)) {
		print json_encode(array('error' => 'usage: post user_name and password'));
		exit();
	}
	try {
		$dbh = new PDO($conn_str, $user, $password);
		$stmt = $dbh->prepare('SELECT count(user_name) FROM users WHERE user_name = :user_name');
		$stmt->bindParam(':user_name', $_POST['user_name']);
		$stmt->execute();
		$user_name_exists = $stmt->fetchColumn() > 0;
		if($user_name_exists) {
			print json_encode(array('error' => 'user name exists'));
		}
		else {
			$stmt = $dbh->prepare('INSERT INTO users (user_name, password_hash) VALUES (:user_name, :password_hash)');
			$stmt->bindParam(':user_name', $_POST['user_name']);
			$stmt->bindParam(':password_hash', md5($_POST['password']));
			if($stmt->execute()) {
				print json_encode(array('ok' => $dbh->lastInsertId()));
			}
			else {
				print json_encode(array('error' => 'could not register'));
			}
		}
	}
	catch(PDOException $e) {
		print $e->getMessage();
	}
?>