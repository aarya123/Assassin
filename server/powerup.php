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

		if(array_key_exists('powerup', $_POST)) {
			$powerup = array('powerup', $_POST);
			if($powerup == 0) {
				print 'Invisibility Powerup Activated';
				// Find and message all hunters
				$sql = 'SELECT id FROM users';
				while($row = fetch_assoc(mysql_query($sql))) {
					$target = 'SELECT target FROM users WHERE userid= :user_id';
					if($target == $userid) {
						messageUser($dbh, $_POST['$row[id]'], ['Your target activated an invisibility powerup!']);
					}
				}
			} 
			else if($powerup == 1) {
				print 'Radar Powerup Activated';
				$target = array('target', $_POST);
				messageUser($dbh, $_POST[$target], ['Your target activated a radar powerup!']);
				// Get target longitude & latitude from database
				$sql = 'SELECT id FROM users';
				while($row = fetch_assoc(mysql_query($sql))) {
					$target_id = 'SELECT id FROM users WHERE user_id = :user_id';
					if($target_id == $target) {
						$longitude = mysql_fetch_row('longitude');
						$latitude = mysql_fetch_row('latitude');
						// Push target location to device
						gcmPush($longitude, $latitude);
					}
					break;
				}
			}
		}
	}
	catch(Exception $e) {
		print json_encode(array('error' => $e->getMessage()));
	}
?>