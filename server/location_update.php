<?php
	include_once('db.php');
	if(!array_key_exists('latitude', $_POST) || !array_key_exists('longitude', $_POST) || !array_key_exists('user_id', $_POST)) {
		print json_encode(array('error' => 'usage: post user_id, latitude, longitude'));
		exit();
	}
	try {
		$dbh = new PDO($conn_str, $user, $password);
		// Prevent sending of location if invisibility powerup is activated
		if (!array_key_exists('powerup', $_POST) || array('powerup', $_POST) != 0) {
			$stmt = $dbh->prepare('UPDATE users SET current_latitude = :latitude, current_longitude = :longitude WHERE user_id = :user_id');
			$stmt->bindParam(':latitude', $_POST['latitude']);
			$stmt->bindParam(':longitude', $_POST['longitude']);
			$stmt->bindParam(':user_id', $_POST['user_id']);
			if($stmt->execute()) {
				print json_encode(array('ok' => 'location updated'));
			}
			else {
				print json_encode(array('error' => 'failed to update location'));
			}
		}
		else {
			print json_encode(array('ok' => 'location hidden'));
		}
	}
	catch(PDOException $e) {
		print json_encode(array('error' => $e->getMessage()));
	}
?>