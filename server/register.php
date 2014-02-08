<?php
	include_once('db.php');
	if(!array_key_exists('password', $_POST) || !array_key_exists('email_address', $_POST) ||
		!array_key_exists('sex', $_POST) || !array_key_exists('age', $_POST) || !array_key_exists('race', $_POST) ||
		!array_key_exists('height', $_POST) || !array_key_exists('locations', $_POST)) {
		print json_encode(array('error' => 'usage: post password, email_address, name, sex, age, race, height, locations (comma separated)'));
		exit();
	}
	function assert_value_unique($dbh, $field, $value) {
		$stmt = $dbh->prepare("SELECT count({$field}) FROM users WHERE {$field} = :value");
		$stmt->bindParam(':value', $value);
		$stmt->execute();
		$value_exists = $stmt->fetchColumn() > 0;
		if($value_exists) {
			print json_encode(array('error' => $field . ' already exists'));
			exit();
		}
	}
	try {
		$dbh = new PDO($conn_str, $user, $password);
		assert_value_unique($dbh, 'email_address', $_POST['email_address']);
		$stmt = $dbh->prepare('INSERT INTO users (password_hash, email_address, sex, age, race, height) VALUES (:password_hash, :email_address, :sex, :age, :race, :height)');
		$stmt->bindParam(':password_hash', md5($_POST['password']));
		$stmt->bindParam(':email_address', $_POST['email_address']);
		$stmt->bindParam(':sex', $_POST['sex']);
		$stmt->bindParam(':age', $_POST['age']);
		$stmt->bindParam(':race', $_POST['race']);
		$stmt->bindParam(':height', $_POST['height']);
		if($stmt->execute()) {
			$user_id = $dbh->lastInsertId();
			$stmt = $dbh->prepare('INSERT INTO locations (user_id, location) VALUES (:user_id, :location)');
			$locations = explode(',', $_POST['locations']);
			$stmt->bindParam(':user_id', $user_id);
			$stmt->bindParam(':location', $location);
			foreach ($locations as $location) {
				$stmt->execute();
			}
			print json_encode(array('ok' => $user_id));
		}
		else {
			print json_encode(array('error' => 'could not register'));
		}
	}
	catch(PDOException $e) {
		print json_encode(array('error' => '$e->getMessage()'));
	}
?>